package com.simplehttpserver.server

import com.simplehttpserver.HttpService

trait HttpServer {

  def start(port: Int)(services: (String, HttpService)*): Unit

}
