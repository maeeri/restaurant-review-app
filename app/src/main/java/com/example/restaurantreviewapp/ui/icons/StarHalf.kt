import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val StarHalf: ImageVector
	get() {
		if (_StarHalf != null) {
			return _StarHalf!!
		}
		_StarHalf = ImageVector.Builder(
            name = "StarHalf",
            defaultWidth = 30.dp,
            defaultHeight = 30.dp,
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
				moveTo(6.405f, 6.252f)
				lineTo(8f, 1f)
				lineToRelative(1.595f, 5.252f)
				horizontalLineTo(15f)
				lineToRelative(-4.373f, 3.4f)
				lineTo(12.25f, 15f)
				lineTo(8f, 11.695f)
				lineTo(3.75f, 15f)
				lineToRelative(1.623f, -5.348f)
				lineTo(1f, 6.252f)
				horizontalLineToRelative(5.405f)
				close()
				moveTo(8f, 10.032f)
				lineToRelative(1.915f, 1.49f)
				lineToRelative(-0.731f, -2.41f)
				lineToRelative(1.915f, -1.49f)
				horizontalLineTo(8.732f)
				lineTo(8f, 5.214f)
				verticalLineToRelative(4.82f)
				close()
				moveToRelative(0f, -7.525f)
				close()
				moveToRelative(5.652f, 4.215f)
				horizontalLineTo(9.28f)
				horizontalLineToRelative(4.372f)
				close()
			}
		}.build()
		return _StarHalf!!
	}

private var _StarHalf: ImageVector? = null

//https://composeicons.com/icons/vscode-codicon/star-half