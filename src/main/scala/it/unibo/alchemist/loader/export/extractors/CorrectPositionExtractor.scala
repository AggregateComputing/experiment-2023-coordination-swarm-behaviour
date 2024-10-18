package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.boundary.Extractor
import it.unibo.alchemist.model.molecules.SimpleMolecule
import it.unibo.alchemist.model.{Actionable, Environment, Node, Position2D, Time}
import it.unibo.scafi.space.Point3D

import java.util
import scala.jdk.CollectionConverters.{IteratorHasAsScala, ListHasAsScala}

class CorrectPositionExtractor() extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("errors")
  override def extractData[T](
      environment: Environment[T, _],
      actionable: Actionable[T],
      time: Time,
      l: Long
  ): util.Map[String, Double] = {
    type EnvironmentType = Environment[T, Position2D[_]]
    val typedEnv = environment.asInstanceOf[EnvironmentType]
    val leaderNode: Option[Node[T]] = typedEnv.getNodes
      .iterator()
      .asScala
      .filter(node => node.getConcentration(new SimpleMolecule("lead")).asInstanceOf[Boolean])
      .collectFirst(identity(_))

    leaderNode match {
      case Some(leader) =>
        val leaderPosition = typedEnv.getPosition(leader)
        val requestedPosition =
          leader.getConcentration(new SimpleMolecule("suggestions")).asInstanceOf[Map[Int, Point3D]]

        val errorWithRespectToTask = requestedPosition.map { case (id, pos) =>
          val node = environment.getNodeByID(id)
          val nodePosition = typedEnv.getPosition(node)
          val suggested = typedEnv.makePosition(pos.x + leaderPosition.getX, pos.y + leaderPosition.getY)
          math.sqrt(math.pow(nodePosition.getX - suggested.getX, 2) + math.pow(nodePosition.getY - suggested.getY, 2))
        }
        java.util.Map.of("errors", errorWithRespectToTask.count(_ > 5).toDouble)
      case None =>
        java.util.Map.of("errors", 0.0)
    }
  }
}
