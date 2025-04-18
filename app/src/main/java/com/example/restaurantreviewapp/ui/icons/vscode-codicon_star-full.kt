import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val StarFull: ImageVector
	get() {
		if (_StarFull != null) {
			return _StarFull!!
		}
		_StarFull = ImageVector.Builder(
            name = "StarFull",
            defaultWidth = 30.dp,
            defaultHeight = 30.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFFFFFF00)),
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
			}
		}.build()
		return _StarFull!!
	}

private var _StarFull: ImageVector? = null

//https://composeicons.com/icons/vscode-codicon/star-full