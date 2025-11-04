package com.example.dz1compose

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.dz1compose.ui.theme.Dz1ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dz1ComposeTheme {
                MainScreen()
            }
        }
    }
}

data class MyCell(
    val id: Int
)

@Composable
fun MyCellContent(cell: MyCell){
    var cellColour = Color.Blue
    if (cell.id % 2 == 0){
        cellColour = Color.Red
    }
    Box(
        modifier = Modifier
            .padding(15.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 24.dp,
                    topStart = 24.dp,
                    bottomEnd = 24.dp,
                    bottomStart = 24.dp
                )
            )
            .size(100.dp)
            .background(cellColour),
        contentAlignment = Alignment.Center,

        ){
        Text(
            text = cell.id.toString(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            color = Color.White
        )
    }
}

@Composable
fun MainScreen(){

    val count: MutableState<Int> = rememberSaveable { mutableIntStateOf(0) }
    val list = List(size = count.value) { MyCell(id = it) }
    
    val configuration = LocalConfiguration.current

    val cellsCount: Int = when (configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> {
            4
        }
        else -> {
            3
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(cellsCount),
            modifier = Modifier
                .weight(1f)
        ) {
            items(
                count = list.size,
                key = {it}
            ) { position ->
                MyCellContent(
                    list[position]
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    count.value += 1
                }
            ) {
                Text("+")
            }
        }
    }
}


@Preview (showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}