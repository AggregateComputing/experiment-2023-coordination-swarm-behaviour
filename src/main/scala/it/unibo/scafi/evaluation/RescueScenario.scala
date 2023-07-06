package it.unibo.scafi.evaluation

import it.unibo.alchemist.model.implementations.molecules
import it.unibo.alchemist.model.implementations.molecules.SimpleMolecule
import it.unibo.alchemist.model.implementations.nodes.SimpleNodeManager
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ID
import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.jdk.CollectionConverters.IterableHasAsScala

class RescueScenario extends BaseMovement {
  private lazy val manager = node.asInstanceOf[SimpleNodeManager[Any]]

  def healer: Boolean = node.get[Boolean]("healer")

  val bound = Point3D(1000, 1000, 0)
  val circleRadius = 50
  val minimumDistance = 20
  val viewRadius = 100
  val confidence = 5
  val healingTime = 10

  def createTeam = teamFormation(
    healer,
    minimumDistance,
    2,
    leader => isTeamFormed(leader, minimumDistance + confidence) & countIn(leader) > 3
  )

  def inDangerDirection: Option[Point3D] = {
    val dangerMolecule = new SimpleMolecule("danger")
    val friends = alchemistEnvironment.getNeighborhood(manager.node)
    val inDanger = friends.asScala
      .filter(node => node.contains(dangerMolecule))
      .filter(_.getConcentration(dangerMolecule).asInstanceOf[Boolean])

    val inDangerPosition = inDanger
      .map(node => alchemistEnvironment.getPosition(node).getCoordinates)
      .map(position => Point3D(position(0), position(1), 0))
      .toList
      .filter(p => p.distance(currentPosition()) < viewRadius)
      .sortBy(_.distance(currentPosition()))
      .headOption

    inDangerPosition
  }

  def heal(healerId: ID, target: Point3D): Point3D = {
    branch(healerId == mid() && isClose(target)) {
      val neighbours = alchemistEnvironment.getNeighborhood(manager.node)
      val inDanger = neighbours.asScala
        .filter(node => node.contains(new SimpleMolecule("danger")))
        .filter(_.getConcentration(new SimpleMolecule("danger")).asInstanceOf[Boolean])
      inDanger.headOption.foreach(node =>
        // alchemistEnvironment.getSimulation.schedule(() => alchemistEnvironment.removeNode(node))
        node.setConcentration(new molecules.SimpleMolecule("danger"), false)
      )

    } {}
    Point3D.Zero
  }

  def healed(): Boolean = true // !danger

  def formation(leading: Boolean): Point3D =
    centeredCircle(leading, circleRadius, confidence, Point3D.Zero)

  def wanderInFormation(leading: Boolean): Point3D =
    centeredCircle(leading, circleRadius, confidence, explore(Point3D.Zero, bound, 1))

  def goToHealInFormation(leading: Boolean, target: Point3D): Point3D = {
    node.put("target", target)
    centeredCircle(leading, circleRadius, confidence, goto(target))
  }

  def circleOk(leading: Boolean): Boolean = isCircleFormed(leading, circleRadius, confidence * 2)

  def insideTeamPlanning(team: Team): Point3D =
    team.insideTeam { k =>
      val leading = k == mid()
      val potential = fastGradient(leading, nbrRange)
      val inDanger =
        C[Double, Option[Point3D]](potential, nearestPointFromOption, inDangerDirection, None).getOrElse(Point3D.Zero)
      node.put("inDanger", inDanger)
      val dangerFound = broadcast(leading, Point3D.Zero != inDanger)

      val dangerReached = broadcast(leading, isClose(inDanger))
      //val toHeal = dangerReached && dangerFound
      val circleIsFormed = circleOk(leading)
      execute
        .repeat(
          plan(formation(leading)).endWhen(circleIsFormed),
          plan(wanderInFormation(leading)).endWhen(dangerFound),
          plan(goToHealInFormation(leading, inDanger)).endWhen(dangerReached),
          plan(heal(k, inDanger)).endWhen(healed)
        )
        .run(leading)
    }

  override protected def movementLogic(): Point3D = {
    val obstacles = excludingSelf.reifyField((nbr(node.getOption[Boolean]("obstacle").isDefined), nbrVector()))
    val obstaclesPerceived = obstacles.filter(_._2._1).values.map(_._2).toSeq
    branch(node.getOption("obstacle").isDefined) {
      Point3D.Zero
    } {
      val team = createTeam
      val velocity = rep(Point3D.Zero) { old =>
        insideTeamPlanning(team) +
          separation(old, OneHopNeighbourhoodWithinRange(5)).normalize
      } +
        obstacleAvoidance(obstaclesPerceived, 70, 50)
      val distancesIntraTeam = excludingSelf
        .reifyField((nbrRange(), nbr(sense[Int]("team"))))
        .filter(_._2._2 == mid())
        .values
        .map(_._1)

      if (healer) {
        node.put("avgDistanceTeam", distancesIntraTeam.sum / distancesIntraTeam.size)
      }
      val minDistance = excludingSelf.reifyField(nbrRange()).minOption.map(_._2).getOrElse(Double.PositiveInfinity)
      node.put("minDistance", minDistance)
      mux(healer)(velocity)(velocity * 2)
    }
  }

  private def nearestPointFromOption(left: Option[Point3D], right: Option[Point3D]): Option[Point3D] =
    (left, right) match {
      case (Some(l), Some(r)) => Some(if (l.distance(currentPosition()) < r.distance(currentPosition())) l else r)
      case (Some(l), None) => Some(l)
      case (None, Some(r)) => Some(r)
      case (None, None) => None
    }
}
