package com.example.studyfocus.ui.revisao

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Flashcard
import com.example.studyfocus.ui.theme.StudyFocusTheme

// Dados falsos: só para o preview e como valor padrão dos parâmetros
private val flashcardFalso = Flashcard(
    id = "2",
    frente = "Como manter a consistência entre as informações?",
    verso = "Usando chaves primárias, chaves estrangeiras e restrições para garantir que os " +
            "dados sejam consistentes e fáceis de consultar.",
    caixa = 2,
    disciplinaId = "1"
)

private val disciplinasFalsas = listOf(
    Disciplina(id = "1", nome = "Banco de Dados I"),
    Disciplina(id = "2", nome = "Estruturas de Dados"),
    Disciplina(id = "3", nome = "Cálculo Diferencial")
)

@Composable
fun EditarFlashcardScreen(
    flashcard: Flashcard = flashcardFalso,
    disciplinas: List<Disciplina> = disciplinasFalsas,
    carregando: Boolean = false,
    erro: String? = null,
    onSalvarClick: (Flashcard) -> Unit = {},
    onExcluirClick: (String) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    // A chave (flashcard.id) faz o formulário se preencher quando o dado chegar
    var frente by remember(flashcard.id) { mutableStateOf(flashcard.frente) }
    var verso by remember(flashcard.id) { mutableStateOf(flashcard.verso) }
    var disciplinaId by remember(flashcard.id) { mutableStateOf(flashcard.disciplinaId) }

    // Flashcard sem id ainda não foi salvo: a mesma tela serve para criar um novo
    val novo = flashcard.id.isBlank()
    val nomeDisciplina = disciplinas.firstOrNull { it.id == disciplinaId }?.nome ?: ""
    val podeSalvar = frente.isNotBlank() && verso.isNotBlank() &&
            disciplinaId.isNotBlank() && !carregando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Topo: voltar + título + nome da disciplina
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
                    text = if (novo) "Novo flashcard" else "Editar flashcard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (nomeDisciplina.isNotBlank()) {
                    Text(
                        text = nomeDisciplina,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Prévia: mostra como o cartão vai ficar enquanto a pessoa digita
        CardPrevia(frente = frente, verso = verso, nomeDisciplina = nomeDisciplina)

        Spacer(Modifier.height(16.dp))

        Rotulo("Disciplina")
        DropdownCampo(
            valor = nomeDisciplina,
            placeholder = "Selecione uma disciplina",
            opcoes = disciplinas.map { it.nome },
            onSelecionar = { indice -> disciplinaId = disciplinas[indice].id }
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Frente (pergunta)")
        OutlinedTextField(
            value = frente,
            onValueChange = { frente = it },
            placeholder = { Text("Escreva a pergunta") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Verso (resposta)")
        OutlinedTextField(
            value = verso,
            onValueChange = { verso = it },
            placeholder = { Text("Escreva a resposta") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        if (erro != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = erro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                onSalvarClick(
                    flashcard.copy(
                        frente = frente.trim(),
                        verso = verso.trim(),
                        disciplinaId = disciplinaId
                    )
                )
            },
            enabled = podeSalvar,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = if (novo) "Criar flashcard" else "Salvar alterações",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Excluir só aparece para um flashcard que já existe
        if (!novo) {
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { onExcluirClick(flashcard.id) },
                enabled = !carregando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Excluir flashcard", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun CardPrevia(frente: String, verso: String, nomeDisciplina: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PRÉVIA DO CARTÃO",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                if (nomeDisciplina.isNotBlank()) {
                    Text(
                        text = nomeDisciplina,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            // O "cartão" em si: frente em cima, verso embaixo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ) {
                LadoDoCartao(
                    rotulo = "FRENTE / PERGUNTA",
                    texto = frente,
                    textoVazio = "A pergunta aparece aqui",
                    destaque = true
                )
                Spacer(Modifier.height(10.dp))
                LadoDoCartao(
                    rotulo = "VERSO / RESPOSTA",
                    texto = verso,
                    textoVazio = "A resposta aparece aqui",
                    destaque = false
                )
            }
        }
    }
}

@Composable
private fun LadoDoCartao(rotulo: String, texto: String, textoVazio: String, destaque: Boolean) {
    Text(
        text = rotulo,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = texto.ifBlank { textoVazio },
        style = if (destaque) {
            MaterialTheme.typography.titleSmall
        } else {
            MaterialTheme.typography.bodyMedium
        },
        fontWeight = if (destaque) FontWeight.SemiBold else null,
        color = if (texto.isBlank()) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )
}

@Composable
private fun Rotulo(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun camposCores() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownCampo(
    valor: String,
    placeholder: String,
    opcoes: List<String>,
    onSelecionar: (Int) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
    ) {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(placeholder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opcoes.forEachIndexed { indice, texto ->
                DropdownMenuItem(
                    text = { Text(texto) },
                    onClick = {
                        onSelecionar(indice)
                        expandido = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditarFlashcardScreenPreview() {
    StudyFocusTheme {
        EditarFlashcardScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun NovoFlashcardScreenPreview() {
    StudyFocusTheme {
        EditarFlashcardScreen(flashcard = Flashcard(disciplinaId = "1"))
    }
}
