package it.unibo.alchemist.model.implementations.effects

import it.unibo.alchemist.boundary.swingui.effect.api.Effect
import it.unibo.alchemist.boundary.ui.api.Wormhole2D
import it.unibo.alchemist.model.implementations.effects.MacroSwarmEffect._
import it.unibo.alchemist.model.implementations.nodes._
import it.unibo.alchemist.model.{Environment, Node, Position, Position2D}
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D
import org.danilopianini.lang.RangedInteger
import org.danilopianini.view.ExportForGUI

import java.awt._
import java.awt.geom._

/** ad-hoc effect to draw drones, animal and station. */
class MacroSwarmEffect extends Effect {
  @ExportForGUI(nameToExport = "Track")
  private val trackEnabled: Boolean = true

  @ExportForGUI(nameToExport = "SnapshotSize")
  val snapshotSize: RangedInteger = new RangedInteger(10, MAX_LENGTH, LENGTH)

  @ExportForGUI(nameToExport = "SnapshotFrequency")
  val timespan: RangedInteger = new RangedInteger(1, 100, CLOCK)

  @ExportForGUI(nameToExport = "NodeSize")
  val nodeSize: RangedInteger = new RangedInteger(1, 20, DRONE_SIZE.toInt)

  override def apply[T, P <: Position2D[P]](
      g: Graphics2D,
      node: Node[T],
      env: Environment[T, P],
      wormhole: Wormhole2D[P]
  ): Unit = {
    val nodePosition: P = env.getPosition(node)
    val viewPoint: Point = wormhole.getViewPoint(nodePosition)
    val (x, y) = (viewPoint.x, viewPoint.y)
    drawArrow(g, node, x, y, wormhole.getZoom, env, wormhole)
  }

  def getColorSummary: Color = Color.BLACK

  def drawArrow[T, P <: Position2D[P]](
      g: Graphics2D,
      droneNode: Node[T],
      x: Int,
      y: Int,
      zoom: Double,
      env: Environment[T, P],
      wormhole: Wormhole2D[P]
  ): Unit = {
    val currentRotation = rotation(droneNode)
    val transform = getTransform(x, y, nodeSize.getVal, currentRotation)
    val shape = DRONE_SHAPE
    val color = droneColor(droneNode.getId, env.getNodeCount)
    val transformedShape = transform.createTransformedShape(shape)
    val nodeManager = new SimpleNodeManager[T](droneNode)
    val positions = nodeManager.getOption[Seq[(P, Double)]]("positions").getOrElse(Seq.empty)
    val lastDraw = nodeManager.getOption("lastDraw").getOrElse(0)
    val alpha = 255.0 / ((Math.min(snapshotSize.getVal, positions.size) * 4) + 1)

    positions.filter(_ => trackEnabled).takeRight(snapshotSize.getVal).zipWithIndex.foreach {
      case ((nodePosition, rotation), index) =>
        val colorFaded =
          new Color(color.getRed, color.getGreen, color.getBlue, Math.max(1, (alpha * (index + 1)).toInt))
        val viewPoint: Point = wormhole.getViewPoint(nodePosition)
        val (x, y) = (viewPoint.x, viewPoint.y)
        val transform = getTransform(x, y, nodeSize.getVal, rotation)
        val transformedShape = transform.createTransformedShape(shape)
        g.setColor(colorFaded)
        g.fill(transformedShape)
    }
    if (nodeManager.getOption("danger").contains(true)) {
      g.setColor(Color.RED)
      g.fill(transform.createTransformedShape(new Ellipse2D.Double(-3, -3, 3, 3)))
    } else if (nodeManager.getOption("danger").isEmpty) {
      g.setColor(color)
      g.fill(transformedShape)
      if (nodeManager.getOption[Boolean]("healer").contains(true)) {
        g.setStroke(new BasicStroke(2))
        g.setColor(Color.BLACK)
        g.draw(transformedShape)
      }
      val roundedTick = env.getSimulation.getTime.toDouble.toInt
      if (roundedTick >= lastDraw) {
        nodeManager.put(
          "positions",
          (positions :+ (env.getPosition(droneNode), currentRotation)).takeRight(MAX_LENGTH)
        )
        nodeManager.put("lastDraw", lastDraw + timespan.getVal)
      }
    }

  }

  private def rotation[T](node: Node[T]): Double = {
    val nodeManager = new SimpleNodeManager[T](node)
    val velocity = nodeManager.getOption("velocity").getOrElse(Point3D(1, 0, 0))
    val smoothed = nodeManager
      .getOption[Point3D]("old")
      .map(_ + (velocity / SMOOTHING_FACTOR))
      .map(_.normalize)
      .getOrElse(velocity)
    nodeManager.put("old", smoothed)
    math.atan2(smoothed.x, smoothed.y)
  }

  private def getTransform(x: Int, y: Int, zoom: Double, rotation: Double): AffineTransform = {
    val transform = new AffineTransform()
    transform.translate(x, y)
    transform.scale(zoom, zoom)
    transform.rotate(rotation)
    transform
  }
}

object MacroSwarmEffect {
  val CLOCK: Int = 10

  val LENGTH: Int = 140

  val MAX_LENGTH: Int = 1000

  val MAX_COLOR: Int = 255

  val TRANSPARENT = new Color(255, 255, 255, 0)

  val DRONE_SHAPE: Polygon = new Polygon(Array(-1, 0, 1), Array(2, -2, 2), 3)

  val DRONE_SIZE = 4.0

  val DRONE_COLOR: Color = Color.BLACK

  val SMOOTHING_FACTOR = 20.0

  def droneColor(id: Int, howMany: Int): Color = new Color(
    Color.HSBtoRGB(id.toFloat / howMany.toFloat, 1, 0.8f)
  )
}
