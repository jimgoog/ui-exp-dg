import androidx.compose.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.remember
import androidx.compose.state
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.ImageAssetConfig
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.platform.font
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.desktop.AppWindow
import androidx.ui.res.imageResource
import org.jetbrains.skija.ColorSpace
import org.jetbrains.skija.Image

val title = "Hello Desktop"

val italicFont = fontFamily(font("Noto Italic", "NotoSans-Italic.ttf"))

fun main() {
    AppWindow(title, IntSize(1024, 768)).show {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("BUTTON") },
                    onClick = {
                        println("Floating button clicked")
                    }
                )
            },
            bodyContent = {
                val amount = state { 0 }
                val animation = state { true }
                val text = state { "Hello \uD83E\uDDD1\uD83C\uDFFF\u200D\uD83E\uDDB0\nПривет" }
                Column(Modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
                    Text(
                        text = "Привет! 你好! Desktop Compose ${amount.value}",
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color.Blue)
                            .preferredHeight(56.dp)
                            .wrapContentSize(Alignment.Center)
                    )

                    Text(
                        text = with(AnnotatedString.Builder("The quick ")) {
                            pushStyle(SpanStyle(color = Color(0xff964B00)))
                            append("brown fox")
                            pop()
                            append(" 🦊 ate a ")
                            pushStyle(SpanStyle(fontSize = 30.sp))
                            append("zesty hamburgerfons")
                            pop()
                            append(" 🍔.\nThe 👩‍👩‍👧‍👧 laughed.")
                            addStyle(SpanStyle(color = Color.Green), 25, 35)
                            toAnnotatedString()
                        },
                        color = Color.Black
                    )

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                                " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                                " minim veniam, quis nostrud exercitation ullamco laboris nisi ut" +
                                " aliquipex ea commodo consequat. Duis aute irure dolor in reprehenderit" +
                                " in voluptate velit esse cillum dolore eu fugiat nulla pariatur." +
                                " Excepteur" +
                                " sint occaecat cupidatat non proident, sunt in culpa qui officia" +
                                " deserunt mollit anim id est laborum."
                    )

                    Text(
                        text = "fun <T : Comparable<T>> List<T>.quickSort(): List<T> = when {\n" +
                                "  size < 2 -> this\n" +
                                "  else -> {\n" +
                                "    val pivot = first()\n" +
                                "    val (smaller, greater) = drop(1).partition { it <= pivot }\n" +
                                "    smaller.quickSort() + pivot + greater.quickSort()\n" +
                                "   }\n" +
                                "}",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = italicFont
                    )

                    Button(onClick = {
                        amount.value++
                    }) {
                        Text("Base")
                    }

                    Row(modifier = Modifier.padding(vertical = 10.dp),
                        verticalGravity = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                animation.value = !animation.value
                            }) {
                            Text("Toggle")
                        }

                        if (animation.value) {
                            CircularProgressIndicator()
                        }
                    }

                    Slider(value = amount.value.toFloat() / 100f,
                        onValueChange = { amount.value = (it * 100).toInt() })
                    TextField(
                        value = amount.value.toString(),
                        onValueChange = { amount.value = it.toIntOrNull() ?: 42 },
                        label = { Text(text = "Input1") }
                    )
                    TextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        label = { Text(text = "Input2") }
                    )

                    Image(imageResource("circus.jpg"))
                }
            }
        )
    }
}
