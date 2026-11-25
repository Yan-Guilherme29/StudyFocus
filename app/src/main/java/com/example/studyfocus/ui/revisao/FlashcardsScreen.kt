package com.example.studyfocus.ui.revisao

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Flashcard
import com.example.studyfocus.ui.theme.StudyFocusTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Dados falsos: só para o preview e como valor padrão dos parâmetros
private const val UM_DIA = 24 * 60 * 60 * 1000L
private val agoraFalso = System.currentTimeMillis()

private val disciplinaFalsa = Disciplina(
    id = "1",
    nome = "Banco de Dados I",
    descricao = "Banco de Dados I foca em modelagem conceitual, lógica e física, consultas " +
            "avançadas e estruturação de informações para manter dados consistentes e fáceis de consultar.",
    cor = 0xFF7C3AED
)

private val flashcardsFalsos = listOf(
    Flashcard(
        id = "1",
        frente = "Qual é o objetivo da normalização?",
        verso = "Reduzir a repetição de dados e evitar problemas ao inserir, alterar ou excluir registros.",
        caixa = 5,
        proximaRevisao = agoraFalso + 10 * UM_DIA,
        disciplinaId = "1"
    ),
    Flashcard(
        id = "2",
        frente = "Como manter a consistência entre as informações?",
        verso = "Usando chaves primárias, chaves estrangeiras e restrições para garantir que os " +
                "dados sejam consistentes e fáceis de consultar.",
        caixa = 2,
        proximaRevisao = agoraFalso,
        disciplinaId = "1"
    ),
    Flashcard(
        id = "3",
        frente = "Qual a diferença entre chave primária e chave estrangeira?",
        verso = "A chave primária identifica cada registro da tabela; a chave estrangeira aponta " +
                "para a chave primária de outra tabela.",
        caixa = 3,
        proximaRevisao = agoraFalso + 2 * UM_DIA,
        disciplinaId = "1"
    )
)

// No Sistema Leitner, o cartão está consolidado quando chega na última caixa
private const val CAIXA_CONSOLIDADO = 5

private enum class Filtro(val texto: String) {
    TODOS("Todos"),
    PARA_REVISAR("Para revisar"),
    CONSOLIDADOS("Consolidados")
}

private fun consolidado(flashcard: Flashcard) = flashcard.caixa >= CAIXA_CONSOLIDADO

private fun paraRevisar(flashcard: Flashcard, agora: Long) = flashcard.proximaRevisao <= agora

// "Revisar hoje" ou "Próxima revisão: 28/11/2026"
private fun textoRevisao(flashcard: Flashcard, agora: Long): String =
    if (paraRevisar(flashcard, agora)) {
        "Revisar hoje"
    } else {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"))
        "Próxima revisão: ${formato.format(Date(flashcard.proximaRevisao))}"
    }

private fun mensagemVazia(filtro: Filtro) = when (filtro) {
    Filtro.TODOS -> "Nenhum flashcard nesta disciplina ainda. Crie o primeiro abaixo."
    Filtro.PARA_REVISAR -> "Nenhum flashcard para revisar agora."
    Filtro.CONSOLIDADOS -> "Nenhum flashcard consolidado ainda."
}

@Composable
fun FlashcardsScreen(
    disciplina: Disciplina = disciplinaFalsa,
    flashcards: List<Flashcard> = flashcardsFalsos,
    carregando: Boolean = false,
    erro: String? = null,
    onFlashcardClick: (id: String) -> Unit = {},
    onNovoFlashcardClick: () -> Unit = {},
    onIniciarRevisaoClick: () -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    // Filtro escolhido nos chips (estado só da tela, não vem do Firebase)
    var filtro by remember { mutableStateOf(Filtro.TODOS) }
    val agora = System.currentTimeMillis()

    val visiveis = when (filtro) {
        Filtro.TODOS -> flashcards
        Filtro.PARA_REVISAR -> flashcards.filter { paraRevisar(it, agora) }
        Filtro.CONSOLIDADOS -> flashcards.filter { consolidado(it) }
    }
    val temParaRevisar = flashcards.any { paraRevisar(it, agora) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Topo: voltar + título + nome da disciplina
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onVoltarClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column {
                    Text(
                        text = "Flashcards",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (disciplina.nome.isNotBlank()) {
                        Text(
                            text = disciplina.nome,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        if (disciplina.descricao.isNotBlank()) {
            item { CardResumo(disciplina.descricao) }
        }

        // Filtros
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Filtro.entries.forEach { opcao ->
                    FilterChip(
                        selected = filtro == opcao,
                        onClick = { filtro = opcao },
                        label = { Text(opcao.texto) },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Título da lista + iniciar revisão (só libera se houver cartão para revisar)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Flashcards (${visiveis.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = onIniciarRevisaoClick, enabled = temParaRevisar) {
                    Text(text = "Iniciar revisão")
                }
            }
        }

        if (erro != null) {
            item {
                Text(
                    text = erro,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        when {
            carregando -> item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            visiveis.isEmpty() -> item {
                Text(
                    text = mensagemVazia(filtro),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            else -> items(visiveis, key = { it.id }) { flashcard ->
                CardFlashcard(
                    flashcard = flashcard,
                    agora = agora,
                    onClick = { onFlashcardClick(flashcard.id) }
                )
            }
        }

        item { CardCriarFlashcard(onClick = onNovoFlashcardClick) }
    }
}

@Composable
private fun CardResumo(descricao: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "RESUMO DA DISCIPLINA",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun CardFlashcard(flashcard: Flashcard, agora: Long, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = bordaCartao()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row {
                Text(
                    text = flashcard.frente,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                EtiquetaStatus(consolidado(flashcard))
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = textoRevisao(flashcard, agora),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EtiquetaStatus(consolidado: Boolean) {
    val texto = if (consolidado) "Consolidado" else "Em revisão"
    val corTexto = if (consolidado) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.primary
    }
    val corFundo = if (consolidado) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    }
    Text(
        text = texto,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        color = corTexto,
        modifier = Modifier
            .background(corFundo, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
private fun CardCriarFlashcard(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = bordaCartao()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = "Criar flashcard",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Adicione uma nova pergunta e resposta para reforçar o estudo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun coresCartao() = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
)

@Composable
private fun bordaCartao() = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)

@Preview(showBackground = true)
@Composable
fun FlashcardsScreenPreview() {
    StudyFocusTheme {
        FlashcardsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardsScreenVaziaPreview() {
    StudyFocusTheme {
        FlashcardsScreen(flashcards = emptyList())
    }
}
