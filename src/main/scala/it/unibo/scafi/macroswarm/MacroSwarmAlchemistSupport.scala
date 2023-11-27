package it.unibo.scafi.macroswarm
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist
import it.unibo.scafi.macroswarm
object MacroSwarmAlchemistSupport extends MacroSwarmSupport(ScafiIncarnationForAlchemist) {
  implicit override def ordering: Ordering[Int] = Ordering.Int
}
