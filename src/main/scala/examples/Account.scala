package examples

import com.simplehttpserver.EntitySerializer


case class Account(id: String, number: Int)

object Account {
  implicit val accountJsonSerializer = new EntitySerializer[Account] {
    override def serialize(a: Account): String = s"""{"id": "${a.id}", "number": "${a.number}" }"""
  }
}

