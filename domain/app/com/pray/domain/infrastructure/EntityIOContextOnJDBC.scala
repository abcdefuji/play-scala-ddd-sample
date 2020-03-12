package com.pray.domain.infrastructure

import scalikejdbc.{AutoSession, DBSession}

case class EntityIOContextOnJDBC(session: DBSession = AutoSession) extends EntityIOContext
