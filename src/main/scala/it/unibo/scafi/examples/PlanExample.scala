package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

class PlanExample extends BaseMovement {
  override protected def movementLogic(): Point3D = {
    val leader = mid() == 1
    val goToTop = alignWithLeader(leader, goto(Point3D(1000, 1000, 0)))
    val goToBottom = alignWithLeader(leader, goto(Point3D(0, 0, 0)))
    val goal = execute
      .once(
        plan {
          sinkAt(leader)
        }.endWhen {
          isTeamFormed(leader, 100, necessary = 2)
        },
        plan(goToTop).endWhen {
          broadcast(leader, isClose(Point3D(1000, 1000, 0)))
        },
        plan(goToBottom).endWhen {
          broadcast(leader, isClose(Point3D(0, 0, 0)))
        }
      )
      .run()
    rep(Point3D.Zero) { velocity =>
      (separation(velocity, OneHopNeighbourhoodWithinRange(90)) + goal).normalize
    }
  }
}
