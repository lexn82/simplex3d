package example.simplex3d.lines

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.doublex.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.console.extension.ImageUtils._


/**
 * @author Aleksey Nikiforov (lex)
 */
object Lines extends App {

{
  val lines = DataArray[Vec2, RFloat](Vec2(100, 100), Vec2(100, 200))
  val colors = DataArray[Vec3, UByte](Vec3(1, 0, 0), Vec3(0, 1, 0))

  drawLines("A Simple Line", Vec3(0)) { (dims) =>
    (lines, colors, lines.size)
  }
}

}
