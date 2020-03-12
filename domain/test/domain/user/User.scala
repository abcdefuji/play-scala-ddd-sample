package domain.user

import com.pray.domain.infrastructure.Entity
import com.pray.domain.infrastructure.common.{ Status, Version }
import com.pray.domain.infrastructure.common.data._
import org.joda.time.DateTime

case class UserName(value: String)

case class User(
                 id: UserId = UserId.create,
                 name: UserName,
                 email: Option[Email],
                 status: Status.Value = Status.Enabled,
                 version: Version = Version.init) extends Entity[UserId] {

  override def withVersion: User =
    copy(version = Version(createdAt = this.version.createdAt,
      updatedAt = Option(DateTime.now)))

}

