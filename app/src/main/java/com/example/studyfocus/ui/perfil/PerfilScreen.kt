package com.example.studyfocus.ui.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyfocus.ui.theme.StudyFocusTheme
import kotlin.math.roundToInt

// Dados falsos: só para o preview e como valor padrão dos parâmetros
private val nomeFalso = "Ana Beatriz Silva"
private val emailFalso = "ana.beatriz@email.com"
private val cursoFalso = "Ciência da Computação"
private val instituicaoFalsa = "Universidade Federal"
private val ligaFalsa = "Prata"
private val claFalso = "Compilados"

// Ícone de lua do "Modo foco": não existe no material-icons-core, então é desenhado por path SVG
private val IconeLua = ImageVector.Builder(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).addPath(
    pathData = addPathNodes("M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9z"),
    stroke = SolidColor(Color.Black),
    strokeLineWidth = 2f,
    strokeLineCap = StrokeCap.Round,
    strokeLineJoin = StrokeJoin.Round
).build()

// Ex.: "1 dia" e "12 dias"
private fun quantidade(numero: Int, singular: String, plural: String) =
    if (numero == 1) "$numero $singular" else "$numero $plural"

@Composable
fun PerfilScreen(
    nome: String = nomeFalso,
    email: String = emailFalso,
    curso: String = cursoFalso,
    instituicao: String = instituicaoFalsa,
    flashcardsRevisados: Int = 47,
    diasSeguidos: Int = 12,
    tarefasConcluidas: Int = 3,
    totalTarefas: Int = 5,
    liga: String? = ligaFalsa,
    pontos: Int = 420,
    temporada: Int = 3,
    cla: String = claFalso,
    posicaoRanking: Int = 3,
    lembreteDiario: Boolean = true,
    modoFoco: Boolean = false,
    carregando: Boolean = false,
    erro: String? = null,
    onEditarDadosClick: () -> Unit = {},
    onSegurancaClick: () -> Unit = {},
    onLembreteDiarioChange: (Boolean) -> Unit = {},
    onModoFocoChange: (Boolean) -> Unit = {},
    onSairClick: () -> Unit = {}
) {
    // Estado interno iniciado pelo parâmetro: com a chave, o switch se atualiza quando o dado chegar
    var lembrete by remember(lembreteDiario) { mutableStateOf(lembreteDiario) }
    var foco by remember(modoFoco) { mutableStateOf(modoFoco) }

    val progresso = if (totalTarefas > 0) tarefasConcluidas.toFloat() / totalTarefas else 0f
    val porcentagem = (progresso * 100).roundToInt()
    val cursoEInstituicao = listOf(curso, instituicao).filter { it.isNotBlank() }.joinToString(" • ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // Dados do usuário
        Text(
            text = nome,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (cursoEInstituicao.isNotEmpty()) {
            Text(
                text = cursoEInstituicao,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (carregando) {
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (erro != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = erro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(16.dp))

        // Resumo do desempenho
        CartaoPerfil {
            TituloCartao("Resumo do desempenho")
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Você revisou ${quantidade(flashcardsRevisados, "flashcard", "flashcards")} " +
                        "esta semana e manteve ${quantidade(diasSeguidos, "dia seguido", "dias seguidos")} " +
                        "de estudo. Continue assim para manter o ritmo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Etiqueta("$porcentagem% concluído")
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "$tarefasConcluidas de $totalTarefas tarefas da semana",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Liga: o card só aparece quando o usuário tem uma liga (liga = null esconde)
        if (liga != null) {
            Spacer(Modifier.height(12.dp))
            CartaoPerfil {
                TituloCartao("Sua evolução na liga")
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Numero(valor = liga, legenda = "Liga atual", modifier = Modifier.weight(1f))
                    Numero(
                        valor = "$pontos pts",
                        legenda = "Temporada $temporada",
                        modifier = Modifier.weight(1f)
                    )
                    Numero(
                        valor = quantidade(diasSeguidos, "dia", "dias"),
                        legenda = "Streak",
                        modifier = Modifier.weight(1f)
                    )
                }
                if (cla.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Clã $cla • ${posicaoRanking}º lugar no ranking",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        TituloSecao("Ações")
        LinhaAcao(
            icone = Icons.Outlined.Person,
            titulo = "Editar dados",
            descricao = "Atualize nome, email e instituição",
            onClick = onEditarDadosClick
        )
        Spacer(Modifier.height(8.dp))
        LinhaAcao(
            icone = Icons.Outlined.Lock,
            titulo = "Segurança",
            descricao = "Senha, autenticação e dispositivos",
            onClick = onSegurancaClick
        )

        TituloSecao("Preferências de estudo")
        LinhaPreferencia(
            icone = Icons.Outlined.Notifications,
            titulo = "Lembrete diário",
            descricao = "Receba lembretes para revisar conteúdo",
            ligado = lembrete,
            onMudar = {
                lembrete = it
                onLembreteDiarioChange(it)
            }
        )
        Spacer(Modifier.height(8.dp))
        LinhaPreferencia(
            icone = IconeLua,
            titulo = "Modo foco",
            descricao = "Reduz notificações durante sessões longas",
            ligado = foco,
            onMudar = {
                foco = it
                onModoFocoChange(it)
            }
        )

        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick = onSairClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Sair", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun CartaoPerfil(conteudo: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = bordaCartao()
    ) {
        Column(modifier = Modifier.padding(14.dp), content = conteudo)
    }
}

@Composable
private fun TituloCartao(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TituloSecao(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun Etiqueta(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
private fun Numero(valor: String, legenda: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = valor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = legenda,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Ícone num quadradinho com a cor principal bem clara no fundo
@Composable
private fun IconeQuadrado(icone: ImageVector) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun TextosDaLinha(titulo: String, descricao: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = descricao,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LinhaAcao(icone: ImageVector, titulo: String, descricao: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = bordaCartao()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconeQuadrado(icone)
            Spacer(Modifier.width(12.dp))
            TextosDaLinha(titulo, descricao, Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LinhaPreferencia(
    icone: ImageVector,
    titulo: String,
    descricao: String,
    ligado: Boolean,
    onMudar: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = coresCartao(),
        border = bordaCartao()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconeQuadrado(icone)
            Spacer(Modifier.width(12.dp))
            TextosDaLinha(titulo, descricao, Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            Switch(checked = ligado, onCheckedChange = onMudar)
        }
    }
}

@Composable
private fun coresCartao() = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
)

@Composable
private fun bordaCartao() = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    StudyFocusTheme {
        PerfilScreen()
    }
}
