package com.pray.infrastructure.db

import skinny.orm.{SkinnyCRUDMapper, Alias}

trait CRUDMapper[T] extends SkinnyCRUDMapper[T] {

  final override def primaryKeyFieldName = "pk"

  final def idFieldName = "id"

  final def statusFieldName = "status"

  final def createdAtFieldName = "createdAt"

  final def updatedAtFieldName = "updatedAt"

  /* require below 4methods, when extends this trait */

  def defaultAlias: Alias[T]

  def tableName: String

  def toNamedValues(record: T): Seq[(Symbol, Any)]

  // def extract(rs: WrappedResultSet, n: ResultName[T])

}
