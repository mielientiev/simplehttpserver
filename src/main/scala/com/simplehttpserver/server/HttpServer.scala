package com.simplehttpserver.server

import com.simplehttpserver.HttpService
import com.simplehttpserver.server.ServerState.{Initial, Started, Stopped}

sealed trait ServerState

object ServerState {
  final class Initial extends ServerState
  final class Started extends ServerState
  final class Stopped extends ServerState
}

trait HttpServer[State <: ServerState] {

  def start[T >: State <: Initial](services: (String, HttpService)*): HttpServer[Started]

  def stop[T >: State <: Started](): HttpServer[Stopped]

}
