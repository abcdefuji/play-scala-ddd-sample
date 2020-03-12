package com.pray.domain.infrastructure

import common.Identifier
import com.pray.infrastructure.db.CRUDMapper
import scalikejdbc.DBSession

trait RepositoryOnJDBC[ID <: Identifier[Long], E <: Entity[ID]] extends Repository[ID, E] {

  type T

  protected val mapper: CRUDMapper[T]

  protected val idName: String = "id"

  protected val statusName: String = "status"

  protected def withDBSession[A](f: DBSession => A)(implicit ctx: EntityIOContext): A = ctx match {
    case EntityIOContextOnJDBC(dbSession) => f(dbSession)
    case _ => throw new IllegalStateException {
      s"Unexpected context id bound (expected: EntityIOContextOnJDBC, actual: $ctx)"
    }
  }
}