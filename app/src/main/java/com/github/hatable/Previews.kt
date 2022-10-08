package com.github.hatable // ktlint-disable filename

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun createPreview() {
    val items: List<GridProps> = listOf(
        GridProps(
            "Mon, Tue & Thu <br>09.00 - 10.00"
        ),
        GridProps(
            "Mon, Wed & Thu <br>11.00 - 12.00"
        ),
        GridProps(
            "Fri, Tue & Thu <br>11.00 - 12.00"
        ),
        GridProps(
            "Tue & Thu <br>02.00 - 04.00"
        ),
        GridProps(
            "Wed & Thu <br>04.00 - 5.00"
        )
    )
    GridView(items) {
    }
}
