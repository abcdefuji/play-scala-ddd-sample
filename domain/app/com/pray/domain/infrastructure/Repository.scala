package com.pray.domain.infrastructure

import common.Identifier
import scalikejdbc.interpolation.SQLSyntax
import scala.util.Try

trait Repository[ID <: Identifier[_], E <: Entity[ID]] {

  type This <: Repository[ID, E]

  type Ctx = EntityIOContext

  def store(entity: E)(implicit ctx: Ctx): Try[(This, E)]

  def resolveById(identifier: ID)(implicit ctx: Ctx): Try[E]

  def deleteBy(id: ID)(implicit ctx: Ctx): Try[(This, E)]

  def existBy(sql: SQLSyntax)(implicit ctx: Ctx): Try[Boolean]

}
