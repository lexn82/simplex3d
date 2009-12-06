/*
 * Simplex3D, DoubleMath module
 * Copyright (C) 2009 Simplex3D team
 *
 * This file is part of Simplex3d.
 *
 * Simplex3d is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3d is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.math.doublem

import simplex3d.math._
import Read._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyMat3x4d
extends ConstRotationSubMat3d
{
    // Column major order.
    def m00: Double; def m10: Double; def m20: Double // column
    def m01: Double; def m11: Double; def m21: Double // column
    def m02: Double; def m12: Double; def m22: Double // column
    def m03: Double; def m13: Double; def m23: Double // column

    def apply(c: Int) :ConstVec3d = {
        c match {
            case 0 => ConstVec3d(m00, m10, m20)
            case 1 => ConstVec3d(m01, m11, m21)
            case 2 => ConstVec3d(m02, m12, m22)
            case 3 => ConstVec3d(m03, m13, m23)
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }

    def apply(c: Int, r: Int) :Double = {
        def error() :Double = {
            throw new IndexOutOfBoundsException("Trying to read index (" +
                     c + ", " + r + ") in " + this.getClass.getSimpleName)
        }

        c match {
            case 0 =>
                r match {
                    case 0 => m00
                    case 1 => m10
                    case 2 => m20
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01
                    case 1 => m11
                    case 2 => m21
                    case _ => error
                }
            case 2 =>
                r match {
                    case 0 => m02
                    case 1 => m12
                    case 2 => m22
                    case _ => error
                }
            case 3 =>
                r match {
                    case 0 => m03
                    case 1 => m13
                    case 2 => m23
                    case _ => error
                }
            case _ => error
        }
    }

    def unary_-() = Mat3x4d(
        -m00, -m10, -m20,
        -m01, -m11, -m21,
        -m02, -m12, -m22,
        -m03, -m13, -m23
    )
    def *(s: Double) = Mat3x4d(
        s*m00, s*m10, s*m20,
        s*m01, s*m11, s*m21,
        s*m02, s*m12, s*m22,
        s*m03, s*m13, s*m23
    )
    def /(s: Double) = { val inv = 1/s; Mat3x4d(
        inv*m00, inv*m10, inv*m20,
        inv*m01, inv*m11, inv*m21,
        inv*m02, inv*m12, inv*m22,
        inv*m03, inv*m13, inv*m23
    )}

    def +(m: AnyMat3x4d) = Mat3x4d(
        m00 + m.m00, m10 + m.m10, m20 + m.m20,
        m01 + m.m01, m11 + m.m11, m21 + m.m21,
        m02 + m.m02, m12 + m.m12, m22 + m.m22,
        m03 + m.m03, m13 + m.m13, m23 + m.m23
    )
    def -(m: AnyMat3x4d) = Mat3x4d(
        m00 - m.m00, m10 - m.m10, m20 - m.m20,
        m01 - m.m01, m11 - m.m11, m21 - m.m21,
        m02 - m.m02, m12 - m.m12, m22 - m.m22,
        m03 - m.m03, m13 - m.m13, m23 - m.m23
    )

    /**
     * Component-wise devision.
     */
    def /(m: AnyMat3x4d) = Mat3x4d(
        m00/m.m00, m10/m.m10, m20/m.m20,
        m01/m.m01, m11/m.m11, m21/m.m21,
        m02/m.m02, m12/m.m12, m22/m.m22,
        m03/m.m03, m13/m.m13, m23/m.m23
    )
    private[math] def divideByComponent(s: Double) = Mat3x4d(
        s/m00, s/m10, s/m20,
        s/m01, s/m11, s/m21,
        s/m02, s/m12, s/m22,
        s/m03, s/m13, s/m23
    )

    def *(m: AnyMat4x2d) = Mat3x2d(
        m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
        m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
        m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

        m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
        m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
        m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31
    )
    def *(m: AnyMat4x3d) = Mat3d(
        m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
        m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
        m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

        m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
        m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
        m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31,

        m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32,
        m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32,
        m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32
    )
    def *(m: AnyMat4d) = Mat3x4d(
        m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
        m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
        m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

        m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
        m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
        m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31,

        m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32,
        m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32,
        m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32,

        m00*m.m03 + m01*m.m13 + m02*m.m23 + m03*m.m33,
        m10*m.m03 + m11*m.m13 + m12*m.m23 + m13*m.m33,
        m20*m.m03 + m21*m.m13 + m22*m.m23 + m23*m.m33
    )

    def *(u: AnyVec4d) = Vec3d(
        m00*u.x + m01*u.y + m02*u.z + m03*u.w,
        m10*u.x + m11*u.y + m12*u.z + m13*u.w,
        m20*u.x + m21*u.y + m22*u.z + m23*u.w
    )
    protected[math] def transposeMul(u: AnyVec3d) = Vec4d(
        m00*u.x + m10*u.y + m20*u.z,
        m01*u.x + m11*u.y + m21*u.z,
        m02*u.x + m12*u.y + m22*u.z,
        m03*u.x + m13*u.y + m23*u.z
    )

    /**
     * This method will apply the matrix transformation to a point
     * (such as vertex or object location).<br/>
     *
     * Equivalent to regular multiplication with Vec(u, 1).
     */
    def transformPoint(u: AnyVec3d) = Vec3d(
        m00*u.x + m01*u.y + m02*u.z + m03,
        m10*u.x + m11*u.y + m12*u.z + m13,
        m20*u.x + m21*u.y + m22*u.z + m23
    )
    /**
     * This method will apply the matrix transformation to a vector
     * (such as object speed).<br/>
     *
     * Equivalent to regular multiplication with Vec(u, 0).
     */
    def transformVector(u: AnyVec3d) = Vec3d(
        m00*u.x + m01*u.y + m02*u.z,
        m10*u.x + m11*u.y + m12*u.z,
        m20*u.x + m21*u.y + m22*u.z
    )

    /**
     * Combine two transformations. This method works similar to regular
     * multiplication but with a special handling of the translation column.
     * <br/>
     * Equaivalent to Mat3x4d(Mat4dx4(this)*Mat4dx4(m)).
     */
    def *(m: AnyMat3x4d) = Mat3x4d(
        m00*m.m00 + m01*m.m10 + m02*m.m20,
        m10*m.m00 + m11*m.m10 + m12*m.m20,
        m20*m.m00 + m21*m.m10 + m22*m.m20,

        m00*m.m01 + m01*m.m11 + m02*m.m21,
        m10*m.m01 + m11*m.m11 + m12*m.m21,
        m20*m.m01 + m21*m.m11 + m22*m.m21,

        m00*m.m02 + m01*m.m12 + m02*m.m22,
        m10*m.m02 + m11*m.m12 + m12*m.m22,
        m20*m.m02 + m21*m.m12 + m22*m.m22,

        m00*m.m03 + m01*m.m13 + m02*m.m23 + m03,
        m10*m.m03 + m11*m.m13 + m12*m.m23 + m13,
        m20*m.m03 + m21*m.m13 + m22*m.m23 + m23
    )

    /**
     * Combine this transformation with rotation. This method works similar
     * to regular multiplication but with a special handling of
     * the translation column.<br/>
     *
     * Equaivalent to Mat3x4d(Mat4dx4(this)*Mat4dx4(m)).
     */
    def *(m: AnyMat3d) = Mat3x4d(
        m00*m.m00 + m01*m.m10 + m02*m.m20,
        m10*m.m00 + m11*m.m10 + m12*m.m20,
        m20*m.m00 + m21*m.m10 + m22*m.m20,

        m00*m.m01 + m01*m.m11 + m02*m.m21,
        m10*m.m01 + m11*m.m11 + m12*m.m21,
        m20*m.m01 + m21*m.m11 + m22*m.m21,

        m00*m.m02 + m01*m.m12 + m02*m.m22,
        m10*m.m02 + m11*m.m12 + m12*m.m22,
        m20*m.m02 + m21*m.m12 + m22*m.m22,

        m03,
        m13,
        m23
    )

    def ==(m: AnyMat3x4d) :Boolean = {
        if (m eq null) false
        else
            m00 == m.m00 && m10 == m.m10 && m20 == m.m20 &&
            m01 == m.m01 && m11 == m.m11 && m21 == m.m21 &&
            m02 == m.m02 && m12 == m.m12 && m22 == m.m22 &&
            m03 == m.m03 && m13 == m.m13 && m23 == m.m23
    }

    def !=(m: AnyMat3x4d) :Boolean = !(this == m)

    private[math] def hasErrors: Boolean = {
        import java.lang.Double._

        (
            isNaN(m00) || isInfinite(m00) ||
            isNaN(m10) || isInfinite(m10) ||
            isNaN(m20) || isInfinite(m20) ||

            isNaN(m01) || isInfinite(m01) ||
            isNaN(m11) || isInfinite(m11) ||
            isNaN(m21) || isInfinite(m21) ||

            isNaN(m02) || isInfinite(m02) ||
            isNaN(m12) || isInfinite(m12) ||
            isNaN(m22) || isInfinite(m22) ||

            isNaN(m03) || isInfinite(m03) ||
            isNaN(m13) || isInfinite(m13) ||
            isNaN(m23) || isInfinite(m23)
        )
    }

    override def toString = {
        this.getClass.getSimpleName +
        "(" +
            m00 + ", " + m10 + ", " + m20 + "; " + 
            m01 + ", " + m11 + ", " + m21 + "; " + 
            m02 + ", " + m12 + ", " + m22 + "; " + 
            m03 + ", " + m13 + ", " + m23 +
        ")"
    }
}

final class ConstMat3x4d private (
    val m00: Double, val m10: Double, val m20: Double,
    val m01: Double, val m11: Double, val m21: Double,
    val m02: Double, val m12: Double, val m22: Double,
    val m03: Double, val m13: Double, val m23: Double
) extends AnyMat3x4d

object ConstMat3x4d {

    def apply(s: Double) = new ConstMat3x4d(
        s, 0, 0,
        0, s, 0,
        0, 0, s,
        0, 0, 0
    )

    def apply(
        m00: Double, m10: Double, m20: Double,
        m01: Double, m11: Double, m21: Double,
        m02: Double, m12: Double, m22: Double,
        m03: Double, m13: Double, m23: Double
      ) = new ConstMat3x4d(
            m00, m10, m20,
            m01, m11, m21,
            m02, m12, m22,
            m03, m13, m23
      )

    def apply(args: ReadAny[Double]*) :ConstMat3x4d = {
        val mat = new Array[Double](12)
        mat(0) = 1
        mat(4) = 1
        mat(8) = 1

        var index = 0
        try {
            var i = 0; while (i < args.length) {
                index = read(args(i), mat, index)
                i += 1
            }
        }
        catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 12) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new ConstMat3x4d(
            mat(0), mat(1), mat(2),
            mat(3), mat(4), mat(5),
            mat(6), mat(7), mat(8),
            mat(9), mat(10), mat(11)
        )
    }

    def apply(m: AnyMat2d) = new ConstMat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat2x3d) = new ConstMat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        m.m02, m.m12, 1,
        0, 0, 0
    )

    def apply(m: AnyMat2x4d) = new ConstMat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        m.m02, m.m12, 1,
        m.m03, m.m13, 0
    )

    def apply(m: AnyMat3x2d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat3d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        0, 0, 0
    )

    def apply(m: AnyMat3x4d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        m.m03, m.m13, m.m23
    )

    def apply(m: AnyMat4x2d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat4x3d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        0, 0, 0
    )

    def apply(m: AnyMat4d) = new ConstMat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        m.m03, m.m13, m.m23
    )

    implicit def mutableToConst(m: Mat3x4d) = ConstMat3x4d(m)
}


final class Mat3x4d private (
    var m00: Double, var m10: Double, var m20: Double,
    var m01: Double, var m11: Double, var m21: Double,
    var m02: Double, var m12: Double, var m22: Double,
    var m03: Double, var m13: Double, var m23: Double
) extends AnyMat3x4d with RotationSubMat3d
{
    def *=(s: Double) {
        m00 *= s; m10 *= s; m20 *= s;
        m01 *= s; m11 *= s; m21 *= s;
        m02 *= s; m12 *= s; m22 *= s;
        m03 *= s; m13 *= s; m23 *= s
    }
    def /=(s: Double) { val inv = 1/s;
        m00 *= inv; m10 *= inv; m20 *= inv;
        m01 *= inv; m11 *= inv; m21 *= inv;
        m02 *= inv; m12 *= inv; m22 *= inv;
        m03 *= inv; m13 *= inv; m23 *= inv
    }

    def +=(m: AnyMat3x4d) {
        m00 += m.m00; m10 += m.m10; m20 += m.m20;
        m01 += m.m01; m11 += m.m11; m21 += m.m21;
        m02 += m.m02; m12 += m.m12; m22 += m.m22;
        m03 += m.m03; m13 += m.m13; m23 += m.m23
    }
    def -=(m: AnyMat3x4d) {
        m00 -= m.m00; m10 -= m.m10; m20 -= m.m20;
        m01 -= m.m01; m11 -= m.m11; m21 -= m.m21;
        m02 -= m.m02; m12 -= m.m12; m22 -= m.m22;
        m03 -= m.m03; m13 -= m.m13; m23 -= m.m23
    }

    def *=(m: AnyMat4d) {
        val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30
        val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30
        val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30

        val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31
        val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31
        val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31

        val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32
        val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32
        val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32

        val a03 = m00*m.m03 + m01*m.m13 + m02*m.m23 + m03*m.m33
        val a13 = m10*m.m03 + m11*m.m13 + m12*m.m23 + m13*m.m33
        val a23 = m20*m.m03 + m21*m.m13 + m22*m.m23 + m23*m.m33

        m00 = a00; m10 = a10; m20 = a20
        m01 = a01; m11 = a11; m21 = a21
        m02 = a02; m12 = a12; m22 = a22
        m03 = a03; m13 = a13; m23 = a23
    }

    /**
     * Combine this transformation with rotation. This method works similar
     * to regular multiplication but with a special handling of
     * the translation column.<br/>
     *
     * Equaivalent to Mat3x4d(Mat4dx4(this)*Mat4dx4(m)).
     */
    def *=(m: AnyMat3x4d) {
        val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20
        val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20
        val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20

        val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21
        val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21
        val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21

        val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22
        val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22
        val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22

        val a03 = m00*m.m03 + m01*m.m13 + m02*m.m23 + m03
        val a13 = m10*m.m03 + m11*m.m13 + m12*m.m23 + m13
        val a23 = m20*m.m03 + m21*m.m13 + m22*m.m23 + m23

        m00 = a00; m10 = a10; m20 = a20
        m01 = a01; m11 = a11; m21 = a21
        m02 = a02; m12 = a12; m22 = a22
        m03 = a03; m13 = a13; m23 = a23
    }

    /**
     * Combine this transformation with rotation. This method works similar
     * to regular multiplication but with a special handling of
     * the translation column.<br/>
     *
     * Equaivalent to Mat3x4d(Mat4dx4(this)*Mat4dx4(m)).
     */
    def *=(m: AnyMat3d) {
        val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20
        val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20
        val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20

        val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21
        val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21
        val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21

        val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22
        val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22
        val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22

        m00 = a00; m10 = a10; m20 = a20
        m01 = a01; m11 = a11; m21 = a21
        m02 = a02; m12 = a12; m22 = a22
    }

    def :=(m: AnyMat3x4d) {
        m00 = m.m00; m10 = m.m10; m20 = m.m20;
        m01 = m.m01; m11 = m.m11; m21 = m.m21;
        m02 = m.m02; m12 = m.m12; m22 = m.m22;
        m03 = m.m03; m13 = m.m13; m23 = m.m23
    }

    def set(
        m00: Double, m10: Double, m20: Double,
        m01: Double, m11: Double, m21: Double,
        m02: Double, m12: Double, m22: Double,
        m03: Double, m13: Double, m23: Double
    ) {
        this.m00 = m00; this.m10 = m10; this.m20 = m20;
        this.m01 = m01; this.m11 = m11; this.m21 = m21;
        this.m02 = m02; this.m12 = m12; this.m22 = m22;
        this.m03 = m03; this.m13 = m13; this.m23 = m23
    }

    def update(c: Int, r: Int, s: Double) {
        def error() {
            throw new IndexOutOfBoundsException("Trying to update index (" +
                     c + ", " + r + ") in " + this.getClass.getSimpleName)
        }

        c match {
            case 0 =>
                r match {
                    case 0 => m00 = s
                    case 1 => m10 = s
                    case 2 => m20 = s
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01 = s
                    case 1 => m11 = s
                    case 2 => m21 = s
                    case _ => error
                }
            case 2 =>
                r match {
                    case 0 => m02 = s
                    case 1 => m12 = s
                    case 2 => m22 = s
                    case _ => error
                }
            case 3 =>
                r match {
                    case 0 => m03 = s
                    case 1 => m13 = s
                    case 2 => m23 = s
                    case _ => error
                }
            case _ => error
        }
    }

    def update(c: Int, v: AnyVec3d) {
        c match {
            case 0 => m00 = v.x; m10 = v.y; m20 = v.z
            case 1 => m01 = v.x; m11 = v.y; m21 = v.z
            case 2 => m02 = v.x; m12 = v.y; m22 = v.z
            case 3 => m03 = v.x; m13 = v.y; m23 = v.z
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }

}

object Mat3x4d {

    def apply(s: Double) = new Mat3x4d(
        s, 0, 0,
        0, s, 0,
        0, 0, s,
        0, 0, 0
    )

    def apply(
        m00: Double, m10: Double, m20: Double,
        m01: Double, m11: Double, m21: Double,
        m02: Double, m12: Double, m22: Double,
        m03: Double, m13: Double, m23: Double
      ) = new Mat3x4d(
            m00, m10, m20,
            m01, m11, m21,
            m02, m12, m22,
            m03, m13, m23
      )

    def apply(args: ReadAny[Double]*) :Mat3x4d = {
        val mat = new Array[Double](12)
        mat(0) = 1
        mat(4) = 1
        mat(8) = 1

        var index = 0
        try {
            var i = 0; while (i < args.length) {
                index = read(args(i), mat, index)
                i += 1
            }
        }
        catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 12) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new Mat3x4d(
            mat(0), mat(1), mat(2),
            mat(3), mat(4), mat(5),
            mat(6), mat(7), mat(8),
            mat(9), mat(10), mat(11)
        )
    }

    def apply(m: AnyMat2d) = new Mat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat2x3d) = new Mat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        m.m02, m.m12, 1,
        0, 0, 0
    )

    def apply(m: AnyMat2x4d) = new Mat3x4d(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0,
        m.m02, m.m12, 1,
        m.m03, m.m13, 0
    )

    def apply(m: AnyMat3x2d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat3d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        0, 0, 0
    )

    def apply(m: AnyMat3x4d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        m.m03, m.m13, m.m23
    )

    def apply(m: AnyMat4x2d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        0, 0, 1,
        0, 0, 0
    )

    def apply(m: AnyMat4x3d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        0, 0, 0
    )

    def apply(m: AnyMat4d) = new Mat3x4d(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21,
        m.m02, m.m12, m.m22,
        m.m03, m.m13, m.m23
    )
}
