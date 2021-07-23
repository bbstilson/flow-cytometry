// format: off
import $ivy.`io.github.davidgregory084::mill-tpolecat:0.2.0`

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import io.github.davidgregory084.TpolecatModule

object flow_cytometry extends ScalaModule with ScalafmtModule with TpolecatModule {
  def scalaVersion = "3.0.0"
  
  def scalacOptions = super
    .scalacOptions()
    .filterNot(Set("-Xsource:3", "-migration"))

  def ivyDeps = Agg(
    ivy"org.scodec::scodec-bits:1.1.27",
    ivy"org.scodec::scodec-core:2.0.0",
  )
}

