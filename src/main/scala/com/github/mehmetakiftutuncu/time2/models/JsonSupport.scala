package com.github.mehmetakiftutuncu.time2.models

import io.circe.{Decoder, Encoder, Json}

trait JsonSupport[T] { self =>
  val jsonEncoder: Encoder[T]
  val jsonDecoder: Decoder[T]

  def toJson(model: T): Json                    = jsonEncoder(model)
  def fromJson(json: Json): Decoder.Result[T]   = jsonDecoder.decodeJson(json)
}
