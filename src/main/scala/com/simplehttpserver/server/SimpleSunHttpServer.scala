package com.simplehttpserver.server

import java.net.InetSocketAddress

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer => SunHttpServer}
import com.simplehttpserver.HttpService
import com.simplehttpserver.dsl.HttpRequest.{GET, POST}
import com.simplehttpserver.dsl._
import com.simplehttpserver.server.ServerState.{Initial, Started, Stopped}

import scala.io.Source

object SimpleSunHttpServer {

  def create(port: Int): HttpServer[Initial] = new HttpServer[Initial] {

    private[this] lazy val httpServer = SunHttpServer.create(new InetSocketAddress(port), 0)

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
        os.write(body)
        os.close()
      }

      override def handle(httpExchange: HttpExchange): Unit = {
        val response = readRequest(httpExchange).map(request => service(request)).getOrElse(BadRequest())
        sendResponse(response)(httpExchange)
      }
    }

    override def start[T <: Initial](services: (String, HttpService)*): HttpServer[Started] = {
      services.foreach {
        case (url, service) => httpServer.createContext(url, serviceToSunHttpHandler(url, service))
      }
      httpServer.start()
      this.asInstanceOf[HttpServer[Started]]
    }

    override def stop[T <: Started](): HttpServer[Stopped] = {
      httpServer.stop(0)
      this.asInstanceOf[HttpServer[Stopped]]
    }
  }
}
