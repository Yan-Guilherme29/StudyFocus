package com.example.studyfocus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studyfocus.ui.disciplinas.DetalheDisciplinaScreen
import com.example.studyfocus.ui.disciplinas.DisciplinasScreen
import com.example.studyfocus.ui.disciplinas.EditarDisciplinaScreen
import com.example.studyfocus.ui.home.HomeScreen
import com.example.studyfocus.ui.login.CadastroScreen
import com.example.studyfocus.ui.login.LoginScreen
import com.example.studyfocus.ui.revisao.RevisaoDiariaScreen
import com.example.studyfocus.ui.sessao.FocoAtivoScreen
import com.example.studyfocus.ui.sessao.NovaSessaoScreen
import com.example.studyfocus.ui.tarefas.EditarTarefaScreen
import com.example.studyfocus.ui.tarefas.NovaTarefaScreen
import com.example.studyfocus.ui.tarefas.TarefasScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyfocus.ui.login.AuthViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination?.route

    Scaffold(
        bottomBar = {
            if (rotaAtual != Rotas.LOGIN && rotaAtual != Rotas.CADASTRO) {
                BarraInferior(
                    rotaSelecionada = abaDaRota(rotaAtual),
                    onItemClick = { rota ->
                        navController.navigate(rota) {
                            popUpTo(Rotas.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rotas.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rotas.LOGIN) {
                val authViewModel: AuthViewModel = viewModel()

                val estado by authViewModel.estado.collectAsState()

                LaunchedEffect(estado.autenticado) {
                    if (estado.autenticado) {
                        navController.navigate(Rotas.HOME) {
                            popUpTo(Rotas.LOGIN) { inclusive = true }
                        }
                    }
                }

                LoginScreen(
                    carregando = estado.carregando,
                    erro = estado.erro,
                    onEntrarClick = { email, senha -> authViewModel.entrar(email, senha) },
                    onCriarContaClick = { navController.navigate(Rotas.CADASTRO) }
                )
            }

            composable(Rotas.CADASTRO) {
                val authViewModel: AuthViewModel = viewModel()
                val estado by authViewModel.estado.collectAsState()

                LaunchedEffect(estado.autenticado) {
                    if (estado.autenticado) {
                        navController.navigate(Rotas.HOME) {
                            popUpTo(Rotas.LOGIN) { inclusive = true }
                        }
                    }
                }

                CadastroScreen(
                    carregando = estado.carregando,
                    erro = estado.erro,
                    // O Cadastro entrega 3 valores: nome, e-mail e senha.
                    onCriarContaClick = { nome, email, senha -> authViewModel.cadastrar(nome, email, senha) },
                    onJaTenhoContaClick = { navController.popBackStack() }
                )
            }

            composable(Rotas.HOME) {
                HomeScreen(
                    onTarefasClick = { navController.navigate(Rotas.TAREFAS) },
                    onEstatisticasClick = { navController.navigate(Rotas.ESTATISTICAS) },
                    onRevisarClick = { navController.navigate(Rotas.REVISAO_DIARIA) },
                    onDisciplinaClick = { _ -> navController.navigate(Rotas.DETALHE_DISCIPLINA) }
                )
            }

            composable(Rotas.DISCIPLINAS) {
                DisciplinasScreen(
                    onDisciplinaClick = {
                        navController.navigate(Rotas.DETALHE_DISCIPLINA)
                    }
                )
            }

            composable(Rotas.DETALHE_DISCIPLINA) {
                DetalheDisciplinaScreen(
                    onVoltarClick = { navController.popBackStack() },
                    onVerTodasTarefasClick = { navController.navigate(Rotas.TAREFAS) },
                    onRevisarClick = { navController.navigate(Rotas.REVISAO_DIARIA) },
                    onEditarClick = { navController.navigate(Rotas.EDITAR_DISCIPLINA) }
                )
            }

            composable(Rotas.EDITAR_DISCIPLINA) {
                EditarDisciplinaScreen(
                    onVoltarClick = { navController.popBackStack() },
                    onSalvarClick = { navController.popBackStack() },
                    onExcluirClick = { navController.popBackStack() }
                )
            }

            composable(Rotas.TAREFAS) {
                TarefasScreen(
                    onNovaTarefaClick = { navController.navigate(Rotas.NOVA_TAREFA) },
                    onTarefaClick = { navController.navigate(Rotas.EDITAR_TAREFA) }
                )
            }

            composable(Rotas.EDITAR_TAREFA) {
                EditarTarefaScreen(
                    onVoltarClick = { navController.popBackStack() },
                    onSalvarClick = { navController.popBackStack() },
                    onExcluirClick = { navController.popBackStack() }
                )
            }

            composable(Rotas.NOVA_TAREFA) {
                NovaTarefaScreen(
                    onVoltarClick = { navController.popBackStack() },
                    onCriarClick = { navController.popBackStack() }
                )
            }

            composable(Rotas.NOVA_SESSAO) {
                NovaSessaoScreen(
                    onIniciarClick = { navController.navigate(Rotas.FOCO_ATIVO) }
                )
            }

            composable(Rotas.FOCO_ATIVO) {
                FocoAtivoScreen(
                    onConcluirClick = { navController.popBackStack(Rotas.HOME, false) }
                )
            }

            composable(Rotas.REVISAO_DIARIA) {
                RevisaoDiariaScreen(
                    onVoltarClick = { navController.popBackStack() }
                )
            }

            composable(Rotas.ESTATISTICAS) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Estatísticas (em breve)")
                }
            }
        }
    }
}

private fun abaDaRota(rota: String?): String? = when (rota) {
    Rotas.DETALHE_DISCIPLINA, Rotas.EDITAR_DISCIPLINA, Rotas.REVISAO_DIARIA -> Rotas.DISCIPLINAS
    Rotas.NOVA_TAREFA -> Rotas.TAREFAS
    Rotas.EDITAR_TAREFA -> Rotas.TAREFAS
    Rotas.FOCO_ATIVO -> Rotas.NOVA_SESSAO
    else -> rota
}