package com.pray.domain.infrastructure.common.data

object Bcript {

  def create(s: String) = BcryptImpl.generate(s)

  def check(raw: String, hash: String) = BcryptImpl.hasBcrypted(raw, hash)

  private object BcryptImpl {

    import com.github.t3hnar.bcrypt._

    final val Rounds: Int = 8 // pow(2, 8) = 256

    def generate(s: String) = s.bcrypt(Rounds)

    def hasBcrypted(raw: String, hash: String) = raw.isBcrypted(hash)

  }

}
