package com.example.studyfocus.ui.tarefas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Prioridade
import com.example.studyfocus.data.model.StatusTarefa
import com.example.studyfocus.data.model.Tarefa
import com.example.studyfocus.ui.theme.StudyFocusTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val Indigo = Color(0xFF4F46E5)
private val Fundo = Color(0xFFF8F9FC)
private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)

// Dados falsos (P1 é só visual)
private val disciplinasFake = listOf(
    Disciplina(id = "1", nome = "Banco de Dados I", cor = 0xFF7C3AED),
    Disciplina(id = "2", nome = "Estruturas de Dados", cor = 0xFF3B82F6)
)

private val tarefasFake = listOf(
    Tarefa(
        id = "1",
        titulo = "Organizar o banco de dados da livraria",
        descricao = "Revisar as tabelas e melhorar a estrutura para facilitar a manutenção.",
        prazo = 1_792_800_000_000L, // 24/10/2026
        prioridade = Prioridade.ALTA,
        status = StatusTarefa.EM_ANDAMENTO,
        disciplinaId = "1"
    ),
    Tarefa(
        id = "2",
        titulo = "Implementar busca em árvore",
        descricao = "Criar uma busca eficiente para listar os dados estruturados em árvore.",
        prazo = 1_793_145_600_000L, // 28/10/2026
        prioridade = Prioridade.MEDIA,
        status = StatusTarefa.PENDENTE,
        disciplinaId = "2"
    )
)

private enum class Filtro { TODAS, PENDENTES, ALTA }

private fun formatarData(millis: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    formato.timeZone = TimeZone.getTimeZone("UTC")
    return formato.format(Date(millis))
}

@Composable
fun TarefasScreen(
    onNovaTarefaClick: () -> Unit = {},
    onTarefaClick: (String) -> Unit = {}
) {
    var filtro by remember { mutableStateOf(Filtro.TODAS) }

    val pendentes = tarefasFake.filter { it.status == StatusTarefa.PENDENTE }
    val altas = tarefasFake.filter { it.prioridade == Prioridade.ALTA }
    val visiveis = when (filtro) {
        Filtro.TODAS -> tarefasFake
        Filtro.PENDENTES -> pendentes
        Filtro.ALTA -> altas
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Suas Tarefas",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Texto
        )

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ChipFiltro("Todas (${tarefasFake.size})", filtro == Filtro.TODAS) {
                filtro = Filtro.TODAS
            }
            ChipFiltro("Pendentes (${pendentes.size})", filtro == Filtro.PENDENTES) {
                filtro = Filtro.PENDENTES
            }
            ChipFiltro("Alta Prioridade (${altas.size})", filtro == Filtro.ALTA) {
                filtro = Filtro.ALTA
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { CardNovaTarefa(onNovaTarefaClick) }
            items(visiveis, key = { it.id }) { tarefa ->
                CardTarefa(tarefa = tarefa, onClick = { onTarefaClick(tarefa.id) })
            }
        }
    }
}

@Composable
private fun ChipFiltro(texto: String, selecionado: Boolean, onClick: () -> Unit) {
    Text(
        text = texto,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (selecionado) Color.White else Indigo,
        modifier = Modifier
            .background(if (selecionado) Indigo else Color.White, RoundedCornerShape(50))
            .border(1.dp, Indigo, RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun CardNovaTarefa(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFEEF2FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Indigo)
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = "Criar nova tarefa",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Texto
                )
                Text(
                    text = "Adicione uma nova tarefa e organize seu cronograma.",
                    fontSize = 12.sp,
                    color = TextoSuave
                )
            }
        }
    }
}

@Composable
private fun CardTarefa(tarefa: Tarefa, onClick: () -> Unit) {
    val disciplina = disciplinasFake.firstOrNull { it.id == tarefa.disciplinaId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(14.dp)) {
            // Disciplina + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(disciplina?.cor ?: 0xFF9CA3AF), CircleShape)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = disciplina?.nome ?: "Sem disciplina",
                        fontSize = 11.sp,
                        color = TextoSuave
                    )
                }
                BadgeStatus(tarefa.status)
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = tarefa.titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )
            Spacer(Modifier.height(2.dp))
            Text(text = tarefa.descricao, fontSize = 12.sp, color = TextoSuave)

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prazo: ${formatarData(tarefa.prazo)}",
                    fontSize = 11.sp,
                    color = TextoSuave
                )
                TextoPrioridade(tarefa.prioridade)
            }

            Spacer(Modifier.height(10.dp))

            // Botão visual (P1: sem ação real)
            Text(
                text = "✓  Marcar como concluída",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Indigo,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Borda, RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun BadgeStatus(status: StatusTarefa) {
    val (texto, corTexto, corFundo) = when (status) {
        StatusTarefa.PENDENTE -> Triple("Pendente", Color(0xFF6B7280), Color(0xFFF3F4F6))
        StatusTarefa.EM_ANDAMENTO -> Triple("Em andamento", Color(0xFF4F46E5), Color(0xFFEEF2FF))
        StatusTarefa.CONCLUIDA -> Triple("Concluída", Color(0xFF16A34A), Color(0xFFDCFCE7))
    }
    Text(
        text = texto,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = corTexto,
        modifier = Modifier
            .background(corFundo, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
private fun TextoPrioridade(prioridade: Prioridade) {
    val (texto, cor) = when (prioridade) {
        Prioridade.ALTA -> "Alta" to Color(0xFFDC2626)
        Prioridade.MEDIA -> "Média" to Color(0xFFD97706)
        Prioridade.BAIXA -> "Baixa" to Color(0xFF16A34A)
    }
    Text(text = texto, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = cor)
}

@Preview(showBackground = true)
@Composable
fun TarefasScreenPreview() {
    StudyFocusTheme {
        TarefasScreen()
    }
}