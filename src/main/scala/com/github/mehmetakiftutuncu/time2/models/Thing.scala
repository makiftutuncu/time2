package com.github.mehmetakiftutuncu.time2.models

import io.circe.{Decoder, Encoder}

final case class Thing(id: Long,
                       what: String,
                       how: Option[String],
                       ownerId: Long,
                       planId: Long) extends Model[Thing] {
  override implicit val jsonEncoder: Encoder[Thing] = Thing.jsonEncoder
}

object Thing extends JsonSupport[Thing] {
  override val jsonEncoder: Encoder[Thing] = {
    implicit val userJsonEncoder: Encoder[User] = User.jsonEncoder

    Encoder.forProduct5("id", "what", "how", "ownerId", "planId")(t => (t.id, t.what, t.how, t.ownerId, t.planId))
  }

  override val jsonDecoder: Decoder[Thing] = {
    implicit val userJsonDecoder: Decoder[User] = User.jsonDecoder

    Decoder.forProduct5("id", "what", "how", "ownerId", "planId")(Thing.apply)
  }
}
