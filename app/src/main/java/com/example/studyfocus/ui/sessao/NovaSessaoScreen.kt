package com.example.studyfocus.ui.sessao

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.data.model.Disciplina
import com.example.studyfocus.data.model.Tarefa
import com.example.studyfocus.ui.theme.StudyFocusTheme

private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)
private val FormaCampo = RoundedCornerShape(12.dp)

private val disciplinasFake = listOf(
    Disciplina(id = "1", nome = "Banco de Dados I"),
    Disciplina(id = "2", nome = "Estruturas de Dados"),
    Disciplina(id = "3", nome = "Cálculo Diferencial")
)

private val tarefasFake = listOf(
    Tarefa(id = "1", titulo = "Normalização até 3NF", disciplinaId = "1"),
    Tarefa(id = "2", titulo = "Organizar o banco da livraria", disciplinaId = "1"),
    Tarefa(id = "3", titulo = "Implementar busca em árvore", disciplinaId = "2")
)

// Rótulo acima de cada campo
@Composable
private fun Rotulo(texto: String) {
    Text(
        text = texto,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Texto
    )
    Spacer(modifier = Modifier.height(6.dp))
}

// Campos brancos com borda cinza, como nas outras telas
@Composable
private fun coresCampo() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = Borda
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaSessaoScreen(onIniciarClick: () -> Unit = {}) {
    var menuDisciplinaAberto by remember { mutableStateOf(false) }
    var disciplinaSelecionada by remember { mutableStateOf<Disciplina?>(null) }
    var menuTarefaAberto by remember { mutableStateOf(false) }
    var tarefaSelecionada by remember { mutableStateOf<Tarefa?>(null) }
    val tarefasDaDisciplina = tarefasFake.filter { it.disciplinaId == disciplinaSelecionada?.id }
    var tempoSelecionado by remember { mutableStateOf(25) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nova Sessão de Foco",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Texto
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Prepare seu ambiente para estudar sem distrações",
            fontSize = 14.sp,
            color = TextoSuave
        )

        Spacer(modifier = Modifier.height(24.dp))
        Rotulo("Qual disciplina vai estudar hoje?")
        ExposedDropdownMenuBox(
            expanded = menuDisciplinaAberto,
            onExpandedChange = { menuDisciplinaAberto = it }
        ) {
            OutlinedTextField(
                value = disciplinaSelecionada?.nome ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecione uma disciplina") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuDisciplinaAberto)
                },
                shape = FormaCampo,
                colors = coresCampo(),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuDisciplinaAberto,
                onDismissRequest = { menuDisciplinaAberto = false }
            ) {
                disciplinasFake.forEach { disciplina ->
                    DropdownMenuItem(
                        text = { Text(disciplina.nome) },
                        onClick = {
                            disciplinaSelecionada = disciplina
                            tarefaSelecionada = null
                            menuDisciplinaAberto = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Rotulo("Vincular tarefa (opcional)")
        ExposedDropdownMenuBox(
            expanded = menuTarefaAberto,
            onExpandedChange = { menuTarefaAberto = it }
        ) {
            OutlinedTextField(
                enabled = true,
                value = tarefaSelecionada?.titulo ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecione uma tarefa") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuTarefaAberto)
                },
                shape = FormaCampo,
                colors = coresCampo(),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuTarefaAberto,
                onDismissRequest = { menuTarefaAberto = false }
            ) {
                tarefasDaDisciplina.forEach { tarefa ->
                    DropdownMenuItem(
                        text = { Text(tarefa.titulo) },
                        onClick = {
                            tarefaSelecionada = tarefa
                            menuTarefaAberto = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Rotulo("Tempo de duração")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val opcoes = listOf(25 to "25 min", 50 to "50 min", 0 to "Livre")
            opcoes.forEach { (valor, rotulo) ->
                if (tempoSelecionado == valor) {
                    Button(
                        onClick = { tempoSelecionado = valor },
                        shape = FormaCampo,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(rotulo)
                    }
                } else {
                    OutlinedButton(
                        onClick = { tempoSelecionado = valor },
                        shape = FormaCampo,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(rotulo)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            enabled = disciplinaSelecionada != null,
            onClick = onIniciarClick,
            shape = FormaCampo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sessão de Estudo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NovaSessaoScreenPreview() {
    StudyFocusTheme {
        NovaSessaoScreen()
    }
}