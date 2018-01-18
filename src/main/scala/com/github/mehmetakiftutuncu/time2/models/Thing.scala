package com.github.mehmetakiftutuncu.time2.models

import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder}

final case class Thing(id: Long, what: String, when: ZonedDateTime) extends Model[Thing] {
  override val jsonEncoder: Encoder[Thing] = Encoder.forProduct3("id", "what", "when")(t => (t.id, t.what, t.when))
}

object Thing extends JsonSupport[Thing] {
  override val jsonDecoder: Decoder[Thing] = Decoder.forProduct3("id", "what", "when")(Thing.apply)
}
