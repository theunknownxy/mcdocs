package de.theunknownxy.jgui.container

import de.theunknownxy.jgui.layout.ExpandingPolicy
import de.theunknownxy.jgui.layout.FixedPolicy

public class VerticalBox : MultiContainer() {
    override fun recalculateChildren() {
        // Step 1: Remove space used by Fixed and collect the total importance of Expanding elements
        var available_space = this.area.height
        var expanding_sum = 0f
        for ((widget, constraint) in children) {
            val policy = constraint.vertical

            when (policy) {
                is ExpandingPolicy -> expanding_sum += policy.importance
                is FixedPolicy -> available_space -= policy.value
            }
        }

        // Step 2: Partition remaining space to the expanding widgets
        for ((widget, constraint) in children) {
            val policy = constraint.vertical
            if (policy is ExpandingPolicy) {
                widget.area.height = policy.importance / expanding_sum * available_space
            }
        }

        // Step 3: Adjust widget positions
        var lasty = area.y
        for (widget in children.keySet()) {
            // Set common properties
            widget.area.x = this.area.x
            val horizontal = children[widget]?.horizontal
            if(horizontal is FixedPolicy) {
                widget.area.width = horizontal.value
            } else if(horizontal is ExpandingPolicy) {
                widget.area.width = this.area.width
            }

            // Set calculated properties
            widget.area.y = lasty
            lasty += widget.area.height

            // Call area changed method
            widget.areaChanged()
        }
    }
}