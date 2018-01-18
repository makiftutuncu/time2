package com.github.mehmetakiftutuncu.time2.models

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class UserSpec extends UnitSpec {
  val user: User = User(1, "akif@time2.com")

  "User" should {
    "be converted to Json properly" in {
      val expectedJson = """{"id":1,"email":"akif@time2.com"}"""

      user.toJson.noSpaces shouldBe expectedJson
    }
  }

  "Converting from Json" should {
    "fail for invalid Json" in {
      val notAUserJson = Json.obj("foo" -> Json.fromString("bar"))

      User.fromJson(notAUserJson).isLeft shouldBe true
    }

    "work correctly for a valid Json" in {
      val userJson = Json.obj(
        "id"    -> 1.asJson,
        "email" -> "akif@time2.com".asJson
      )

      User.fromJson(userJson).right.get shouldBe user
    }
  }
}
