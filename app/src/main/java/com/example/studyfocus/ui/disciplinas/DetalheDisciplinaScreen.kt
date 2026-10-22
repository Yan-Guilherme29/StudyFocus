package com.example.studyfocus.ui.disciplinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Flashcard
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
private val disciplinaFake = Disciplina(
    id = "1",
    nome = "Banco de Dados I",
    descricao = "Organização de dados, consultas avançadas e a estruturação de informações.",
    cor = 0xFF7C3AED
)

private val tarefasFake = listOf(
    Tarefa(
        id = "1",
        titulo = "Organização de dados",
        descricao = "Revisar normalização até a 3FN",
        prazo = 1_792_800_000_000L, // 24/10/2026
        prioridade = Prioridade.ALTA,
        status = StatusTarefa.EM_ANDAMENTO,
        disciplinaId = "1"
    )
)

private val flashcardsFake = listOf(
    Flashcard(
        id = "1",
        frente = "Como manter a consistência entre as informações?",
        verso = "Usando chaves primárias, chaves estrangeiras e fáceis de consultar.",
        caixa = 3,
        proximaRevisao = 1_792_800_000_000L,
        disciplinaId = "1"
    )
)

private fun formatarData(millis: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    formato.timeZone = TimeZone.getTimeZone("UTC")
    return formato.format(Date(millis))
}

@Composable
fun DetalheDisciplinaScreen(
    onVoltarClick: () -> Unit = {},
    onVerTodasTarefasClick: () -> Unit = {},
    onRevisarClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Topo: voltar + título
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVoltarClick) {
                Text(
                    text = "←",
                    fontSize = 26.sp,
                    color = Texto
                )
            }
            Text(
                text = disciplinaFake.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )
        }

        Spacer(Modifier.height(12.dp))

        // Descrição geral
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Indigo)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "DESCRIÇÃO GERAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = disciplinaFake.descricao,
                    fontSize = 14.sp,
                    color = Texto
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Tarefas ativas
        CabecalhoSecao(
            titulo = "Tarefas Ativas (${tarefasFake.size})",
            acao = "Ver todas",
            onAcaoClick = onVerTodasTarefasClick
        )
        tarefasFake.forEach { tarefa ->
            CardTarefa(tarefa)
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(12.dp))

        // Flashcards
        CabecalhoSecao(
            titulo = "Flashcards (${flashcardsFake.size})",
            acao = "Revisar",
            onAcaoClick = onRevisarClick
        )
        flashcardsFake.forEach { flashcard ->
            CardFlashcard(flashcard)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CabecalhoSecao(titulo: String, acao: String, onAcaoClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Texto
        )
        TextButton(onClick = onAcaoClick) {
            Text(text = acao, fontSize = 12.sp, color = Indigo)
        }
    }
}

@Composable
private fun CardTarefa(tarefa: Tarefa) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = tarefa.titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Texto,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Prazo: ${formatarData(tarefa.prazo)}",
                    fontSize = 12.sp,
                    color = TextoSuave
                )
            }
            BadgePrioridade(tarefa.prioridade)
        }
    }
}

@Composable
private fun BadgePrioridade(prioridade: Prioridade) {
    val (texto, corTexto, corFundo) = when (prioridade) {
        Prioridade.ALTA -> Triple("Alta", Color(0xFFDC2626), Color(0xFFFEE2E2))
        Prioridade.MEDIA -> Triple("Média", Color(0xFFD97706), Color(0xFFFEF3C7))
        Prioridade.BAIXA -> Triple("Baixa", Color(0xFF16A34A), Color(0xFFDCFCE7))
    }
    Text(
        text = texto,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = corTexto,
        modifier = Modifier
            .background(corFundo, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
private fun CardFlashcard(flashcard: Flashcard) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(
                text = "Caixa Leitner ${flashcard.caixa}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = flashcard.frente,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Texto
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalheDisciplinaScreenPreview() {
    StudyFocusTheme {
        DetalheDisciplinaScreen()
    }
}