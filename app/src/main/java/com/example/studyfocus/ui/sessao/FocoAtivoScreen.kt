package com.example.studyfocus.ui.sessao

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyfocus.ui.theme.StudyFocusTheme
import kotlinx.coroutines.delay

@Composable
fun FocoAtivoScreen(
    nomeDisciplina: String = "Banco de Dados I",
    tituloTarefa: String? = "Normalização até 3NF",
    duracaoMin: Int = 50,
    onPausarClick: () -> Unit = {},
    onConcluirClick: () -> Unit = {}
) {
    val totalSegundos = duracaoMin * 60
    var segundosRestantes by remember { mutableStateOf(totalSegundos) }

    LaunchedEffect(Unit) {
        while (segundosRestantes > 0) {
            delay(1000)
            segundosRestantes--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Foco Ativo",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Disciplina: $nomeDisciplina")
                Text(text = "Tarefa vinculada: ${tituloTarefa ?: "Nenhuma"}")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { segundosRestantes / totalSegundos.toFloat() },
                modifier = Modifier.size(220.dp),
                strokeWidth = 8.dp
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatarTempo(segundosRestantes),
                    style = MaterialTheme.typography.displayMedium
                )
                Text(text = "FOCO ATIVO")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Início", style = MaterialTheme.typography.bodySmall)
                Text(text = "09:41", style = MaterialTheme.typography.titleMedium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Duração planejada", style = MaterialTheme.typography.bodySmall)
                Text(text = "$duracaoMin min", style = MaterialTheme.typography.titleMedium)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onPausarClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Pausar")
            }
            Button(
                onClick = onConcluirClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Concluir")
            }
        }
    }
}

private fun formatarTempo(segundos: Int): String {
    val minutos = segundos / 60
    val resto = segundos % 60
    return minutos.toString().padStart(2, '0') + ":" + resto.toString().padStart(2, '0')
}

@Preview(showBackground = true)
@Composable
fun FocoAtivoScreenPreview() {
    StudyFocusTheme {
        FocoAtivoScreen()
    }
}