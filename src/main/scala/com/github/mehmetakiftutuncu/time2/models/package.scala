package com.github.mehmetakiftutuncu.time2

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import io.circe.{Decoder, Encoder}

import scala.util.Try

package object models {
  implicit val zonedDateTimeJsonEncoder: Encoder[ZonedDateTime] =
    Encoder.encodeString.contramap(_.format(DateTimeFormatter.ISO_DATE_TIME))

  implicit val zonedDateTimeJsonDecoder: Decoder[ZonedDateTime] =
    Decoder.decodeString.emapTry(s => Try(ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)))
}
