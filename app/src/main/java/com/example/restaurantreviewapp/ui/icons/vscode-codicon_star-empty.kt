import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val StarEmpty: ImageVector
	get() {
		if (_StarEmpty != null) {
			return _StarEmpty!!
		}
		_StarEmpty = ImageVector.Builder(
            name = "StarEmpty",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.EvenOdd
			) {
				moveTo(9.595f, 6.252f)
				lineTo(8f, 1f)
				lineTo(6.405f, 6.252f)
				horizontalLineTo(1f)
				lineToRelative(4.373f, 3.4f)
				lineTo(3.75f, 15f)
				lineTo(8f, 11.695f)
				lineTo(12.25f, 15f)
				lineToRelative(-1.623f, -5.348f)
				lineTo(15f, 6.252f)
				horizontalLineTo(9.595f)
				close()
				moveToRelative(-7.247f, 0.47f)
				horizontalLineTo(6.72f)
				lineTo(8f, 2.507f)
				lineTo(6.72f, 6.722f)
				horizontalLineTo(2.348f)
				close()
				moveToRelative(3.537f, 2.75f)
				lineToRelative(-1.307f, 4.305f)
				lineToRelative(1.307f, -4.305f)
				close()
				moveToRelative(7.767f, -2.75f)
				horizontalLineTo(9.28f)
				horizontalLineToRelative(4.372f)
				close()
				moveToRelative(-8.75f, 0.9f)
				horizontalLineToRelative(2.366f)
				lineTo(8f, 5.214f)
				lineToRelative(0.732f, 2.41f)
				horizontalLineToRelative(2.367f)
				lineToRelative(-1.915f, 1.49f)
				lineToRelative(0.731f, 2.409f)
				lineTo(8f, 10.032f)
				lineToRelative(-1.915f, 1.49f)
				lineToRelative(0.731f, -2.41f)
				lineToRelative(-1.915f, -1.49f)
				close()
			}
		}.build()
		return _StarEmpty!!
	}

private var _StarEmpty: ImageVector? = null


// https://composeicons.com/icons/vscode-codicon/star-empty