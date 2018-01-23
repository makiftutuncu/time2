package com.github.mehmetakiftutuncu.time2.models

import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder}

import scala.annotation.tailrec

final case class Plan(start: ZonedDateTime, repetition: Repetition) extends Model[Plan] {
  override implicit val jsonEncoder: Encoder[Plan] = Plan.jsonEncoder

  val instances: List[ZonedDateTime] = repetition match {
    case Repetition.NoRepetition =>
      List(start)

    case Repetition.RepeatNTimes(times, by) =>
      (1 to times).foldLeft(List(start)) {
        case (results, _) => results :+ by.next(results.last)
      }

    case Repetition.RepeatUntil(end, by) =>
      @tailrec
      def go(list: List[ZonedDateTime]): List[ZonedDateTime] =
        if (list.lastOption.exists(_.isAfter(end))) {
          list.dropRight(1)
        } else {
          go(list :+ by.next(list.last))
        }

      go(List(start))
  }
}

object Plan extends JsonSupport[Plan] {
  override val jsonEncoder: Encoder[Plan] = {
    implicit val repetitionEncoder: Encoder[Repetition] = Repetition.jsonEncoder

    Encoder.forProduct2("start", "repetition")(p => p.start -> p.repetition)
  }

  override val jsonDecoder: Decoder[Plan] = {
    implicit val repetitionDecoder: Decoder[Repetition] = Repetition.jsonDecoder

    Decoder.forProduct2("start", "repetition")(Plan.apply)
  }
}
