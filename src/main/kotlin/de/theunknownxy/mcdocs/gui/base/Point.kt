package de.theunknownxy.mcdocs.gui.base

public class Point(var x: Float, var y: Float) {
    override fun equals(other: Any?): Boolean {
        if (other != null) {
            if (other is Point) {
                return other.x == x && other.y == y
            }
        }
        return false
    }

    override fun toString(): String {
        return "($x|$y)"
    }
}