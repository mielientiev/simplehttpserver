package examples

import com.simplehttpserver.HttpService
import com.simplehttpserver.dsl.HttpRequest.GET
import com.simplehttpserver.dsl._
import com.simplehttpserver.server.SimpleSunHttpServer

object Main {

  val myService: HttpService = HttpService {
    case GET("/hello") => OK("Hello World")
    case GET("/account") => OK(Account("ABC", 21321))
  }

  val nextService: HttpService = HttpService {
    case GET("/abc") => OK("abc")
  }


  def main(args: Array[String]): Unit = {
    SimpleSunHttpServer.start()(
      ("/", myService),
      ("/a/", nextService)
    )
  }
}

