package com.simplehttpserver.dsl

sealed class HttpRequest(method: String, path: String)
object HttpRequest {
  case class GET(path: String) extends HttpRequest("GET", path)
  case class POST(path: String, body: String) extends HttpRequest("POST", path)
}
