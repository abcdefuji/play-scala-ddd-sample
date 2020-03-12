package spec.user

import com.pray.domain.infrastructure.{EntityIOContextOnJDBC, EntityIOContext}
import com.pray.domain.infrastructure.common.data.Email
import com.pray.domain.infrastructure.common.Status
import org.specs2.mutable.Specification
import scalikejdbc._
import scalikejdbc.specs2.mutable.AutoRollback
import org.joda.time.DateTime
import domain.user.{UserName, User, UserRepository, UserId}
import infrastructure.db.user.UserDB

sealed trait UserAutoRollbackWithFixture extends AutoRollback {

  override def fixture(implicit session: DBSession) {
    SQL("insert into users values (?, ?, ? ,?, ?, ?, ?)")
      .bind(1, UserId.create.value, "bob", "bob@gmail.com",
        Status.Enabled.toString, DateTime.now, None).update().apply()
  }

}

class UserRepositorySpec extends Specification with UserDB {

  "User" should {

    def withContext[A](session: DBSession)(f: EntityIOContext => A): A =
      f(EntityIOContextOnJDBC(session))

    val repository = UserRepository.ofJDBC

    val tom = User(
      name = UserName("tom"),
      email = Option(Email("tom@gmail.com")))

    "create new user" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        val result = repository.store(tom)
        result must beSuccessfulTry
        val (_, entity) = result.get
        entity.id mustEqual tom.id
        entity.name.value mustEqual tom.name.value
        entity.version.updatedAt mustEqual None
      }
    }

    "create new user failed with duplicated id" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        val storedTom = repository.store(tom)
        storedTom must beSuccessfulTry
        val bob = User(
          id = tom.id,
          name = UserName("bob"),
          email = None)
        val storedBob = repository.store(bob)
        storedBob must beFailedTry
      }
    }

    "update user" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        val storedTom = repository.store(tom)
        val newTom = tom.copy(name = UserName("new tom")).withVersion
        val updatedTom = repository.store(newTom)
        updatedTom must beSuccessfulTry
        val (_, result) = updatedTom.get
        result.id must_== tom.id
        result.name.value must_== "new tom"
        result.version.createdAt must_== tom.version.createdAt
        result.version.updatedAt must_== newTom.version.updatedAt
      }
    }

    "find by id" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        repository.store(tom) must beSuccessfulTry
        repository.resolveById(tom.id) must beSuccessfulTry.like {
          case entity => entity must_== tom
        }
      }
    }

    "find by name" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        repository.resolveByName("bob") must beSuccessfulTry
      }
    }

    "find by name failed" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        repository.resolveByName("notom") must beFailedTry
      }
    }

    "find all records" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        repository.store(tom) must beSuccessfulTry
        val allResults = repository.resolveAll
        allResults must beSuccessfulTry
        allResults.get.size should be_==(2)
      }
    }

    "is exist enabled user" in new UserAutoRollbackWithFixture {
      withContext(session) { implicit ctx =>
        val isExistActiveUser = repository.existByStatus
        isExistActiveUser must beSuccessfulTry.like {
          case exist => exist should beTrue
        }
      }
    }

    /*
    "find all by where clauses" in new UserAutoRollbackWithFixture {
      val results = User.findAllBy(sqls.eq(u.id, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new UserAutoRollbackWithFixture {
      val count = User.countBy(sqls.eq(u.id, 1L))
      count should be_>(0L)
    }
    "create new record" in new UserAutoRollbackWithFixture {
      val created = User.create(createdAt = DateTime.now)
      created should not beNull
    }
    "save a record" in new UserAutoRollbackWithFixture {
      val entity = User.findAll().head
      val modified = entity.copy(name = Some("Changed"))
      val updated = User.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new UserAutoRollbackWithFixture {
      val entity = User.findAll().head
      User.destroy(entity)
      val shouldBeNone = User.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    */
  }

}
