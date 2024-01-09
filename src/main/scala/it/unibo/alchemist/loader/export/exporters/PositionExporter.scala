package it.unibo.alchemist.loader.`export`.exporters

import it.unibo.alchemist.model.interfaces.{Actionable, Environment, Position, Time}
import java.awt.image.BufferedImage
import upickle.default.{macroRW, ReadWriter => RW}
import upickle.default._
class PositionExporter[T, P <: Position[P]](
   val path: String,
   val name: String,
    val samplingInterval: Double
) extends AbstractExporter[T, P](samplingInterval: Double) {
  private var positions = Map[Int, List[(String, String)]]()
  override def exportData(environment: Environment[T, P], actionable: Actionable[T], time: Time, l: Long): Unit = {
    val nodes = environment.getNodes
    nodes.forEach(node => {
      val id = node.getId
      val position = environment.getPosition(node)
      val x = f"${position.getCoordinate(0)}%1.2f"
      val y = f"${position.getCoordinate(1)}%1.2f"
      val currentPositions = positions.getOrElse(id, List.empty)
      positions = positions + (id -> ((x, y) :: currentPositions))
    })
  }

  override def close(environment: Environment[T, P], time: Time, l: Long): Unit = {
    println(write(positions))
    println("Closing")
  }

  override def setup(environment: Environment[T, P]): Unit = {
    println("Setting up")
  }
}
