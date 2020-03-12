package domain.user

import com.pray.domain.infrastructure.common.data.Email
import com.pray.domain.infrastructure.common.{ Status, Version }
import com.pray.domain.infrastructure.mapper.EntityRecordMapper
import infrastructure.db.user.UserRecord

trait UserMapper extends EntityRecordMapper[UserId, User, UserRecord] {

  protected def toRecord(entity: User): UserRecord = UserRecord(
    id = entity.id.value,
    name = entity.name.value,
    email = entity.email.map(_.value),
    status = entity.status.toString,
    createdAt = entity.version.createdAt,
    updatedAt = entity.version.updatedAt
  )

  protected def toEntity(record: UserRecord): User = User(
    id = UserId(record.id),
    name = UserName(record.name),
    email = record.email.map(Email(_)),
    status = Status.withName(record.status),
    version = Version(record.createdAt, record.updatedAt)
  )
  
}
