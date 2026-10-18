package com.example.studyfocus.ui.disciplinas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisciplinasScreen() {

    Box(

        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Disciplina Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun DisciplinaScreenPreview() {
    DisciplinasScreen()
}