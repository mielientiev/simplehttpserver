package com.simplehttpserver

import com.simplehttpserver.dsl.{HttpRequest, HttpResponse, NotFound}

trait HttpService {
  def apply(req: HttpRequest): HttpResponse
}

object HttpService {
  def apply(pf: PartialFunction[HttpRequest, HttpResponse]): HttpService =
    (req: HttpRequest) => pf.applyOrElse[HttpRequest, HttpResponse](req, _ => NotFound())
}
