package com.example.studyfocus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyfocus.ui.disciplinas.DetalheDisciplinaScreen
import com.example.studyfocus.ui.disciplinas.DisciplinasScreen
import com.example.studyfocus.ui.home.HomeScreen
import com.example.studyfocus.ui.login.LoginScreen
import com.example.studyfocus.ui.revisao.RevisaoDiariaScreen
import com.example.studyfocus.ui.sessao.FocoAtivoScreen
import com.example.studyfocus.ui.sessao.NovaSessaoScreen
import com.example.studyfocus.ui.tarefas.NovaTarefaScreen
import com.example.studyfocus.ui.tarefas.TarefasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.LOGIN
    ) {
        composable(Rotas.LOGIN) { LoginScreen() }
        composable(Rotas.HOME) { HomeScreen() }
        composable(Rotas.DISCIPLINAS) { DisciplinasScreen() }
        composable(Rotas.DETALHE_DISCIPLINA) { DetalheDisciplinaScreen() }
        composable(Rotas.TAREFAS) { TarefasScreen() }
        composable(Rotas.NOVA_TAREFA) { NovaTarefaScreen() }
        composable(Rotas.NOVA_SESSAO) { NovaSessaoScreen() }
        composable(Rotas.FOCO_ATIVO) { FocoAtivoScreen() }
        composable(Rotas.REVISAO_DIARIA) { RevisaoDiariaScreen() }
    }
}