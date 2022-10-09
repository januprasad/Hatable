package com.github.hatable

import androidx.compose.runtime.snapshots.SnapshotStateList

val limit = 6
val data: List<GridProps> = mutableListOf(
    GridProps(
        "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
    ),
    GridProps(
        "Tue, Wed & Thu <br><b>11.00 - 12.00</b>"
    ),
    GridProps(
        "Mon, Tue & Thu <br><b>11.00 - 12.00</b>"
    ),
    GridProps(
        "Wed & Thu <br><b>02.00 - 04.00</b>"
    ),
    GridProps(
        "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
    ),
    GridProps(
        "Tue, Wed & Thu <br><b>11.00 - 12.00</b>"
    ),
    GridProps(
        "Fri, Tue & Thu <br><b>11.00 - 12.00</b>"
    ),
    GridProps(
        "Tue & Thu <br><b>02.00 - 04.00</b>"
    ),
    GridProps(
        "Fri, Tue & Thu <br><b>09.00 - 10.00</b>"
    ),
    GridProps(
        "Tue, Wed & Thu <br><b>11.00 - 12.00</b>"
    )
)

fun SnapshotStateList<GridProps>.changeVisibilityUIListData(
    operation: (GridProps) -> Unit
) {
    this.takeLast(data.size - limit).map {
//        it.isVisible = value
        operation(it)
    }
}
