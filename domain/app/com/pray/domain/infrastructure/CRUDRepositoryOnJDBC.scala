package com.pray.domain.infrastructure

import common.{ Status, Identifier }
import exception.{ EntitiesNotFoundException, RepositoryIOException, EntityNotFoundException }
import com.pray.infrastructure.db.{ Record, CRUDMapper }
import scalikejdbc._
import skinny.orm.feature.CRUDFeatureWithId
import scala.util.Try
import scala.language.implicitConversions

trait CRUDRepositoryOnJDBC[ID <: Identifier[Long], E <: Entity[ID]] extends RepositoryOnJDBC[ID, E] {
  self =>

  type T <: Record

  override protected val mapper: CRUDMapper[T]

  type SQLQuery = mapper.EntitiesSelectOperationBuilder

  type JoinedQuery = CRUDFeatureWithId[Nothing, T]#EntitiesSelectOperationBuilder

  implicit def string2field(name: String): SQLSyntax = mapper.defaultAlias.field(name)

  def toPrimaryKey(id: Identifier[Long]): Long = id.value

  private lazy val sqlClause: SQLSyntax => String = where =>
    where.parameters.foldLeft(where.value) { (l, r) => l.replaceFirst("""\?""", r.toString) }

  protected def toRecord(model: E): T

  protected def toEntity(record: T): E

  private object Query {

    def withId(identifier: ID) = sqls.eq(idName, toPrimaryKey(identifier))

    def withIdStatus(identifier: ID) =
      withId(identifier).and.eq(statusName, Status.Enabled.toString)

  }

  override def store(entity: E)(implicit ctx: Ctx): Try[(This, E)] = withDBSession {
    implicit s =>
      Try {
        def update() = if (mapper.updateBy(Query.withId(entity.id))
          .withAttributes(mapper.toNamedValues(toRecord(entity)).filterNot {
          case (k, _) => k.name == mapper.primaryKeyFieldName
        }: _*) == 0)
          throw new IllegalStateException(s"This Entity has illegal version(id = ${entity.id})")
        def insert() = mapper.createWithAttributes(mapper.toNamedValues(toRecord(entity)): _*)
        if (entity.version.updatedAt.isDefined) update() else insert()
        entity
      } map ((this.asInstanceOf[This], _))
  }

  override def resolveById(identifier: ID)(implicit ctx: Ctx): Try[E] = withDBSession {
    implicit s =>
      Try {
        mapper.findBy(Query.withIdStatus(identifier)).map(toEntity) getOrElse {
          throw EntityNotFoundException(identifier)
        }
      }
  }

  protected def resolveBy(where: SQLSyntax)(implicit ctx: Ctx): Try[E] = withDBSession {
    implicit s =>
      Try {
        mapper.findBy(where).map(toEntity) getOrElse {
          throw EntityNotFoundException(sqlClause(where))
        }
      }
  }

  protected def resolveBy(joinedMapper: CRUDFeatureWithId[_, T])(where: SQLSyntax)(implicit ctx: Ctx): Try[E] = withDBSession {
    implicit s =>
      Try {
        joinedMapper.findBy(where).map(toEntity) getOrElse {
          throw EntityNotFoundException(sqlClause(where))
        }
      }
  }

  protected def resolveAllBy(where: SQLSyntax)(implicit ctx: Ctx): Try[Seq[E]] = withDBSession {
    implicit s =>
      Try {
        mapper.findAllBy(where).map(toEntity) match {
          case Nil => throw EntitiesNotFoundException(sqlClause(where))
          case entities => entities
        }
      }
  }

  protected def resolveAllBy(query: SQLQuery)(implicit ctx: Ctx): Try[Seq[E]] = withDBSession {
    implicit s =>
      Try {
        query.apply().map(toEntity) match {
          case Nil => throw EntitiesNotFoundException()
          case entities => entities
        }
      }
  }

  protected def resolveAllBy[R](joinedQuery: JoinedQuery)(implicit ctx: Ctx, ev: R <:< JoinedQuery): Try[Seq[E]] = withDBSession {
    implicit s =>
      Try {
        joinedQuery.apply().map(toEntity) match {
          case Nil => throw EntitiesNotFoundException()
          case entities => entities
        }
      }
  }

  override def existBy(sql: SQLSyntax)(implicit ctx: Ctx): Try[Boolean] = withDBSession {
    implicit s => Try(mapper.countBy(sql) > 0)
  }

  override def deleteBy(identifier: ID)(implicit ctx: Ctx): Try[(This, E)] = withDBSession {
    implicit s =>
      resolveById(identifier).map { entity =>
        if (mapper.deleteBy(Query.withId(identifier)) == 0) {
          throw new RepositoryIOException(s"Failed to delete record: $identifier")
        } else entity
      } map ((this.asInstanceOf[This], _))
  }

}