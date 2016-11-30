package com.simplehttpserver.server

import java.net.InetSocketAddress

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer => SunHttpServer}
import com.simplehttpserver.HttpService
import com.simplehttpserver.dsl.HttpRequest.{GET, POST}
import com.simplehttpserver.dsl._

import scala.io.Source

object SimpleSunHttpServer extends HttpServer {

  override def start(port: Int = 9000)(services: (String, HttpService)*): Unit = {
    val httpServer = SunHttpServer.create(new InetSocketAddress(port), 0)
    services.foreach {
      case (url, service) => httpServer.createContext(url, serviceToSunHttpHandler(url, service))
    }
    httpServer.start()
  }

  private def serviceToSunHttpHandler(prefix: String, service: HttpService): HttpHandler = new HttpHandler {

    private def normalize(httpMethod: String) = httpMethod.toUpperCase

    private def readRequest(httpExchange: HttpExchange): Option[HttpRequest] = {
      val methodStr = httpExchange.getRequestMethod
      val url = httpExchange.getRequestURI.toString.replace(prefix, "/")

      normalize(methodStr) match {
        case "GET" => Some(GET(url))
        case "POST" =>
          val body = Source.fromInputStream(httpExchange.getRequestBody()).mkString
          Some(POST(url, body))
        case _ => None
      }
    }

    private def sendResponse(response: HttpResponse)(httpExchange: HttpExchange): Unit = {
      val body = response.body.getBytes
      httpExchange.sendResponseHeaders(response.status.code, body.length)
      val os = httpExchange.getResponseBody
      try {
        os.write(body)
      } finally {
        os.close()
      }
    }

    override def handle(httpExchange: HttpExchange): Unit = {
      val response = readRequest(httpExchange).map(request => service(request)).getOrElse(BadRequest())
      sendResponse(response)(httpExchange)
    }
  }
}
