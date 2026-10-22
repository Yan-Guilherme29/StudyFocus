package com.example.studyfocus.ui.disciplinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.ui.theme.StudyFocusTheme

private val Indigo = Color(0xFF4F46E5)
private val IndigoClaro = Color(0xFFEEF2FF)
private val Fundo = Color(0xFFF8F9FC)
private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)

// Dados falsos (P1 é só visual)
private val disciplinasFake = listOf(
    Disciplina(
        id = "1",
        nome = "Banco de Dados I",
        descricao = "Modelagem conceitual, lógica e física. SQL avançado.",
        cor = 0xFF7C3AED
    ),
    Disciplina(
        id = "2",
        nome = "Estruturas de Dados",
        descricao = "Árvores, grafos e algoritmos de busca.",
        cor = 0xFF3B82F6
    ),
    Disciplina(
        id = "3",
        nome = "Cálculo Diferencial",
        descricao = "Limites, derivadas e integrais definidas.",
        cor = 0xFFF97316
    )
)

@Composable
fun DisciplinasScreen(
    onDisciplinaClick: (String) -> Unit = {},
) {
    Scaffold(
        containerColor = Fundo,

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Disciplinas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Texto,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            items(disciplinasFake, key = { it.id }) { disciplina ->
                CardDisciplina(
                    disciplina = disciplina,
                    onClick = { onDisciplinaClick(disciplina.id) }
                )
            }
        }
    }
}

@Composable
private fun CardDisciplina(disciplina: Disciplina, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(16.dp)) {
            // Faixinha com a cor da disciplina + seta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(5.dp)
                        .background(Color(disciplina.cor), RoundedCornerShape(50))
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(IndigoClaro, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = IconeSeta,
                        contentDescription = "Abrir disciplina",
                        tint = Indigo,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = disciplina.nome,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = disciplina.descricao,
                fontSize = 13.sp,
                color = TextoSuave
            )
        }
    }
}

// Ícones desenhados a partir de um path SVG (estilo contorno, igual ao Figma).
// Assim não precisamos adicionar uma biblioteca de ícones no projeto.
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

private val IconeSeta = icone("M5 12h14M12 5l7 7-7 7")

@Preview(showBackground = true)
@Composable
fun DisciplinaScreenPreview() {
    StudyFocusTheme {
        DisciplinasScreen()
    }
}
