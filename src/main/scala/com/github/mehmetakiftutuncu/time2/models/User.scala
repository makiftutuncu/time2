package com.github.mehmetakiftutuncu.time2.models

import io.circe.{Decoder, Encoder}

final case class User(id: Long, email: String) extends Model[User] {
  override implicit val jsonEncoder: Encoder[User] = User.jsonEncoder
}

object User extends JsonSupport[User] {
  override val jsonEncoder: Encoder[User] = Encoder.forProduct2("id", "email")(u => u.id -> u.email)
  override val jsonDecoder: Decoder[User] = Decoder.forProduct2("id", "email")(User.apply)
}
