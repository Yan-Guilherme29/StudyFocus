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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.studyfocus.ui.theme.StudyFocusTheme

// Ícones desenhados a partir de path SVG (sem material-icons-extended)
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
fun CadastroScreen(
    carregando: Boolean = false,
    erro: String? = null,
    onCriarContaClick: (nome: String, email: String, senha: String) -> Unit = { _, _, _ -> },
    onJaTenhoContaClick: () -> Unit = {}
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarVisivel by remember { mutableStateOf(false) }

    // Validação só visual (regras de negócio ficam com o Firebase)
    val emailInvalido = email.isNotBlank() && !email.contains("@")
    val senhasDiferentes = confirmar.isNotBlank() && senha != confirmar
    val podeCriar = nome.isNotBlank() &&
            email.isNotBlank() && !emailInvalido &&
            senha.isNotBlank() && confirmar.isNotBlank() &&
            !senhasDiferentes && !carregando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = IconeCapelo,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Crie sua conta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Comece a organizar seus estudos hoje",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        Rotulo("Nome completo")
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            placeholder = { Text("Digite seu nome") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = camposCores()
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Digite seu email") },
            singleLine = true,
            isError = emailInvalido,
            supportingText = if (emailInvalido) {
                { Text("Digite um email válido") }
            } else null,
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
        CampoSenha(
            valor = senha,
            onValorChange = { senha = it },
            placeholder = "Crie sua senha",
            visivel = senhaVisivel,
            onAlternarVisivel = { senhaVisivel = !senhaVisivel },
            imeAction = ImeAction.Next
        )

        Spacer(Modifier.height(16.dp))

        Rotulo("Confirmar senha")
        CampoSenha(
            valor = confirmar,
            onValorChange = { confirmar = it },
            placeholder = "Repita sua senha",
            visivel = confirmarVisivel,
            onAlternarVisivel = { confirmarVisivel = !confirmarVisivel },
            imeAction = ImeAction.Done,
            isError = senhasDiferentes,
            mensagemErro = if (senhasDiferentes) "As senhas não conferem" else null
        )

        if (erro != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = erro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onCriarContaClick(nome.trim(), email.trim(), senha) },
            enabled = podeCriar,
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
                Text(text = "Criar minha conta", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .clickable(onClick = onJaTenhoContaClick)
                .padding(8.dp)
        ) {
            Text(
                text = "Já tem uma conta? ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Faça login",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun CampoSenha(
    valor: String,
    onValorChange: (String) -> Unit,
    placeholder: String,
    visivel: Boolean,
    onAlternarVisivel: () -> Unit,
    imeAction: ImeAction,
    isError: Boolean = false,
    mensagemErro: String? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = isError,
        supportingText = if (mensagemErro != null) {
            { Text(mensagemErro) }
        } else null,
        visualTransformation = if (visivel) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onAlternarVisivel) {
                Icon(
                    imageVector = if (visivel) IconeOlhoRiscado else IconeOlho,
                    contentDescription = if (visivel) "Ocultar senha" else "Mostrar senha",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = camposCores()
    )
}

@Composable
private fun Rotulo(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
}

@Composable
private fun camposCores() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    errorContainerColor = MaterialTheme.colorScheme.surface
)

@Preview(showBackground = true)
@Composable
fun CadastroScreenPreview() {
    StudyFocusTheme {
        CadastroScreen()
    }
}