package com.simplehttpserver

package object dsl {

  implicit class OkSerializer(val status: OK.type) extends AnyVal with EntityResponseSerializer
  implicit class NotFoundSerializer(val status: NotFound.type) extends AnyVal with EntityResponseSerializer
  implicit class InternalErrorSerializer(val status: InternalError.type) extends AnyVal with EntityResponseSerializer
  implicit class BadRequestSerializer(val status: BadRequest.type) extends AnyVal with EntityResponseSerializer

  val OK: Status.Ok.type = Status.Ok
  val NotFound: Status.NotFound.type = Status.NotFound
  val InternalError: Status.InternalError.type = Status.InternalError
  val BadRequest: Status.BadRequest.type = Status.BadRequest
}
