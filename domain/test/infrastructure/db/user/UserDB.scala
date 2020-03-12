package infrastructure.db.user

import scalikejdbc._

trait UserDB extends infrastructure.db.DBConnection {

  DB autoCommit { implicit s =>
    sql"""
CREATE TABLE IF NOT EXISTS users (
  pk bigint(20) NOT NULL AUTO_INCREMENT,
  id bigint(20) NOT NULL,
  name varchar(32) NOT NULL,
  email varchar(128) DEFAULT NULL,
  status varchar(32) NOT NULL,
  created_at datetime NOT NULL,
  updated_at datetime DEFAULT NULL,
  PRIMARY KEY (pk),
  UNIQUE KEY (id),
  UNIQUE KEY (email),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
          """.execute().apply()
  }

}
