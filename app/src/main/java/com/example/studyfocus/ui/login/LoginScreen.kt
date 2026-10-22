package com.example.studyfocus.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyfocus.ui.theme.StudyFocusTheme

private val Indigo = Color(0xFF4F46E5)
private val Fundo = Color(0xFFF8F9FC)
private val Texto = Color(0xFF1E1E2E)
private val TextoSuave = Color(0xFF6B7280)
private val Borda = Color(0xFFE5E7EB)
private val Vermelho = Color(0xFFDC2626)

// Ícones desenhados a partir de um path SVG (estilo contorno, igual ao Figma).
// Assim não precisamos adicionar uma biblioteca de ícones no projeto.
private fun icone(path: String): ImageVector =
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).addPath(
        pathData = addPathNodes(path),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ).build()

private val IconeCapelo = icone(
    "M21.42 10.922a1 1 0 0 0-.019-1.838L12.83 5.18a2 2 0 0 0-1.66 0L2.6 9.08" +
            "a1 1 0 0 0 0 1.832l8.57 3.908a2 2 0 0 0 1.66 0z" +
            "M22 10v6M6 12.5V16a6 3 0 0 0 12 0v-3.5"
)
private val IconeOlho = icone(
    "M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" +
            "M12 9a3 3 0 1 0 0 6a3 3 0 1 0 0-6z"
)
private val IconeOlhoRiscado = icone(
    "M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94" +
            "M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19" +
            "m-6.72-1.07a3 3 0 1 1-4.24-4.24M1 1l22 22"
)

@Composable
fun LoginScreen(
    onEntrarClick: () -> Unit = {},
    onCriarContaClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var erro by remember { mutableStateOf<String?>(null) }

    // P1: só confere se os campos foram preenchidos (o login de verdade entra depois)
    fun entrar() {
        erro = when {
            email.isBlank() || senha.isBlank() -> "Preencha o email e a senha."
            !email.contains("@") -> "Digite um email válido."
            else -> null
        }
        if (erro == null) onEntrarClick()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(72.dp))

        // Logo + nome do app
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Indigo, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = IconeCapelo,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = "StudyFocus",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Texto
        )

        Spacer(Modifier.height(40.dp))

        Text(
            text = "Faça seu login",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Texto,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Email")
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                erro = null
            },
            placeholder = { Text("Digite seu email", color = TextoSuave) },
            singleLine = true,
            isError = erro != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Senha")
        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
                erro = null
            },
            placeholder = { Text("Digite sua senha", color = TextoSuave) },
            singleLine = true,
            isError = erro != null,
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                    Icon(
                        imageVector = if (senhaVisivel) IconeOlhoRiscado else IconeOlho,
                        contentDescription = if (senhaVisivel) "Ocultar senha" else "Mostrar senha",
                        tint = TextoSuave,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { entrar() }),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        // Mensagem de erro (só aparece se faltar algo)
        erro?.let {
            Spacer(Modifier.height(8.dp))
            Text(
                text = it,
                fontSize = 12.sp,
                color = Vermelho,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { entrar() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo)
        ) {
            Text(text = "Entrar", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(12.dp))

        // Link para o cadastro (tela fora da P1)
        Row(
            modifier = Modifier
                .clickable(onClick = onCriarContaClick)
                .padding(8.dp)
        ) {
            Text(text = "Ainda não tem conta? ", fontSize = 13.sp, color = TextoSuave)
            Text(
                text = "Crie uma agora",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Indigo
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun Rotulo(texto: String) {
    Text(
        text = texto,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextoSuave,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
}

@Composable
private fun camposCores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Indigo,
    unfocusedBorderColor = Borda,
    errorBorderColor = Vermelho,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    errorContainerColor = Color.White,
    focusedTextColor = Texto,
    unfocusedTextColor = Texto,
    errorTextColor = Texto,
    cursorColor = Indigo,
    errorCursorColor = Indigo
)

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    StudyFocusTheme {
        LoginScreen()
    }
}
