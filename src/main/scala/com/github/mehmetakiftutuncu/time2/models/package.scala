package com.github.mehmetakiftutuncu.time2

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import io.circe.{Decoder, Encoder, Json}

import scala.util.Try

package object models {
  implicit val zonedDateTimeJsonEncoder: Encoder[ZonedDateTime] =
    Encoder.encodeString.contramap(_.format(DateTimeFormatter.ISO_DATE_TIME))

  implicit val zonedDateTimeJsonDecoder: Decoder[ZonedDateTime] =
    Decoder.decodeString.emapTry(s => Try(ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)))

  trait Model[M] { self: M =>
    implicit val jsonEncoder: Encoder[M]

    def toJson: Json = jsonEncoder(self)

    override def toString: String = toJson.noSpaces
  }

  trait JsonSupport[T <: Model[T]] {
    val jsonEncoder: Encoder[T]
    val jsonDecoder: Decoder[T]

    def fromJson(json: Json): Decoder.Result[T] = jsonDecoder.decodeJson(json)
  }
}
