package infrastructure.db

import scalikejdbc.ConnectionPool

trait DBConnection {

  Class.forName("org.h2.Driver")

  ConnectionPool.singleton("jdbc:h2:mem:default", "sa", "secret")

}
