package com.github.mehmetakiftutuncu.time2.models

import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder, JsonObject, ObjectEncoder}

sealed trait Repetition extends Model[Repetition] {
  override implicit val jsonEncoder: Encoder[Repetition] = Repetition.jsonEncoder
}

object Repetition extends JsonSupport[Repetition] {
  case object NoRepetition                                             extends Repetition
  final case class RepeatNTimes(times: Int, by: RepetitionUnit)        extends Repetition
  final case class RepeatUntil(end: ZonedDateTime, by: RepetitionUnit) extends Repetition

  override val jsonEncoder: Encoder[Repetition] = {
    implicit val repetitionUnitEncoder: Encoder[RepetitionUnit] = RepetitionUnit.jsonEncoder

    ObjectEncoder.instance[Repetition] {
      case NoRepetition =>
        JsonObject.empty

      case r: RepeatNTimes =>
        Encoder.forProduct2[Int, RepetitionUnit, RepeatNTimes]("times", "by")(r => r.times -> r.by).encodeObject(r)

      case r: RepeatUntil =>
        Encoder.forProduct2[ZonedDateTime, RepetitionUnit, RepeatUntil]("end", "by")(r => r.end -> r.by).encodeObject(r)
    }
  }

  override val jsonDecoder: Decoder[Repetition] = {
    implicit val repetitionUnitDecoder: Decoder[RepetitionUnit] = RepetitionUnit.jsonDecoder

    Decoder.instanceTry[Repetition] { cursor =>
      val maybeRepetition = for {
        times <- cursor.downField("times").as[Int]
        by    <- cursor.downField("by").as[RepetitionUnit]
      } yield RepeatNTimes(times, by)

      maybeRepetition.toTry
    } or Decoder.instanceTry[Repetition] { cursor =>
      val maybeRepetition = for {
        end <- cursor.downField("end").as[ZonedDateTime]
        by  <- cursor.downField("by").as[RepetitionUnit]
      } yield RepeatUntil(end, by)

      maybeRepetition.toTry
    } or Decoder.instanceTry { cursor =>
      cursor.as[JsonObject].toTry.filter(_.isEmpty).map(_ => NoRepetition)
    }
  }
}
