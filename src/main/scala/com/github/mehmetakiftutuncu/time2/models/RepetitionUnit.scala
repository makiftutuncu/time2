package com.github.mehmetakiftutuncu.time2.models

import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder, ObjectEncoder}

sealed trait RepetitionUnit extends Model[RepetitionUnit] {
  val amount: Int
  val unitName: String

  protected val adder: ZonedDateTime => Long => ZonedDateTime

  def next(to: ZonedDateTime): ZonedDateTime = adder(to)(amount)

  override implicit val jsonEncoder: Encoder[RepetitionUnit] = RepetitionUnit.jsonEncoder
}

object RepetitionUnit extends JsonSupport[RepetitionUnit] {
  final case class Minutes(amount: Int) extends RepetitionUnit {
    override val unitName: String = "minutes"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusMinutes
  }

  final case class Hours(amount: Int) extends RepetitionUnit {
    override val unitName: String = "hours"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusHours
  }

  final case class Days(amount: Int) extends RepetitionUnit {
    override val unitName: String = "days"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusDays
  }

  final case class Weeks(amount: Int) extends RepetitionUnit {
    override val unitName: String = "weeks"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusWeeks
  }

  final case class Months(amount: Int) extends RepetitionUnit {
    override val unitName: String = "months"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusMonths
  }

  final case class Years(amount: Int) extends RepetitionUnit {
    override val unitName: String = "years"
    override protected val adder: ZonedDateTime => Long => ZonedDateTime = _.plusYears
  }

  override val jsonEncoder: Encoder[RepetitionUnit] =
    ObjectEncoder.instance[RepetitionUnit] {
      case ru: Minutes => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
      case ru: Hours   => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
      case ru: Days    => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
      case ru: Weeks   => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
      case ru: Months  => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
      case ru: Years   => Encoder.forProduct2[String, Int, RepetitionUnit]("unit", "amount")(ru => ru.unitName -> ru.amount).encodeObject(ru)
    }

  override val jsonDecoder: Decoder[RepetitionUnit] =
    Decoder.instanceTry[RepetitionUnit] { cursor =>
      val maybeRepetitionUnit = for {
        amount   <- cursor.downField("amount").as[Int]
        unitName <- cursor.downField("unit").as[String]
      } yield forUnit(unitName)(amount)

      maybeRepetitionUnit.toTry
    }

  private def forUnit(unit: String): Int => RepetitionUnit =
    unit match {
      case "minutes" => Minutes
      case "hours"   => Hours
      case "days"    => Days
      case "weeks"   => Weeks
      case "months"  => Months
      case "years"   => Years
    }
}
