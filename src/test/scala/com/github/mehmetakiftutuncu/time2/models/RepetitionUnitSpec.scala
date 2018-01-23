package com.github.mehmetakiftutuncu.time2.models

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class RepetitionUnitSpec extends UnitSpec {
  val minutes = RepetitionUnit.Minutes(6)
  val hours   = RepetitionUnit.Hours(5)
  val days    = RepetitionUnit.Days(4)
  val weeks   = RepetitionUnit.Weeks(3)
  val months  = RepetitionUnit.Months(2)
  val years   = RepetitionUnit.Years(1)

  "RepetitionUnit" should {
    "be converted to Json properly" when {
      "it is Minutes" in {
        val expectedJson = s"""{"unit":"minutes","amount":6}"""

        minutes.toJson.noSpaces shouldBe expectedJson
      }

      "it is Hours" in {
        val expectedJson = s"""{"unit":"hours","amount":5}"""

        hours.toJson.noSpaces shouldBe expectedJson
      }

      "it is Days" in {
        val expectedJson = s"""{"unit":"days","amount":4}"""

        days.toJson.noSpaces shouldBe expectedJson
      }

      "it is Weeks" in {
        val expectedJson = s"""{"unit":"weeks","amount":3}"""

        weeks.toJson.noSpaces shouldBe expectedJson
      }

      "it is Months" in {
        val expectedJson = s"""{"unit":"months","amount":2}"""

        months.toJson.noSpaces shouldBe expectedJson
      }

      "it is Years" in {
        val expectedJson = s"""{"unit":"years","amount":1}"""

        years.toJson.noSpaces shouldBe expectedJson
      }
    }
  }

  "Converting from Json" should {
    "fail for invalid Json" in {
      val notARepetitionUnitJson = Json.obj("foo" -> Json.fromString("bar"))

      RepetitionUnit.fromJson(notARepetitionUnitJson).isLeft shouldBe true
    }

    "work correctly for a valid Json" when {
      "it is a Minutes Json" in {
        val minutesJson = Json.obj("unit" -> "minutes".asJson, "amount" -> 6.asJson)

        RepetitionUnit.fromJson(minutesJson).right.get shouldBe minutes
      }

      "it is a Hours Json" in {
        val hoursJson = Json.obj("unit" -> "hours".asJson, "amount" -> 5.asJson)

        RepetitionUnit.fromJson(hoursJson).right.get shouldBe hours
      }

      "it is a Days Json" in {
        val daysJson = Json.obj("unit" -> "days".asJson, "amount" -> 4.asJson)

        RepetitionUnit.fromJson(daysJson).right.get shouldBe days
      }

      "it is a Weeks Json" in {
        val weeksJson = Json.obj("unit" -> "weeks".asJson, "amount" -> 3.asJson)

        RepetitionUnit.fromJson(weeksJson).right.get shouldBe weeks
      }

      "it is a Months Json" in {
        val monthsJson = Json.obj("unit" -> "months".asJson, "amount" -> 2.asJson)

        RepetitionUnit.fromJson(monthsJson).right.get shouldBe months
      }

      "it is a Years Json" in {
        val yearsJson = Json.obj("unit" -> "years".asJson, "amount" -> 1.asJson)

        RepetitionUnit.fromJson(yearsJson).right.get shouldBe years
      }
    }
  }
}
