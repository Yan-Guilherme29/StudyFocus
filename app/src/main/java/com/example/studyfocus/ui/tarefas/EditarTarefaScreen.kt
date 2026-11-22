package com.example.studyfocus.ui.tarefas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.studyfocus.data.model.Prioridade
import com.example.studyfocus.data.model.StatusTarefa
import com.example.studyfocus.data.model.Tarefa
import com.example.studyfocus.ui.theme.StudyFocusTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Dados falsos: só para o preview e como valor padrão dos parâmetros
private val tarefaFalsa = Tarefa(
    id = "1",
    titulo = "Organizar o banco de dados da livraria",
    descricao = "Revisar as tabelas e melhorar a estrutura para facilitar a manutenção.",
    prazo = 1_792_800_000_000L,
    prioridade = Prioridade.ALTA,
    status = StatusTarefa.EM_ANDAMENTO,
    disciplinaId = "1"
)

private val disciplinasFalsas = listOf(
    Disciplina(id = "1", nome = "Banco de Dados I"),
    Disciplina(id = "2", nome = "Estruturas de Dados"),
    Disciplina(id = "3", nome = "Cálculo Diferencial")
)

private fun nomePrioridade(p: Prioridade) = when (p) {
    Prioridade.BAIXA -> "Baixa"
    Prioridade.MEDIA -> "Média"
    Prioridade.ALTA -> "Alta"
}

private fun nomeStatus(s: StatusTarefa) = when (s) {
    StatusTarefa.PENDENTE -> "Pendente"
    StatusTarefa.EM_ANDAMENTO -> "Em andamento"
    StatusTarefa.CONCLUIDA -> "Concluída"
}

private fun formatoData() = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).apply {
    timeZone = TimeZone.getTimeZone("UTC")
    isLenient = false
}

private fun dataParaTexto(millis: Long): String =
    if (millis == 0L) "" else formatoData().format(Date(millis))

private fun textoParaData(texto: String): Long? =
    try {
        formatoData().parse(texto)?.time
    } catch (e: Exception) {
        null
    }

@Composable
fun EditarTarefaScreen(
    tarefa: Tarefa = tarefaFalsa,
    disciplinas: List<Disciplina> = disciplinasFalsas,
    carregando: Boolean = false,
    erro: String? = null,
    onSalvarClick: (Tarefa) -> Unit = {},
    onExcluirClick: (String) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    // A chave (tarefa.id) faz o formulário se preencher quando o dado chegar
    var titulo by remember(tarefa.id) { mutableStateOf(tarefa.titulo) }
    var descricao by remember(tarefa.id) { mutableStateOf(tarefa.descricao) }
    var prazo by remember(tarefa.id) { mutableStateOf(dataParaTexto(tarefa.prazo)) }
    var prioridade by remember(tarefa.id) { mutableStateOf(tarefa.prioridade) }
    var status by remember(tarefa.id) { mutableStateOf(tarefa.status) }
    var disciplinaId by remember(tarefa.id) { mutableStateOf(tarefa.disciplinaId) }

    val prazoInvalido = prazo.isNotBlank() && textoParaData(prazo) == null
    val podeSalvar = titulo.isNotBlank() && !prazoInvalido && !carregando

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
                text = "Editar tarefa",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(16.dp))

        Rotulo("Título da tarefa")
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
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
            placeholder = { Text("dd/mm/aaaa") },
            singleLine = true,
            isError = prazoInvalido,
            supportingText = if (prazoInvalido) {
                { Text("Use o formato dd/mm/aaaa") }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Prioridade")
        DropdownCampo(
            valor = nomePrioridade(prioridade),
            opcoes = Prioridade.values().map { nomePrioridade(it) },
            onSelecionar = { indice -> prioridade = Prioridade.values()[indice] }
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Status")
        DropdownCampo(
            valor = nomeStatus(status),
            opcoes = StatusTarefa.values().map { nomeStatus(it) },
            onSelecionar = { indice -> status = StatusTarefa.values()[indice] }
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Disciplina associada")
        DropdownCampo(
            valor = disciplinas.firstOrNull { it.id == disciplinaId }?.nome ?: "",
            placeholder = "Selecione uma disciplina",
            opcoes = disciplinas.map { it.nome },
            onSelecionar = { indice -> disciplinaId = disciplinas[indice].id }
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
                    tarefa.copy(
                        titulo = titulo,
                        descricao = descricao,
                        prazo = textoParaData(prazo) ?: tarefa.prazo,
                        prioridade = prioridade,
                        status = status,
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
                Text(text = "Salvar alterações", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onExcluirClick(tarefa.id) },
            enabled = !carregando,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Excluir tarefa", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownCampo(
    valor: String,
    opcoes: List<String>,
    onSelecionar: (Int) -> Unit,
    placeholder: String = ""
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
fun EditarTarefaScreenPreview() {
    StudyFocusTheme {
        EditarTarefaScreen()
    }
}