package com.simplehttpserver.dsl

final case class HttpResponse(status: Status, body: String)

private[dsl] final case class Status private (code: Int)

private[dsl] object Status {
  val Ok = Status(200)
  val NotFound = Status(404)
  val InternalError = Status(500)
  val BadRequest = Status(400)
}

