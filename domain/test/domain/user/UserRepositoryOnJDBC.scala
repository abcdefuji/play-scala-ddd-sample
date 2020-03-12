package domain.user

import com.pray.domain.infrastructure.common.Status
import com.pray.domain.infrastructure.{ EntityIOContextOnJDBC, EntityIOContext, CRUDRepositoryOnJDBC }
import infrastructure.db.user.UserRecord
import scalikejdbc._

class UserRepositoryOnJDBC extends CRUDRepositoryOnJDBC[UserId, User] with UserRepository with UserMapper {

  type T = UserRecord

  override protected val mapper = UserRecord

  def resolveByName(name: String)(implicit ctx: EntityIOContext) = resolveBy(sqls.eq("name", name))

  implicit val ctx = EntityIOContextOnJDBC()

  def resolveAll(implicit ctx: EntityIOContext) = resolveAllBy(mapper.limit(5).offset(0))

  def resolveNone(implicit ctx: EntityIOContext) = resolveAllBy(mapper.where('name -> "aki").limit(5).offset(0))

  def existByStatus(implicit ctx: EntityIOContext) = existBy(sqls.eq("status", Status.Enabled.toString))

}
