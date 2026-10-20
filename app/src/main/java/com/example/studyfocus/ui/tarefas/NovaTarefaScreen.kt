package com.example.studyfocus.ui.tarefas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Prioridade

private val Indigo = Color(0xFF4F46E5)
private val Fundo = Color(0xFFF8F9FC)
private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)

// Dados falsos (P1 é só visual)
private val disciplinasFake = listOf(
    Disciplina(id = "1", nome = "Banco de Dados I"),
    Disciplina(id = "2", nome = "Estruturas de Dados"),
    Disciplina(id = "3", nome = "Cálculo Diferencial")
)

private fun nomePrioridade(p: Prioridade) = when (p) {
    Prioridade.BAIXA -> "Baixa"
    Prioridade.MEDIA -> "Média"
    Prioridade.ALTA -> "Alta"
}

@Composable
fun NovaTarefaScreen(
    onVoltarClick: () -> Unit = {},
    onCriarClick: () -> Unit = {}
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var prazo by remember { mutableStateOf("") }
    var prioridade by remember { mutableStateOf<Prioridade?>(null) }
    var disciplina by remember { mutableStateOf<Disciplina?>(null) }

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
                Text(text = "←", fontSize = 26.sp, color = Texto)
            }
            Text(
                text = "Nova tarefa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )
        }

        Spacer(Modifier.height(16.dp))

        Rotulo("Título da tarefa")
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            placeholder = { Text("Entregar projeto", color = TextoSuave) },
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
            placeholder = { Text("Descreva o que precisa ser feito.", color = TextoSuave) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Prazo")
        OutlinedTextField(
            value = prazo,
            onValueChange = { prazo = it },
            placeholder = { Text("Selecione uma data", color = TextoSuave) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Prioridade")
        DropdownCampo(
            valor = prioridade?.let { nomePrioridade(it) } ?: "",
            placeholder = "Selecione a prioridade",
            opcoes = Prioridade.values().map { nomePrioridade(it) },
            onSelecionar = { indice -> prioridade = Prioridade.values()[indice] }
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Disciplina associada")
        DropdownCampo(
            valor = disciplina?.nome ?: "",
            placeholder = "Selecione uma disciplina",
            opcoes = disciplinasFake.map { it.nome },
            onSelecionar = { indice -> disciplina = disciplinasFake[indice] }
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onCriarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo)
        ) {
            Text(text = "Criar tarefa", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun Rotulo(texto: String) {
    Text(
        text = texto,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextoSuave,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun camposCores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Indigo,
    unfocusedBorderColor = Borda,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedTextColor = Texto,
    unfocusedTextColor = Texto
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
            placeholder = { Text(placeholder, color = TextoSuave) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .menuAnchor()
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
fun NovaTarefaScreenPreview() {
    NovaTarefaScreen()
}