package com.simplehttpserver.dsl

import com.simplehttpserver.EntitySerializer

trait ResponseSerializer extends Any {
  def status: Status
}

trait EmptyResponseSerializer extends Any with ResponseSerializer{
  def apply(): HttpResponse = HttpResponse(status, "")
}


trait EntityResponseSerializer  extends Any with EmptyResponseSerializer{
  def apply[A: EntitySerializer](body: A)(): HttpResponse = {
    val serializer = implicitly[EntitySerializer[A]]
    HttpResponse(status, serializer.serialize(body))
  }
}