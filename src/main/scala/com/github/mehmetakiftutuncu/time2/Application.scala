package com.github.mehmetakiftutuncu.time2

object Application {
  def main(args: Array[String]): Unit = {
    val config: Config = pureconfig.loadConfigOrThrow[Config]("com.github.mehmetakiftutuncu.time2")

    println(config.key)
  }
}
