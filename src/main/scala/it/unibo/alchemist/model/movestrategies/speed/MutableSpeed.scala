/*
 * Copyright (C) 2010-2023, Danilo Pianini and contributors
 * listed, for each module, in the respective subproject's build.gradle.kts file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */
package it.unibo.alchemist.model.movestrategies.speed

import it.unibo.alchemist.model.{Molecule, Node, Position, Reaction}
import it.unibo.alchemist.model.movestrategies.SpeedSelectionStrategy

import scala.annotation.nowarn

@nowarn("cat=deprecation")
case class MutableSpeed[T, P <: Position[P]](speedMolecule: Molecule, node: Node[T], reaction: Reaction[T])
    extends SpeedSelectionStrategy[T, P]
    with Serializable {

  override def getNodeMovementLength(target: P): Double = {
    val speed = node.getConcentration(speedMolecule).asInstanceOf[Double]
    speed / reaction.getRate
  }

  override def cloneIfNeeded(destination: Node[T], reaction: Reaction[T]): MutableSpeed[T, P] =
    MutableSpeed(speedMolecule, node, reaction)
}
