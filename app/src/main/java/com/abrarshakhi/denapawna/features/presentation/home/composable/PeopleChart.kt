package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.LineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.data.ExtraStore

@Composable
fun PeopleChart(persons: List<Person>) {
    if (persons.isEmpty()) return

    var isVisible by rememberSaveable { mutableStateOf(true) }
    val chevronRotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else -90f,
        label = "chevron",
    )

    val modelProducer = remember { CartesianChartModelProducer() }
    val personNamesKey = remember { ExtraStore.Key<List<String>>() }

    LaunchedEffect(persons) {
        modelProducer.runTransaction {
            columnSeries { series(persons.map { it.totalAmount.toFloat() }) }
            extras { it[personNamesKey] = persons.map { p -> p.fullName } }
        }
    }

    val blueColumn = rememberLineComponent(Fill(BlueColor), 16.dp)
    val redColumn = rememberLineComponent(Fill(RedColor), 16.dp)

    val columnProvider = remember(blueColumn, redColumn) {
        object : ColumnCartesianLayer.ColumnProvider {
            override fun getColumn(
                entry: ColumnCartesianLayerModel.Entry,
                seriesIndex: Int,
                extraStore: ExtraStore,
            ): LineComponent = if (entry.y >= 0) blueColumn else redColumn

            override fun getWidestSeriesColumn(
                seriesIndex: Int,
                extraStore: ExtraStore,
            ): LineComponent = blueColumn
        }
    }

    val bottomAxisFormatter = CartesianValueFormatter { context, x, _ ->
        context.model.extraStore.getOrNull(personNamesKey)
            ?.getOrElse(x.toInt()) { "" }
            ?: ""
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Per person",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "${persons.size} people",
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = { isVisible = !isVisible },
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_forward_24),
                        contentDescription = if (isVisible) "Hide chart" else "Show chart",
                        tint = Color.Gray,
                        modifier = Modifier.rotate(chevronRotation + 90f),
                    )
                }
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    CartesianChartHost(
                        rememberCartesianChart(
                            rememberColumnCartesianLayer(columnProvider = columnProvider),
                            startAxis = VerticalAxis.rememberStart(),
                            bottomAxis = HorizontalAxis.rememberBottom(
                                valueFormatter = bottomAxisFormatter,
                            ),
                        ),
                        modelProducer,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
