package com.github.mehmetakiftutuncu.time2.models

import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class PlanSpec extends UnitSpec {
  val start        = ZonedDateTime.of(2018, 1, 24, 0, 0, 0, 0, ZoneId.ofOffset("", ZoneOffset.ofHours(3)))
  val end          = ZonedDateTime.of(2018, 1, 25, 0, 0, 0, 0, ZoneId.ofOffset("", ZoneOffset.ofHours(3)))
  val noRepetition = Repetition.NoRepetition
  val repeatNTimes = Repetition.RepeatNTimes(3, RepetitionUnit.Days(3))
  val repeatUntil  = Repetition.RepeatUntil(end, RepetitionUnit.Hours(4))

  val noRepetitionPlan = Plan(1, start, noRepetition)
  val repeatNTimesPlan = Plan(2, start, repeatNTimes)
  val repeatUntilPlan  = Plan(3, start, repeatUntil)

  "Plan" should {
    "be converted to Json properly" in {
      val expectedJson = s"""{"id":1,"start":"2018-01-24T00:00:00+03:00","repetition":$noRepetition}"""

      noRepetitionPlan.toJson.noSpaces shouldBe expectedJson
    }
  }

  "Converting from Json" should {
    "fail for invalid Json" in {
      val notAPlanJson = Json.obj("foo" -> Json.fromString("bar"))

      Plan.fromJson(notAPlanJson).isLeft shouldBe true
    }

    "work correctly for a valid Json" in {
      val repeatNTimesPlanJson = Json.obj(
        "id"         -> 2.asJson,
        "start"      -> start.asJson,
        "repetition" -> repeatNTimes.toJson
      )

      Plan.fromJson(repeatNTimesPlanJson).right.get shouldBe repeatNTimesPlan
    }
  }

  "List of instances of a Plan" should {
    "contain only start for NoRepetition Plan" in {
      noRepetitionPlan.instances shouldBe List(start)
    }

    "contain instances of size 'times', starting from 'start' and incrementing by 'by' for RepeatNTimes Plan" in {
      repeatNTimesPlan.instances shouldBe List(
        start,
        start.plusDays(3),
        start.plusDays(6),
        start.plusDays(9)
      )
    }

    "contain instances starting from 'start', ending at 'end' and incrementing by 'by' for RepeatUntil Plan" in {
      repeatUntilPlan.instances shouldBe List(
        start,
        start.plusHours(4),
        start.plusHours(8),
        start.plusHours(12),
        start.plusHours(16),
        start.plusHours(20),
        start.plusHours(24)
      )
    }
  }
}
