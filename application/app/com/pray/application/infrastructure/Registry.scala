package com.pray.application.infrastructure

import com.pray.domain.infrastructure.EntityIOContextOnJDBC
import scalikejdbc.ConnectionPool

trait Registry {

  Class.forName("")

  ConnectionPool.singleton("", "", "")

  implicit val ctx = EntityIOContextOnJDBC()

}
