package domain.user

import com.pray.domain.infrastructure.common.Identifier
import com.pray.infrastructure.service.IdentifierService

trait UserId extends Identifier[Long] {

  def value: Long

}

object UserId extends IdentifierService {

  def apply(value: Long): UserId = UserIdImpl(value)

  def create: UserId = UserIdImpl(this.generate)

  private case class UserIdImpl(value: Long) extends UserId

}