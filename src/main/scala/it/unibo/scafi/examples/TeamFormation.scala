package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class TeamFormation extends BaseMovement {
  override protected def movementLogic() = {
    // a team is formed when the minimum distance between nodes is around 40 meters and the group has at least 3 nodes
    val team = teamFormation( // internally it uses S
      targetIntraDistance = 30,
      targetExtraDistance = 300, // influence of the leader
      separationWeight = 2,
      condition = leader => isTeamFormed(leader, 40) & countIn(leader) > 3
    )
    node.put("leader", mid() == team.leader)

    team.insideTeam(k => alignWithLeader(mid() == k, remember(brownian()))).normalize
  }

  private def countIn(source: Boolean): Int = {
    val potential = fastGradient(source, nbrRange)
    val count = CWithShare[Double, Int](potential, _ + _, 1, 0)
    broadcastAlongWithShare(potential, count, nbrRange)
  }
}
