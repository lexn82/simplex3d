/*
 * Simplex3D, Math package
 * Copyright (C) 2009 Simplex3D team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * CLASSPATH EXCEPTION FOR UNMODIFIED WORK:
 * Linking this library statically or dynamically with other modules is making
 * a combined work based on this library. Thus, the terms and conditions of
 * the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce
 * an executable, regardless of the license terms of these independent modules,
 * and to copy and distribute the resulting executable under terms of your
 * choice, provided that you also meet, for each linked independent module,
 * the terms and conditions of the license of that module. An independent module
 * is a module which is not derived from or based on this library. If you modify
 * this library in any way, then this exception is null and void and no longer
 * applies, in this case delete this exception statement from your version.
 */

package simplex3d.math

import VecMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyMat4x3 {
    // Column major order.
    def m00: Float; def m10: Float; def m20: Float; def m30: Float // column
    def m01: Float; def m11: Float; def m21: Float; def m31: Float // column
    def m02: Float; def m12: Float; def m22: Float; def m32: Float // column

    def apply(c: Int) :ConstVec4 = {
        c match {
            case 0 => ConstVec4(m00, m10, m20, m30)
            case 1 => ConstVec4(m01, m11, m21, m31)
            case 2 => ConstVec4(m02, m12, m22, m32)
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 2, got " + j)
        }
    }

    def apply(c: Int, r: Int) :Float = {
        def error() :Float = {
            throw new IndexOutOfBoundsException("Trying to read index (" +
                     c + ", " + r + ") in " + this.getClass.getSimpleName)
        }

        c match {
            case 0 =>
                r match {
                    case 0 => m00
                    case 1 => m10
                    case 2 => m20
                    case 3 => m30
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01
                    case 1 => m11
                    case 2 => m21
                    case 3 => m31
                    case _ => error
                }
            case 2 =>
                r match {
                    case 0 => m02
                    case 1 => m12
                    case 2 => m22
                    case 3 => m32
                    case _ => error
                }
            case _ => error
        }
    }

    def unary_-() = Mat4x3(
        -m00, -m10, -m20, -m30,
        -m01, -m11, -m21, -m31,
        -m02, -m12, -m22, -m32
    )
    def *(s: Float) = Mat4x3(
        s*m00, s*m10, s*m20, s*m30,
        s*m01, s*m11, s*m21, s*m31,
        s*m02, s*m12, s*m22, s*m32
    )
    def /(s: Float) = { val inv = 1/s; Mat4x3(
        inv*m00, inv*m10, inv*m20, inv*m30,
        inv*m01, inv*m11, inv*m21, inv*m31,
        inv*m02, inv*m12, inv*m22, inv*m32
    )}

    def +(m: AnyMat4x3) = Mat4x3(
        m00 + m.m00, m10 + m.m10, m20 + m.m20, m30 + m.m30,
        m01 + m.m01, m11 + m.m11, m21 + m.m21, m31 + m.m31,
        m02 + m.m02, m12 + m.m12, m22 + m.m22, m32 + m.m32
    )
    def -(m: AnyMat4x3) = Mat4x3(
        m00 - m.m00, m10 - m.m10, m20 - m.m20, m30 - m.m30,
        m01 - m.m01, m11 - m.m11, m21 - m.m21, m31 - m.m31,
        m02 - m.m02, m12 - m.m12, m22 - m.m22, m32 - m.m32
    )

    def *(m: AnyMat3x2) = Mat4x2(
        m00*m.m00 + m01*m.m10 + m02*m.m20,
        m10*m.m00 + m11*m.m10 + m12*m.m20,
        m20*m.m00 + m21*m.m10 + m22*m.m20,
        m30*m.m00 + m31*m.m10 + m32*m.m20,

        m00*m.m01 + m01*m.m11 + m02*m.m21,
        m10*m.m01 + m11*m.m11 + m12*m.m21,
        m20*m.m01 + m21*m.m11 + m22*m.m21,
        m30*m.m01 + m31*m.m11 + m32*m.m21
    )
    def *(m: AnyMat3) = Mat4x3(
        m00*m.m00 + m01*m.m10 + m02*m.m20,
        m10*m.m00 + m11*m.m10 + m12*m.m20,
        m20*m.m00 + m21*m.m10 + m22*m.m20,
        m30*m.m00 + m31*m.m10 + m32*m.m20,

        m00*m.m01 + m01*m.m11 + m02*m.m21,
        m10*m.m01 + m11*m.m11 + m12*m.m21,
        m20*m.m01 + m21*m.m11 + m22*m.m21,
        m30*m.m01 + m31*m.m11 + m32*m.m21,

        m00*m.m02 + m01*m.m12 + m02*m.m22,
        m10*m.m02 + m11*m.m12 + m12*m.m22,
        m20*m.m02 + m21*m.m12 + m22*m.m22,
        m30*m.m02 + m31*m.m12 + m32*m.m22
    )
    def *(m: AnyMat3x4) = Mat4(
        m00*m.m00 + m01*m.m10 + m02*m.m20,
        m10*m.m00 + m11*m.m10 + m12*m.m20,
        m20*m.m00 + m21*m.m10 + m22*m.m20,
        m30*m.m00 + m31*m.m10 + m32*m.m20,

        m00*m.m01 + m01*m.m11 + m02*m.m21,
        m10*m.m01 + m11*m.m11 + m12*m.m21,
        m20*m.m01 + m21*m.m11 + m22*m.m21,
        m30*m.m01 + m31*m.m11 + m32*m.m21,

        m00*m.m02 + m01*m.m12 + m02*m.m22,
        m10*m.m02 + m11*m.m12 + m12*m.m22,
        m20*m.m02 + m21*m.m12 + m22*m.m22,
        m30*m.m02 + m31*m.m12 + m32*m.m22,

        m00*m.m03 + m01*m.m13 + m02*m.m23,
        m10*m.m03 + m11*m.m13 + m12*m.m23,
        m20*m.m03 + m21*m.m13 + m22*m.m23,
        m30*m.m03 + m31*m.m13 + m32*m.m23
    )

    def *(u: AnyVec3) = Vec4(
        m00*u.x + m01*u.y + m02*u.z,
        m10*u.x + m11*u.y + m12*u.z,
        m20*u.x + m21*u.y + m22*u.z,
        m30*u.x + m31*u.y + m32*u.z
    )
    protected[math] def transposeMul(u: AnyVec4) = Vec3(
        m00*u.x + m10*u.y + m20*u.z + m30*u.w,
        m01*u.x + m11*u.y + m21*u.z + m31*u.w,
        m02*u.x + m12*u.y + m22*u.z + m32*u.w
    )

    /**
     * Equivalent to regular multiplication with Vec(u, 1)
     * For example u: Vec2 is treated as Vec3(u, 1).
     * Useful when using matrices that store translation as the last column.
     */
    def transform(u: AnyVec2) = Vec4(
        m00*u.x + m01*u.y + m02,
        m10*u.x + m11*u.y + m12,
        m20*u.x + m21*u.y + m22,
        m30*u.x + m31*u.y + m32
    )

    def ==(m: AnyMat4x3) :Boolean = {
        if (m eq null) false
        else
            m00 == m.m00 && m10 == m.m10 && m20 == m.m20 && m30 == m.m30 &&
            m01 == m.m01 && m11 == m.m11 && m21 == m.m21 && m31 == m.m31 &&
            m02 == m.m02 && m12 == m.m12 && m22 == m.m22 && m32 == m.m32
    }

    def !=(m: AnyMat4x3) :Boolean = !(this == m)

    /**
     * Approximate comparision.
     * Read: "this isApproximately m".
     */
    def ~=(m: AnyMat4x3) :Boolean = {
        if (m == null)
            false
        else (
            abs(m00 - m.m00) < ApproximationDelta &&
            abs(m10 - m.m10) < ApproximationDelta &&
            abs(m20 - m.m20) < ApproximationDelta &&
            abs(m30 - m.m30) < ApproximationDelta &&

            abs(m01 - m.m01) < ApproximationDelta &&
            abs(m11 - m.m11) < ApproximationDelta &&
            abs(m21 - m.m21) < ApproximationDelta &&
            abs(m31 - m.m31) < ApproximationDelta &&

            abs(m02 - m.m02) < ApproximationDelta &&
            abs(m12 - m.m12) < ApproximationDelta &&
            abs(m22 - m.m22) < ApproximationDelta &&
            abs(m32 - m.m32) < ApproximationDelta
        )
    }

    /**
     * Inverse of approximate comparision.
     * Read: "this isNotApproximately m" or "this isDistinctFrom m".
     */
    def !~(m: AnyMat4x3) :Boolean = !(this ~= m)

    def isValid: Boolean = {
        import java.lang.Float._

        !(
            isNaN(m00) || isInfinite(m00) ||
            isNaN(m10) || isInfinite(m10) ||
            isNaN(m20) || isInfinite(m20) ||
            isNaN(m30) || isInfinite(m30) ||

            isNaN(m01) || isInfinite(m01) ||
            isNaN(m11) || isInfinite(m11) ||
            isNaN(m21) || isInfinite(m21) ||
            isNaN(m31) || isInfinite(m31) ||

            isNaN(m02) || isInfinite(m02) ||
            isNaN(m12) || isInfinite(m12) ||
            isNaN(m22) || isInfinite(m22) ||
            isNaN(m32) || isInfinite(m32)
        )
    }

    override def toString = {
        this.getClass.getSimpleName +
        "(" +
            m00 + ", " + m10 + ", " + m20 + ", " + m30 + "; " + 
            m01 + ", " + m11 + ", " + m21 + ", " + m31 + "; " + 
            m02 + ", " + m12 + ", " + m22 + ", " + m32 +
        ")"
    }
}

final class ConstMat4x3 private (
    val m00: Float, val m10: Float, val m20: Float, val m30: Float,
    val m01: Float, val m11: Float, val m21: Float, val m31: Float,
    val m02: Float, val m12: Float, val m22: Float, val m32: Float
) extends AnyMat4x3

object ConstMat4x3 {

    def apply(s: Float) = new ConstMat4x3(
        s, 0, 0, 0,
        0, s, 0, 0,
        0, 0, s, 0
    )

    def apply(
        m00: Float, m10: Float, m20: Float, m30: Float,
        m01: Float, m11: Float, m21: Float, m31: Float,
        m02: Float, m12: Float, m22: Float, m32: Float
      ) = new ConstMat4x3(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32
      )

    private def read(arg: ReadAny[Float], mat: Array[Float], index: Int) :Int = {
        var i = index
        arg match {
            case s: ExtendedFloat => {
                mat(i) = s.value
                i += 1
            }
            case v2: AnyVec2 => {
                mat(i) = v2.x
                i += 1
                mat(i) = v2.y
                i += 1
            }
            case v3: AnyVec3 => {
                mat(i) = v3.x
                i += 1
                mat(i) = v3.y
                i += 1
                mat(i) = v3.z
                i += 1
            }
            case v4: AnyVec4 => {
                mat(i) = v4.x
                i += 1
                mat(i) = v4.y
                i += 1
                mat(i) = v4.z
                i += 1
                mat(i) = v4.w
                i += 1
            }
            case _ => throw new IllegalArgumentException(
                "Expected a scalar or a vector of type Float, " +
                "got " + arg.getClass.getName)
        }
        i
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float], 
              a9: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
            index = read(a9, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float], 
              a9: ReadAny[Float], a10: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
            index = read(a9, mat, index)
            index = read(a10, mat, index)
        } catch {
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

        new ConstMat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(m: AnyMat2) = new ConstMat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        0, 0, 1, 0
    )

    def apply(m: AnyMat2x3) = new ConstMat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        m.m02, m.m12, 1, 0
    )

    def apply(m: AnyMat2x4) = new ConstMat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        m.m02, m.m12, 1, 0
    )

    def apply(m: AnyMat3x2) = new ConstMat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        0, 0, 1, 0
    )

    def apply(m: AnyMat3) = new ConstMat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        m.m02, m.m12, m.m22, 0
    )

    def apply(m: AnyMat3x4) = new ConstMat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        m.m02, m.m12, m.m22, 0
    )

    def apply(m: AnyMat4x2) = new ConstMat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        0, 0, 1, 0
    )

    def apply(m: AnyMat4x3) = new ConstMat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        m.m02, m.m12, m.m22, m.m32
    )

    def apply(m: AnyMat4) = new ConstMat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        m.m02, m.m12, m.m22, m.m32
    )

    implicit def mutableToConst(m: Mat4x3) = ConstMat4x3(m)
}


final class Mat4x3 private (
    var m00: Float, var m10: Float, var m20: Float, var m30: Float,
    var m01: Float, var m11: Float, var m21: Float, var m31: Float,
    var m02: Float, var m12: Float, var m22: Float, var m32: Float
) extends AnyMat4x3
{
    def *=(s: Float) {
        m00 *= s; m10 *= s; m20 *= s; m30 *= s;
        m01 *= s; m11 *= s; m21 *= s; m31 *= s;
        m02 *= s; m12 *= s; m22 *= s; m32 *= s
    }
    def /=(s: Float) { val inv = 1/s;
        m00 *= inv; m10 *= inv; m20 *= inv; m30 *= inv;
        m01 *= inv; m11 *= inv; m21 *= inv; m31 *= inv;
        m02 *= inv; m12 *= inv; m22 *= inv; m32 *= inv
    }

    def +=(m: AnyMat4x3) {
        m00 += m.m00; m10 += m.m10; m20 += m.m20; m30 += m.m30;
        m01 += m.m01; m11 += m.m11; m21 += m.m21; m31 += m.m31;
        m02 += m.m02; m12 += m.m12; m22 += m.m22; m32 += m.m32
    }
    def -=(m: AnyMat4x3) {
        m00 -= m.m00; m10 -= m.m10; m20 -= m.m20; m30 -= m.m30;
        m01 -= m.m01; m11 -= m.m11; m21 -= m.m21; m31 -= m.m31;
        m02 -= m.m02; m12 -= m.m12; m22 -= m.m22; m32 -= m.m32
    }

    def *=(m: AnyMat3) {
        val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20
        val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20
        val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20
        val a30 = m30*m.m00 + m31*m.m10 + m32*m.m20

        val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21
        val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21
        val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21
        val a31 = m30*m.m01 + m31*m.m11 + m32*m.m21

        val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22
        val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22
        val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22
        val a32 = m30*m.m02 + m31*m.m12 + m32*m.m22

        m00 = a00; m10 = a10; m20 = a20; m30 = a30
        m01 = a01; m11 = a11; m21 = a21; m31 = a31
        m02 = a02; m12 = a12; m22 = a22; m32 = a32
    }

    def :=(m: AnyMat4x3) {
        m00 = m.m00; m10 = m.m10; m20 = m.m20; m30 = m.m30;
        m01 = m.m01; m11 = m.m11; m21 = m.m21; m31 = m.m31;
        m02 = m.m02; m12 = m.m12; m22 = m.m22; m32 = m.m32
    }

    def update(c: Int, r: Int, s: Float) {
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
                    case 3 => m30 = s
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01 = s
                    case 1 => m11 = s
                    case 2 => m21 = s
                    case 3 => m31 = s
                    case _ => error
                }
            case 2 =>
                r match {
                    case 0 => m02 = s
                    case 1 => m12 = s
                    case 2 => m22 = s
                    case 3 => m32 = s
                    case _ => error
                }
            case _ => error
        }
    }

    def update(c: Int, v: AnyVec4) {
        c match {
            case 0 => m00 = v.x; m10 = v.y; m20 = v.z; m30 = v.w
            case 1 => m01 = v.x; m11 = v.y; m21 = v.z; m31 = v.w
            case 2 => m02 = v.x; m12 = v.y; m22 = v.z; m32 = v.w
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 2, got " + j)
        }
    }

}

object Mat4x3 {

    def apply(s: Float) = new Mat4x3(
        s, 0, 0, 0,
        0, s, 0, 0,
        0, 0, s, 0
    )

    def apply(
        m00: Float, m10: Float, m20: Float, m30: Float,
        m01: Float, m11: Float, m21: Float, m31: Float,
        m02: Float, m12: Float, m22: Float, m32: Float
      ) = new Mat4x3(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32
      )

    private def read(arg: ReadAny[Float], mat: Array[Float], index: Int) :Int = {
        var i = index
        arg match {
            case s: ExtendedFloat => {
                mat(i) = s.value
                i += 1
            }
            case v2: AnyVec2 => {
                mat(i) = v2.x
                i += 1
                mat(i) = v2.y
                i += 1
            }
            case v3: AnyVec3 => {
                mat(i) = v3.x
                i += 1
                mat(i) = v3.y
                i += 1
                mat(i) = v3.z
                i += 1
            }
            case v4: AnyVec4 => {
                mat(i) = v4.x
                i += 1
                mat(i) = v4.y
                i += 1
                mat(i) = v4.z
                i += 1
                mat(i) = v4.w
                i += 1
            }
            case _ => throw new IllegalArgumentException(
                "Expected a scalar or a vector of type Float, " +
                "got " + arg.getClass.getName)
        }
        i
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float], 
              a9: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
            index = read(a9, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float], 
              a3: ReadAny[Float], a4: ReadAny[Float], a5: ReadAny[Float], 
              a6: ReadAny[Float], a7: ReadAny[Float], a8: ReadAny[Float], 
              a9: ReadAny[Float], a10: ReadAny[Float]) =
    {
        val mat = new Array[Float](12)
        mat(0) = 1
        mat(5) = 1
        mat(10) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
            index = read(a3, mat, index)
            index = read(a4, mat, index)
            index = read(a5, mat, index)
            index = read(a6, mat, index)
            index = read(a7, mat, index)
            index = read(a8, mat, index)
            index = read(a9, mat, index)
            index = read(a10, mat, index)
        } catch {
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

        new Mat4x3(
            mat(0), mat(1), mat(2), mat(3),
            mat(4), mat(5), mat(6), mat(7),
            mat(8), mat(9), mat(10), mat(11)
        )
    }

    def apply(m: AnyMat2) = new Mat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        0, 0, 1, 0
    )

    def apply(m: AnyMat2x3) = new Mat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        m.m02, m.m12, 1, 0
    )

    def apply(m: AnyMat2x4) = new Mat4x3(
        m.m00, m.m10, 0, 0,
        m.m01, m.m11, 0, 0,
        m.m02, m.m12, 1, 0
    )

    def apply(m: AnyMat3x2) = new Mat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        0, 0, 1, 0
    )

    def apply(m: AnyMat3) = new Mat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        m.m02, m.m12, m.m22, 0
    )

    def apply(m: AnyMat3x4) = new Mat4x3(
        m.m00, m.m10, m.m20, 0,
        m.m01, m.m11, m.m21, 0,
        m.m02, m.m12, m.m22, 0
    )

    def apply(m: AnyMat4x2) = new Mat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        0, 0, 1, 0
    )

    def apply(m: AnyMat4x3) = new Mat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        m.m02, m.m12, m.m22, m.m32
    )

    def apply(m: AnyMat4) = new Mat4x3(
        m.m00, m.m10, m.m20, m.m30,
        m.m01, m.m11, m.m21, m.m31,
        m.m02, m.m12, m.m22, m.m32
    )
}
