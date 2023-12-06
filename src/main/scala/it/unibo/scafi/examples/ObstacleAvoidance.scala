package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

class ObstacleAvoidance extends BaseMovement {
  override protected def movementLogic(): Point3D = {
    val obstacles = excludingSelf.reifyField((nbr(sense[Boolean]("obstacle")), nbrVector()))
    val obstaclesPerceived = obstacles.filter(_._2._1).values.map(_._2).toSeq
    node.put("obstacles", obstacles)
    rep(Point3D.Zero)(old => {
      mux(!sense[Boolean]("obstacle"))(
        separation(old, OneHopNeighbourhoodWithinRange(30)) + obstacleAvoidance(
          obstaclesPerceived,
          minDistance = 200,
          50
        ) + goto(Point3D(1000, 1000, 0), maxVelocity = 1)
      )(Point3D.Zero).normalize
    })
  }
}
