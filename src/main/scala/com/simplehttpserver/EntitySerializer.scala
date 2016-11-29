package com.simplehttpserver

trait EntitySerializer[A] {
  def serialize(a: A): String
}

object EntitySerializer {
  implicit def stringEntitySerializer = new EntitySerializer[String] {
    override def serialize(str: String): String = str
  }
}