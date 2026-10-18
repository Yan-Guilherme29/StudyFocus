package com.example.studyfocus.ui.tarefas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NovaTarefaScreen() {

    Box(

        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Nova Tarefa Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun NovaTarefaScreenPreview() {
    NovaTarefaScreen()
}