package com.example.studyfocus.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private class ItemNavegacao(
    val rota: String,
    val texto: String,
    val icone: ImageVector
)

// Ícone desenhado a partir de um path SVG (sem biblioteca extra)
private fun icone(path: String): ImageVector =
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).addPath(
        pathData = addPathNodes(path),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ).build()

private val itens = listOf(

    ItemNavegacao(
        Rotas.HOME,
        "Início",
        icone("M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2zM9 22V12h6v10")
    ),
    ItemNavegacao(
        Rotas.DISCIPLINAS,
        "Disciplinas",
        icone("M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2zM22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z")
    ),

    ItemNavegacao(
        Rotas.TAREFAS,
        "Tarefas",
        icone("M5 3h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2zM9 12l2 2 4-4")
    ),

    ItemNavegacao(
        Rotas.NOVA_SESSAO,
        "Sessão",
        icone("M12 2a10 10 0 1 0 0 20a10 10 0 1 0 0-20zM12 6v6l4 2")
    ),

    ItemNavegacao(
        Rotas.ESTATISTICAS,
        "Estatísticas",
        icone("M3 3v16a2 2 0 0 0 2 2h16M18 17V9M13 17V5M8 17v-3")
    )


)

@Composable
fun BarraInferior(
    rotaSelecionada: String?,
    onItemClick: (String) -> Unit
) {
    Column {
        HorizontalDivider()
        NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
            itens.forEach { item ->
                NavigationBarItem(
                    selected = item.rota == rotaSelecionada,
                    onClick = { onItemClick(item.rota) },
                    icon = { Icon(imageVector = item.icone, contentDescription = null) },
                    label = { Text(text = item.texto, fontSize = 11.sp, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF6B7280),
                        unselectedTextColor = Color(0xFF6B7280)
                    )
                )
            }
        }
    }
}