package com.github.mehmetakiftutuncu.time2.models

import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class RepetitionSpec extends UnitSpec {
  val end          = ZonedDateTime.of(2018, 1, 24, 22, 51, 17, 0, ZoneId.ofOffset("", ZoneOffset.ofHours(3)))
  val noRepetition = Repetition.NoRepetition
  val repeatNTimes = Repetition.RepeatNTimes(1, RepetitionUnit.Days(3))
  val repeatUntil  = Repetition.RepeatUntil(end, RepetitionUnit.Hours(1))

  "Repetition" should {
    "be converted to Json properly" when {
      "it is NoRepetition" in {
        val expectedJson = s"""{}"""

        noRepetition.toJson.noSpaces shouldBe expectedJson
      }

      "it is RepeatNTimes" in {
        val expectedJson = s"""{"times":1,"by":${repeatNTimes.by}}"""

        repeatNTimes.toJson.noSpaces shouldBe expectedJson
      }

      "it is RepeatUntil" in {
        val expectedJson = s"""{"end":"2018-01-24T22:51:17+03:00","by":${repeatUntil.by}}"""

        repeatUntil.toJson.noSpaces shouldBe expectedJson
      }
    }
  }

  "Converting from Json" should {
    "fail for invalid Json" in {
      val notARepetitionUnitJson = Json.obj("foo" -> Json.fromString("bar"))

      RepetitionUnit.fromJson(notARepetitionUnitJson).isLeft shouldBe true
    }

    "work correctly for a valid Json" when {
      "it is a NoRepetition Json" in {
        val noRepetitionJson = Json.obj()

        Repetition.fromJson(noRepetitionJson).right.get shouldBe noRepetition
      }

      "it is a RepeatNTimes Json" in {
        val repeatNTimesJson = Json.obj("times" -> 1.asJson, "by" -> repeatNTimes.by.toJson)

        Repetition.fromJson(repeatNTimesJson).right.get shouldBe repeatNTimes
      }

      "it is a RepeatUntil Json" in {
        val repeatUntilJson = Json.obj("end" -> end.asJson, "by" -> repeatUntil.by.toJson)

        Repetition.fromJson(repeatUntilJson).right.get shouldBe repeatUntil
      }
    }
  }
}
