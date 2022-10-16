package com.github.hatable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.hatable.ui.theme.HatableTheme
import com.github.hatable.ui.theme.activeBorderColor
import com.github.hatable.ui.theme.activeCardColor
import com.github.hatable.ui.theme.borderColor
import com.github.hatable.ui.theme.buttonColor
import com.github.hatable.ui.theme.cardColor
import com.github.hatable.ui.theme.greenColor

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HatableTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = greenColor,
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) {
                        MainView()
                    }
                }
            }
        }
    }
}

@Composable
private fun ButtonView() {
    Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
        var selectionId: TeacherType by remember {
            mutableStateOf(TeacherType.Nothing)
        }

        TeacherTypeButton(
            selectionId,
            TeacherType.Local(),
            Modifier.weight(1f)
        ) {
            selectionId = it
        }
        TeacherTypeButton(
            selectionId,
            TeacherType.Foreign(),
            Modifier.weight(1f)
        ) {
            selectionId = it
        }
    }
}

sealed interface ITeacherType {
    fun selectionReverse(): TeacherType
}

sealed class TeacherType(val label: String, val isSelected: Boolean = false) : ITeacherType {

    data class Local(val selection: Boolean = false) :
        TeacherType("Local", isSelected = selection) {
        override fun selectionReverse(): TeacherType {
            return this.copy(selection = !selection)
        }
    }

    data class Foreign(val selection: Boolean = false) :
        TeacherType("Foreign", isSelected = selection) {
        override fun selectionReverse(): TeacherType {
            return this.copy(selection = !selection)
        }
    }

    object Nothing : TeacherType("Nothing") {
        override fun selectionReverse(): TeacherType {
            TODO("Not yet implemented")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeacherTypeButton(
    selectedTeacherType: TeacherType,
    teacherType: TeacherType,
    weight: Modifier,
    updateSelectionId: (TeacherType) -> Unit
) {
    val currentBorderColor =
        if (checkActive(selectedTeacherType, teacherType)) activeBorderColor else borderColor
    val currentCardColor =
        if (checkActive(selectedTeacherType, teacherType)) activeCardColor else cardColor

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = currentCardColor,
        border = BorderStroke(width = 1.dp, color = currentBorderColor),
        modifier = weight.aspectRatio(ratio = 16f / 5)
            .padding(4.dp),
        onClick = {
            when (teacherType) {
                is TeacherType.Foreign -> {
                    buttonStateChanged(teacherType, updateSelectionId)
                }

                is TeacherType.Local -> {
                    buttonStateChanged(teacherType, updateSelectionId)
                }

                else -> {}
            }
        },
        elevation = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = teacherType.label,
                lineHeight = 16.sp,
                fontSize = 12.sp
            )
        }
    }
}

private fun buttonStateChanged(
    teacherType: TeacherType,
    updateSelectionId: (TeacherType) -> Unit
) {
    if (!teacherType.isSelected) {
        updateSelectionId(teacherType.selectionReverse())
    } else {
        updateSelectionId(teacherType.selectionReverse())
    }
}

private fun checkActive(
    selectedTeacherType: TeacherType,
    currentTeacherType: TeacherType
): Boolean {
    return if (selectedTeacherType.label == currentTeacherType.label) {
        selectedTeacherType.isSelected != currentTeacherType.isSelected
    } else false
}

@Composable
private fun MainView() {
    val gridViewModel = viewModel(modelClass = GridViewModel::class.java)

    val uiState by remember {
        gridViewModel.uiState
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Box(
            modifier = Modifier
        ) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            Column(
                modifier = Modifier
                    .padding(start = 34.dp)
                    .drawWithCache {
                        onDrawWithContent {
                            // draw behind the content the vertical line on the left
                            drawLine(
                                color = buttonColor,
                                Offset.Zero,
                                Offset(0f, this.size.height),
                                1f,
                                pathEffect = pathEffect
                            )
                            drawContent()
                        }
                    }
            ) {
                Row(modifier = Modifier.padding(start = 34.dp)) {
                    Column() {
                        Text(
                            text = "Availability",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 17.sp
                        )
                        Text(
                            text = "When are you available to study? " +
                                "You're free to pick more than one (1) option.",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        TopSection(Modifier.weight(1f), uiState.items) { item ->
                            gridViewModel.onEvent(UIEvent.OnGridItemSelected(item))
                        }
                        MiddleSection(moreIconState = uiState.items.size > 6, modifier = Modifier) {
                            gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, buttonColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = activeCardColor)
                ) {
                    Text(text = "1", color = buttonColor)
                }
            }
        }
        ButtonView()
        Column() {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, buttonColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = activeCardColor)
                ) {
                    Text(text = "2", color = buttonColor)
                }
            }
            EndSection() {
                gridViewModel.onEvent(UIEvent.OnSubmitPressed)
            }
        }
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    items: SnapshotStateList<GridProps>,
    OnGridIconPressed: (GridProps) -> Unit
) {
    GridView(items) {
        OnGridIconPressed(it)
    }
}

@Composable
fun MiddleSection(
    moreIconState: Boolean,
    modifier: Modifier = Modifier,
    OnMoreIconPressed: () -> Unit
) {
    var enforceMore by remember {
        mutableStateOf(true)
    }

    val c = LocalContext.current
    if (moreIconState) {
        val showMoretext = enforceMore then "Show More" ?: "Show Less"
        Text(
            text = showMoretext,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    enforceMore = !enforceMore
                    OnMoreIconPressed()
                }
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun EndSection(OnSubmitPressed: () -> Unit) {
    val gridViewModel = viewModel(modelClass = GridViewModel::class.java)
    Button(
        onClick = {
            OnSubmitPressed()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(text = "Collect Selected")
    }
}

@Composable
fun GridView(
    items: List<GridProps>,
    modifier: Modifier = Modifier,
    onItemSelected: (GridProps) -> Unit
) {
    LazyVerticalGrid(
        // on below line we are setting the
        columns = GridCells.Fixed(2)
    ) {
        items(items, key = { item -> item.id }) {
            val item = it
            if (item.isVisible) {
                GridItem(
                    item,
                    onItemSelected = { selectedItem ->
                        onItemSelected(selectedItem)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GridItem(item: GridProps, onItemSelected: (GridProps) -> Unit) {
    val gridItemState by remember {
        mutableStateOf(item)
    }
    val currentBorderColor = item.isSelectedGridItem then activeBorderColor ?: borderColor
    val currentCardColor = item.isSelectedGridItem then activeCardColor ?: cardColor
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = currentCardColor,
        border = BorderStroke(width = 1.dp, color = currentBorderColor),
        modifier = Modifier
            .aspectRatio(ratio = 16f / 5)
            .padding(4.dp),
        onClick = {
            onItemSelected(item)
        },
        elevation = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HtmlText(
                text = item.item,
                lineHeight = 16.sp,
                fontSize = 12.sp
            )
        }
    }
}
