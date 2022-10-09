package com.github.hatable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
private fun MainView() {
    val gridViewModel = viewModel(modelClass = GridViewModel::class.java)
    val itemState = gridViewModel.itemsState
    val state = itemState.value
    var enforceMore by remember {
        mutableStateOf(true)
    }
    val limit = 6
    val moreIconState = state.items.size > 6
    val c = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        GridView(modifier = Modifier.weight(1f), state.items) {
            gridViewModel.onEvent(UIEvent.OnGridItemSelected(it))
        }
        if (moreIconState) {
            val buttonText = enforceMore then "More" ?: "Less"
            Button(
                onClick = {
                    enforceMore = !enforceMore
                    gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = buttonText)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Button(
            onClick = { gridViewModel.onEvent(UIEvent.OnSubmitPressed) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(text = "Collect Selected")
        }
    }
}

@Composable
fun GridView(
    modifier: Modifier = Modifier,
    items: List<GridProps>,
    onItemSelected: (GridProps) -> Unit
) {
    LazyVerticalGrid( // on below line we are setting the
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(10.dp)
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
            .padding(8.dp),
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
