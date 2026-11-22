package com.example.studyfocus.ui.disciplinas

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.ui.theme.StudyFocusTheme

// Dados falsos: só para o preview e como valor padrão dos parâmetros
private val disciplinaFalsa = Disciplina(
    id = "1",
    nome = "Banco de Dados I",
    descricao = "Organização de dados, consultas avançadas e a estruturação de informações.",
    cor = 0xFF7C3AED
)

// Opções de cor de identificação (ARGB como Long, igual ao model)
private val coresDisponiveis: List<Long> = listOf(
    0xFF7C3AED, // roxo
    0xFF3B82F6, // azul
    0xFFF97316, // laranja
    0xFF22C55E, // verde
    0xFFEAB308, // amarelo
    0xFFEC4899  // rosa
)

@Composable
fun EditarDisciplinaScreen(
    disciplina: Disciplina = disciplinaFalsa,
    carregando: Boolean = false,
    erro: String? = null,
    onSalvarClick: (Disciplina) -> Unit = {},
    onExcluirClick: (String) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    // A chave (disciplina.id) faz o formulário se preencher quando o dado chegar
    var nome by remember(disciplina.id) { mutableStateOf(disciplina.nome) }
    var descricao by remember(disciplina.id) { mutableStateOf(disciplina.descricao) }
    var cor by remember(disciplina.id) { mutableStateOf(disciplina.cor) }

    val podeSalvar = nome.isNotBlank() && !carregando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVoltarClick) {
                Text(
                    text = "←",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "Editar disciplina",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(16.dp))

        Rotulo("Nome da disciplina")
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Descrição")
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            minLines = 4,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Cor de identificação")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            coresDisponiveis.forEach { opcao ->
                BolinhaCor(
                    cor = opcao,
                    selecionada = opcao == cor,
                    onClick = { cor = opcao }
                )
            }
        }

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
                    disciplina.copy(nome = nome, descricao = descricao, cor = cor)
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
                Text(text = "Salvar alterações", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onExcluirClick(disciplina.id) },
            enabled = !carregando,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Excluir disciplina", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun BolinhaCor(cor: Long, selecionada: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(cor), CircleShape)
            .then(
                if (selecionada) {
                    Modifier.border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selecionada) {
            Text(text = "✓", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
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
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface
)

@Preview(showBackground = true)
@Composable
fun EditarDisciplinaScreenPreview() {
    StudyFocusTheme {
        EditarDisciplinaScreen()
    }
}