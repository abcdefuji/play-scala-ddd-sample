package infrastructure.db.user

import com.pray.infrastructure.db.{CRUDMapper, Record}
import org.joda.time.DateTime
import scalikejdbc._

case class UserRecord(
                       id: Long,
                       name: String,
                       email: Option[String],
                       status: String,
                       createdAt: DateTime,
                       updatedAt: Option[DateTime]) extends Record

object UserRecord extends CRUDMapper[UserRecord] {

  override def defaultAlias = createAlias("users")

  override def tableName: String = "users"

  override def toNamedValues(record: UserRecord): Seq[(Symbol, Any)] = Seq(
    'id -> record.id,
    'name -> record.name,
    'email -> record.email,
    'status -> record.status,
    'createdAt -> record.createdAt,
    'updatedAt -> record.updatedAt
  )

  override def extract(rs: WrappedResultSet, n: ResultName[UserRecord]) = UserRecord(
    id = rs.long(n.id),
    name = rs.string(n.name),
    email = rs.stringOpt(n.email),
    status = rs.string(n.status),
    createdAt = rs.jodaDateTime(n.createdAt),
    updatedAt = rs.jodaDateTimeOpt(n.updatedAt)
  )

}
