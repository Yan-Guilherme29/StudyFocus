package com.example.studyfocus.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Prioridade
import com.example.studyfocus.data.model.SessaoEstudo
import com.example.studyfocus.data.model.StatusTarefa
import com.example.studyfocus.data.model.Tarefa
import com.example.studyfocus.ui.theme.StudyFocusTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

private val Indigo = Color(0xFF4F46E5)
private val Fundo = Color(0xFFF8F9FC)
private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)
private val Verde = Color(0xFF10B981)
private val Laranja = Color(0xFFF97316)

private const val UM_DIA = 24 * 60 * 60 * 1000L
private val agora = System.currentTimeMillis()

// Dados falsos (P1 é só visual)
private val nomeUsuarioFake = "Ana Beatriz"

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

// Tarefas da semana: 2 pendentes e 3 concluídas
private val tarefasFake = listOf(
    Tarefa(
        id = "1",
        titulo = "Exercícios de Modelagem",
        prazo = agora,
        prioridade = Prioridade.ALTA,
        status = StatusTarefa.PENDENTE,
        disciplinaId = "1"
    ),
    Tarefa(
        id = "2",
        titulo = "Trabalho de Algoritmos",
        prazo = agora + UM_DIA,
        prioridade = Prioridade.MEDIA,
        status = StatusTarefa.EM_ANDAMENTO,
        disciplinaId = "2"
    ),
    Tarefa(
        id = "3",
        titulo = "Lista de Limites",
        status = StatusTarefa.CONCLUIDA,
        disciplinaId = "3"
    ),
    Tarefa(
        id = "4",
        titulo = "Resumo de Normalização",
        status = StatusTarefa.CONCLUIDA,
        disciplinaId = "1"
    ),
    Tarefa(
        id = "5",
        titulo = "Implementar Pilha e Fila",
        status = StatusTarefa.CONCLUIDA,
        disciplinaId = "2"
    )
)

// Sessões de foco da semana (somam 3h20)
private val sessoesFake = listOf(
    SessaoEstudo(
        id = "1",
        disciplinaId = "1",
        tarefaId = "1",
        inicio = agora - UM_DIA,
        duracaoMin = 50
    ),
    SessaoEstudo(
        id = "2",
        disciplinaId = "2",
        tarefaId = "2",
        inicio = agora - 2 * UM_DIA,
        duracaoMin = 45
    ),
    SessaoEstudo(id = "3", disciplinaId = "3", inicio = agora - 3 * UM_DIA, duracaoMin = 60),
    SessaoEstudo(id = "4", disciplinaId = "1", inicio = agora - 4 * UM_DIA, duracaoMin = 45)
)

private val flashcardsParaRevisarFake = 12
private val flashcardsRevisadosFake = 47
private val diasSeguidosFake = 5

// "Hoje", "Amanhã" ou a data (dd/MM)
private fun textoPrazo(prazo: Long): String {
    val formato = SimpleDateFormat("dd/MM", Locale.forLanguageTag("pt-BR"))
    val dia = formato.format(Date(prazo))
    return when (dia) {
        formato.format(Date(agora)) -> "Hoje"
        formato.format(Date(agora + UM_DIA)) -> "Amanhã"
        else -> dia
    }
}

// Ex.: "Semana de 19/10 a 25/10" (de segunda a domingo)
private fun textoSemana(): String {
    val formato = SimpleDateFormat("dd/MM", Locale.forLanguageTag("pt-BR"))
    val calendario = Calendar.getInstance()
    // volta até a segunda-feira da semana atual
    while (calendario.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
        calendario.add(Calendar.DAY_OF_MONTH, -1)
    }
    val inicio = formato.format(calendario.time)
    calendario.add(Calendar.DAY_OF_MONTH, 6)
    val fim = formato.format(calendario.time)
    return "Semana de $inicio a $fim"
}

// Ex.: 200 minutos -> "3h20"
private fun formatarDuracao(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    return "${horas}h" + resto.toString().padStart(2, '0')
}

@Composable
fun HomeScreen(
    onTarefasClick: () -> Unit = {},
    onEstatisticasClick: () -> Unit = {},
    onRevisarClick: () -> Unit = {},
    onDisciplinaClick: (String) -> Unit = {}
) {
    val pendentes = tarefasFake
        .filter { it.status != StatusTarefa.CONCLUIDA }
        .sortedBy { it.prazo }
    val concluidas = tarefasFake.count { it.status == StatusTarefa.CONCLUIDA }
    val minutosEstudados = sessoesFake.sumOf { it.duracaoMin }

    Scaffold(
        containerColor = Fundo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Olá, $nomeUsuarioFake!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            Spacer(Modifier.height(16.dp))

            // Resumo da semana (os dois cards ficam com a mesma altura)
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CardResumo(
                    titulo = "Esta semana",
                    valor = if (pendentes.size == 1) "1 tarefa" else "${pendentes.size} tarefas",
                    corValor = Indigo,
                    onClick = onTarefasClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                CardResumo(
                    titulo = "Flashcards",
                    valor = "$flashcardsParaRevisarFake para revisar",
                    corValor = Verde,
                    onClick = onRevisarClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }

            Spacer(Modifier.height(16.dp))

            // Próximas tarefas
            CabecalhoSecao(titulo = "Próximas Tarefas")
            pendentes.forEach { tarefa ->
                CardTarefa(tarefa)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(8.dp))

            // Progresso semanal
            CabecalhoSecao(
                titulo = "Progresso Semanal",
                acao = "Ver detalhes →",
                onAcaoClick = onEstatisticasClick
            )
            CardProgresso(
                concluidas = concluidas,
                total = tarefasFake.size,
                minutosEstudados = minutosEstudados
            )

            Spacer(Modifier.height(16.dp))

            // Disciplinas em grade de 2 colunas
            CabecalhoSecao(titulo = "Suas Disciplinas")
            disciplinasFake.chunked(2).forEach { linha ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    linha.forEach { disciplina ->
                        CardDisciplina(
                            disciplina = disciplina,
                            onClick = { onDisciplinaClick(disciplina.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // completa a linha quando sobra só uma disciplina
                    if (linha.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun CabecalhoSecao(titulo: String, acao: String? = null, onAcaoClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Texto
        )
        if (acao != null) {
            TextButton(onClick = onAcaoClick) {
                Text(text = acao, fontSize = 12.sp, color = Indigo)
            }
        }
    }
}

@Composable
private fun CardResumo(
    titulo: String,
    valor: String,
    corValor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(text = titulo, fontSize = 12.sp, color = TextoSuave)
            Spacer(Modifier.height(4.dp))
            Text(
                text = valor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = corValor
            )
        }
    }
}

@Composable
private fun CardTarefa(tarefa: Tarefa) {
    val disciplina = disciplinasFake.firstOrNull { it.id == tarefa.disciplinaId }

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
            verticalAlignment = Alignment.CenterVertically
        ) {
            // bolinha com a cor da disciplina
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(disciplina?.cor ?: 0xFF9CA3AF), CircleShape)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = tarefa.titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Texto,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${disciplina?.nome ?: "Sem disciplina"} • ${textoPrazo(tarefa.prazo)}",
                    fontSize = 12.sp,
                    color = TextoSuave
                )
            }
            Spacer(Modifier.width(8.dp))
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
private fun CardProgresso(concluidas: Int, total: Int, minutosEstudados: Int) {
    val progresso = if (total > 0) concluidas.toFloat() / total else 0f
    val porcentagem = (progresso * 100).roundToInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = textoSemana(), fontSize = 12.sp, color = TextoSuave)
                Text(
                    text = "$concluidas de $total tarefas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Indigo
                )
            }

            Spacer(Modifier.height(10.dp))

            // Barra de progresso: fundo cinza + parte pintada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Borda, RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progresso)
                        .fillMaxHeight()
                        .background(Indigo, RoundedCornerShape(50))
                )
            }

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "0%", fontSize = 10.sp, color = TextoSuave)
                Text(
                    text = "$porcentagem% concluído",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Indigo
                )
                Text(text = "100%", fontSize = 10.sp, color = TextoSuave)
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = Borda)
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Estatistica(
                    valor = "$flashcardsRevisadosFake",
                    legenda = "Flashcards revisados",
                    cor = Verde,
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(color = Borda)
                Estatistica(
                    valor = formatarDuracao(minutosEstudados),
                    legenda = "Tempo de estudo",
                    cor = Indigo,
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider(color = Borda)
                Estatistica(
                    valor = "$diasSeguidosFake",
                    legenda = "Dias seguidos",
                    cor = Laranja,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Estatistica(valor: String, legenda: String, cor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = valor, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = cor)
        Text(
            text = legenda,
            fontSize = 11.sp,
            color = TextoSuave,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CardDisciplina(
    disciplina: Disciplina,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borda)
    ) {
        Column(Modifier.padding(6.dp)) {
            // faixinha com a cor da disciplina
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(4.dp)
                    .background(Color(disciplina.cor), RoundedCornerShape(50))
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = disciplina.nome,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Texto,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = disciplina.descricao,
                fontSize = 11.sp,
                color = TextoSuave,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StudyFocusTheme {
        HomeScreen()
    }
}
