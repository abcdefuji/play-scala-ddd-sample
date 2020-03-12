package domain.user

import com.pray.domain.infrastructure.Repository

trait UserRepository extends Repository[UserId, User] {

  type This = UserRepositoryOnJDBC

}

object UserRepository {

  def ofJDBC: UserRepositoryOnJDBC = new UserRepositoryOnJDBC

}
