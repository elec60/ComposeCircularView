package com.example.composecircularview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.*

@Composable
fun CircularView(
    content: @Composable () -> Unit
) {
    var middle by remember {
        mutableStateOf(Offset.Zero)
    }

    var size by remember {
        mutableStateOf(0.dp)
    }

    var dragAngle by remember {
        mutableStateOf(0f)
    }

    Canvas(modifier = Modifier.size(size)) {
        drawCircle(
            color = Color.Red,
            center = middle,
            style = Stroke(1.dp.toPx())
        )
    }
    Layout(
        content = content,
        modifier = Modifier.pointerInput(true) {
            detectDragGestures(
                onDrag = { change, _ ->
                    change.consumeAllChanges()
                    val positionOfDrag = change.position
                    val previousPosition = change.previousPosition

                    dragAngle += atan2(
                        positionOfDrag.x - middle.x,
                        positionOfDrag.y - middle.y
                    ) - atan2(
                        previousPosition.x - middle.x,
                        previousPosition.y - middle.y
                    )
                }
            )
        }
    ) { measurables, constraints ->

        val placeables = measurables.map { it.measure(constraints) }
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        layout(layoutWidth, layoutHeight) {
            val childCount = placeables.size
            if (childCount == 0) return@layout

            val middleX = layoutWidth / 2f
            val middleY = layoutHeight / 2f


            middle = Offset(middleX, middleY)

            val angleBetween = 2 * PI / childCount
            val radius =
                min(
                    layoutWidth - (placeables.maxByOrNull { it.width }?.width ?: 0),
                    layoutHeight - (placeables.maxByOrNull { it.height }?.height ?: 0)
                ) / 2

            size = (radius * 2).toDp()

            placeables.forEachIndexed { index, placeable ->
                val angle = index * angleBetween - PI / 2 - dragAngle
                val x = middleX + (radius) * cos(angle) - placeable.width / 2f
                val y = middleY + (radius) * sin(angle) - placeable.height / 2f
                placeable.placeRelative(x = x.toInt(), y = y.toInt())
            }
        }
    }
}