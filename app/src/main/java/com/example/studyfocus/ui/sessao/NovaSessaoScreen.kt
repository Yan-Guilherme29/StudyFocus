package com.example.studyfocus.ui.sessao

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NovaSessaoScreen(onIniciarClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nova Sessão de Foco",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "Prepare seu ambiente para estudar sem distrações")

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Qual disciplina vai estudar hoje?")

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Vincular tarefa (opcional)")

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tempo de duração")

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onIniciarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sessão de Estudo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NovaSessaoScreenPreview() {
    NovaSessaoScreen()
}