package it.unibo.alchemist.model.actions

import com.google.common.collect.ImmutableList
import it.unibo.alchemist.model.{Environment, Molecule, Node, Position2D, Reaction}
import it.unibo.alchemist.model.movestrategies.speed.{ConstantSpeed, MutableSpeed}
import it.unibo.alchemist.model.movestrategies.target.FollowTarget
import it.unibo.alchemist.model.routes.PolygonalChain
import org.apache.commons.math3.util.FastMath

import scala.util.Try

final class MoveToTargetWithSpeed[T, P <: Position2D[P]](
    environment: Environment[T, P],
    node: Node[T],
    reaction: Reaction[T],
    trackMolecule: Molecule,
    speedMolecule: Molecule
) extends AbstractConfigurableMoveNode[T, P](
      environment,
      node,
      (p1: P, p2: P) => new PolygonalChain(ImmutableList.of(p1, p2)),
      new FollowTarget(environment, node, trackMolecule),
      MutableSpeed(speedMolecule, node, reaction)
    ) {

  override def cloneAction(node: Node[T], reaction: Reaction[T]): MoveToTargetWithSpeed[T, P] =
    new MoveToTargetWithSpeed(environment, node, reaction, trackMolecule, speedMolecule)

  override protected def interpolatePositions(current: P, target: P, maxWalk: Double): P = {
    val vector = target.minus(current.getCoordinates) // Assuming Position2D has a minus method
    if (current.distanceTo(target) < maxWalk) {
      vector.asInstanceOf[P] // Assuming Position2D has a correct type
    } else {
      val angle = FastMath.atan2(vector.getY, vector.getX)
      environment
        .makePosition(maxWalk * FastMath.cos(angle), maxWalk * FastMath.sin(angle))
        .asInstanceOf[P] // Safe cast?
    }
  }
}
