package com.example.funlounge
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



//A classe MainActivity herda de AppCompatActivity, que fornece funcionalidades para compatibilidade
// com versões mais antigas do Android.
class MainActivityJogo : AppCompatActivity() {



    //Declaração de Variáveis


    //Esta variável é um "controle de segurança" para garantir que a contagem de partidas só
    // seja atualizada uma vez por jogo, quando há um vencedor ou um empate.
    private var gameCountUpdated = false

    //Lista de combinações vencedoras do jogo (linhas, colunas e diagonais)
    private val combinationsList: MutableList<IntArray> = ArrayList()

    // Define de quem é a vez (1 = jogador 1, 2 = jogador 2)
    private var playerTurn: Int = 0

    //A variável lastWinner está a ser usada para armazenar o último jogador que venceu a partida.
    // Ela é atualizada sempre que há um vencedor e é usada para determinar quem começará o próximo jogo.
    private var lastWinner: Int = 0

    //Conta o número de casas preenchidas no tabuleiro
    //Este contador ajuda a detectar empates (quando todas as casas são preenchidas).
    private var totalSelectedBoxes: Int = 0

    // Array que armazena o estado de cada posição do tabuleiro (0 = vazia, 1 = jogador 1, 2 = jogador 2).
    private var boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

    //Indica se o jogador esta a jogar contra o bot
    private var playingAgainstBot = false
    //Símbolo do jogador (X ou O)
    private var playerSymbol = "X"
    //Símbolo do bot (se o jogador for X, o bot será O)
    private var botSymbol = "O"

    // Declaração dos Elementos da Interface Gráfica
    private lateinit var playerOneLayout: LinearLayout
    private lateinit var playerTwoLayout: LinearLayout
    private lateinit var playerOneName: TextView
    private lateinit var playerTwoName: TextView
    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var image3: ImageView
    private lateinit var image4: ImageView
    private lateinit var image5: ImageView
    private lateinit var image6: ImageView
    private lateinit var image7: ImageView
    private lateinit var image8: ImageView
    private lateinit var image9: ImageView

    //boardLineDrawer é uma variável do tipo BoardLineDrawer
    //O uso de lateinit significa que ela será inicializada posteriormente, antes de ser utilizada.
    private lateinit var boardLineDrawer: BoardLineDrawer



    // Método onCreate:Chamado quando a tela é criada. Aqui são configurados os elementos da interface e
    // inicializadas as variáveis.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Definir o layout da interface com base no arquivo XML activity_main.xml
        setContentView(R.layout.activity_mainmenu)

        //Recebe os dados da Activity anterior (ChooseSymbolActivity)
        // símbolo do jogador
        //se nada for recebido, define "X" como padrão
        playerSymbol = intent.getStringExtra("playerSymbol") ?: "X"
        //Modo de jogo (contra bot ou amigo)
        playingAgainstBot = intent.getBooleanExtra("playingAgainstBot", false)
        // Se o jogador escolheu "X", o bot será "O"
        //Se o jogador escolheu "O", o bot será "X"
        botSymbol = if (playerSymbol == "X") "O" else "X"


        //Se o elemento jogador1nome estiver presente no layout:"(R.layout.activity_main)", ele será encontrado e
        // referenciado pela variável playerOneName
        playerOneName = findViewById(R.id.jogador1nome)
        playerTwoName = findViewById(R.id.jogador2nome)

       //Estas duas linhas procuram os elementos da interface que têm o ID jogador1layout e jogador2layout
        // no arquivo XML activity_main.xml, e associam esses elementos às variáveis playerOneLayout e
        // playerTwoLayout, respectivamente.
        playerOneLayout = findViewById(R.id.jogador1layout)
        playerTwoLayout = findViewById(R.id.jogador2layout)

        // Verifica se o jogo está no modo contra o bot.
        //Se sim entramos dentro do if
        if (playingAgainstBot) {
            //Busca o nome digitado pelo jogador na tela anterior
            //"?: "Jogador 1"--> significa que, se o nome não for enviado ou estiver vazio, o padrão será "Jogador 1".
            playerOneName.text = intent.getStringExtra("playerName") ?: "Jogador 1"
            //Define o nome do segundo jogador como "Bot"
            playerTwoName.text = "Bot"
            //Se não for um jogo contra o bot entramos no else que significa que o jogador escolheu jogar contra um amigo
        } else {
            playerOneName.text = intent.getStringExtra("jogador1") ?: "Jogador 1"
            playerTwoName.text = intent.getStringExtra("jogador2") ?: "Jogador 2"
        }
        //findViewById(R.id.imageJogador1): Encontra o ImageView do jogador 1 na interface (activity_main.xml).
        //findViewById(R.id.imageJogador2): Encontra o ImageView do jogador 2 (ou Bot).
        //Objetivo: Identificar onde a imagem do símbolo (X ou O) será exibida para cada jogador.
        val playerOneSymbolView: ImageView = findViewById(R.id.imageJogador1)
        val playerTwoSymbolView: ImageView = findViewById(R.id.imageJogador2)
        // A função getImageResourceForSymbol(symbol: String) retorna a imagem correspondente para "X" ou "O".
        //setImageResource() define a imagem correta no ImageView
        playerOneSymbolView.setImageResource(getImageResourceForSymbol(playerSymbol))
        playerTwoSymbolView.setImageResource(getImageResourceForSymbol(botSymbol))

       // Estas linhas associam as variáveis image1 a image9 aos elementos ImageView no arquivo de layout XML
        // (activity_main.xml). Cada uma dessas variáveis está ligada a uma posição específica no tabuleiro do
        // jogo
        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)
        image3 = findViewById(R.id.image3)
        image4 = findViewById(R.id.image4)
        image5 = findViewById(R.id.image5)
        image6 = findViewById(R.id.image6)
        image7 = findViewById(R.id.image7)
        image8 = findViewById(R.id.image8)
        image9 = findViewById(R.id.image9)

        //Se o jogador escolheu jogar contra o bot (playingAgainstBot == true)
        if (playingAgainstBot) {
            //  Escolhe aleatoriamente se o jogador (1) ou o bot (2) começa.
            playerTurn = (1..2).random()
            //Se o bot foi escolhido para começar (playerTurn == 2), ele não joga imediatamente.
            // Para tornar o jogo mais natural, um atraso de 500ms (0.5 segundos) é adicionado antes de chamar botMakeMove().
            //Isso faz com que o bot pareça "pensar" antes de jogar.
            if (playerTurn == 2) {
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    botMakeMove()
                }, 500)
            }
        }
        //Quando é iniciado o primeiro jogo de todos é
        //escolhido de forma aleatória o primeiro jogador
        //a jogar
        if (playerTurn == 0) {
            playerTurn = (1..2).random()
        }


        // Definição das Combinações Vencedoras
        //linhas
        combinationsList.add(intArrayOf(0, 1, 2))
        combinationsList.add(intArrayOf(3, 4, 5))
        combinationsList.add(intArrayOf(6, 7, 8))

        //colunas
        combinationsList.add(intArrayOf(0, 3, 6))
        combinationsList.add(intArrayOf(1, 4, 7))
        combinationsList.add(intArrayOf(2, 5, 8))

        //diagonais
        combinationsList.add(intArrayOf(0, 4, 8))
        combinationsList.add(intArrayOf(2, 4, 6))

        //o objeto boardLineDrawer é associado ao elemento correspondente no layout XML (R.id.boardLineDrawer)
        boardLineDrawer = findViewById(R.id.boardLineDrawer)




        //Obter os nomes dos jogadores passados do layout "activity_adicionar_jogadores.xml" e exibi-los na
        // interface Main
        val getPlayerOneName: String? = intent.getStringExtra("jogador1")
        val getPlayerTwoName: String? = intent.getStringExtra("jogador2")

       //O texto exibido em playerOneName será o valor armazenado em getPlayerOneName,
        // ou seja, o nome do jogador 1 que foi passado da tela anterior
        playerOneName.text = getPlayerOneName
        playerTwoName.text = getPlayerTwoName

        // Escolher aleatoriamente quem começa o primeiro jogo
        if (playerTurn == 0) {
            playerTurn = (1..2).random()
        }

        //Dar "enfase visualmente" identificando logo no inicio o 1ºjogador que ira começar a jogar no 1ºjogo
        updateTurnUI()

        //Quando o jogador clica numa "box" é verificada se está disponível com isBoxSelectable.
        //Se estiver disponível chama-se o performAction para registrar a jogada.
        image1.setOnClickListener {
            if (isBoxSelectable(1)) {
                performAction(image1, 0)
            }
        }

        image2.setOnClickListener {
            if (isBoxSelectable(2)) {
                performAction(image2, 1)
            }
        }

        image3.setOnClickListener {
            if (isBoxSelectable(3)) {
                performAction(image3, 2)
            }
        }

        image4.setOnClickListener {
            if (isBoxSelectable(4)) {
                performAction(image4, 3)
            }
        }

        image5.setOnClickListener {
            if (isBoxSelectable(5)) {
                performAction(image5, 4)
            }
        }

        image6.setOnClickListener {
            if (isBoxSelectable(6)) {
                performAction(image6, 5)
            }
        }

        image7.setOnClickListener {
            if (isBoxSelectable(7)) {
                performAction(image7, 6)
            }
        }

        image8.setOnClickListener {
            if (isBoxSelectable(8)) {
                performAction(image8, 7)
            }
        }

        image9.setOnClickListener {
            if (isBoxSelectable(9)) {
                performAction(image9, 8)
            }
        }

    }

    //A função analisa a variável playerTurn,que indica se é a vez do Jogador 1 (valor 1) ou do Jogador 2(valor 2)
    //Se for a vez do jogador 1 (playerTurn == 1):
    //Define uma borda branca no layout do jogador 1 (R.drawable.white_border) para indicar que é sua vez.
    //O layout do jogador 2 recebe uma aparência diferente para mostrar que ele está a aguardar.

    //Se for a vez do jogador 2 (playerTurn == 2):
    //O layout do jogador 2 recebe a borda branca destacando que é a vez dele.
    //O layout do jogador 1 muda para indicar que ele não a está a jogar no momento.
    private fun updateTurnUI() {
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.white_border)
            playerTwoLayout.setBackgroundResource(R.drawable.box_to_play)
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.white_border)
            playerOneLayout.setBackgroundResource(R.drawable.box_to_play)
        }
    }

    //Esta função retorna a imagem correspondente para o símbolo "X" ou "O".
    private fun getImageResourceForSymbol(symbol: String): Int {
        //Se o símbolo for "X" retorna R.drawable.cruz (a imagem de "X").
        //Se o símbolo for "O" retorna R.drawable.bola (a imagem de "O").
        return if (symbol == "X") R.drawable.cruz else R.drawable.bola
    }


    //Este método drawWinningLine é responsável por desenhar uma linha visualmente sobre a combinação vencedora
    // no tabuleiro do jogo
    private fun drawWinningLine(combination: IntArray) {

        //O array imageViews contém todas as casas do tabuleiro
        val imageViews = arrayOf(image1, image2, image3, image4, image5, image6, image7, image8, image9)

        imageViews[combination[0]].post {

            //combination[0] e combination[2] representam o início e o fim de uma linha vencedora
            val startView = imageViews[combination[0]]
            val endView = imageViews[combination[2]]

            //Cria um array startLocation para armazenar a posição da primeira casa vencedora.
            //getLocationInWindow(startLocation) preenche o array com as coordenadas (X, Y) da casa na tela.
            val startLocation = IntArray(2)
            startView.getLocationInWindow(startLocation)

            //Cria um array endLocation para armazenar a posição da última casa vencedora.
            //Obtém a posição da casa na tela.
            val endLocation = IntArray(2)
            endView.getLocationInWindow(endLocation)

            //Cria um array boardLocation para armazenar a posição do tabuleiro.
            //Obtém as coordenadas do tabuleiro na tela.
            val boardLocation = IntArray(2)
            boardLineDrawer.getLocationInWindow(boardLocation)

            //Calcular a posição relativa dentro do tabuleiro, subtraindo a posição do tabuleiro das posições das
            // casas
            //Assim, sabemos que a primeira casa está na posição (150, 200) dentro do tabuleiro.
            //"+ startView.width / 2" desloca o ponto para o centro da casa
            // Ajuste para garantir que a linha vai até os cantos
            val startX = startLocation[0] - boardLocation[0]
            val startY = startLocation[1] - boardLocation[1]
            val endX = endLocation[0] - boardLocation[0] + endView.width
            val endY = endLocation[1] - boardLocation[1] + endView.height

            // Verificar se a linha é diagonal, vertical ou horizontal
            val isDiagonalLTR = (combination.contentEquals(intArrayOf(0, 4, 8)))  // Diagonal Esquerda para Direita
            val isDiagonalRTL = (combination.contentEquals(intArrayOf(2, 4, 6)))  // Diagonal Direita para Esquerda

            //Se todas as posições da combinação vencedora tiverem o mesmo resto quando divididas por 3,
            // significa que estão na mesma coluna.
            val isVertical = combination[0] % 3 == combination[1] % 3

            // Posições na mesma linha sempre terão o mesmo quociente quando divididas por 3.
            val isHorizontal = combination[0] / 3 == combination[1] / 3

            //offsetX e offsetY garante que a linha seja desenhada corretamente no meio das células quando
            // for horizontal ou vertical.
            val offsetX = if (isVertical) startView.width / 2 else 0
            val offsetY = if (isHorizontal) startView.height / 2 else 0



            val adjustedStartX = if (isDiagonalLTR) startX //Se for diagonal da esquerda para a
                                                          // direita (isDiagonalLTR), startX permanece o mesmo

                                 else if (isDiagonalRTL) startX + startView.width //Se for diagonal da direita para a esquerda
                          // (isDiagonalRTL)  startX precisa começar mais à direita, então soma-se startView.width.

                                 else startX + offsetX //Para linhas horizontais ou verticais,
                                                     // adiciona-se o offsetX para ajustar o início corretamente.
            val adjustedStartY = startY + offsetY //isto garante que a linha comece no centro das células, caso necessário

            val adjustedEndX = if (isDiagonalLTR) endX
                               else if (isDiagonalRTL) endX - endView.width
                               else endX - offsetX
            val adjustedEndY = endY - offsetY

            // Tornar visível a linha
            boardLineDrawer.visibility = View.VISIBLE
            //Chama a função que realmente desenha a linha, passando as coordenadas ajustadas.
            boardLineDrawer.drawWinningLine(
                adjustedStartX.toFloat(),
                adjustedStartY.toFloat(),
                adjustedEndX.toFloat(),
                adjustedEndY.toFloat()
            )
        }
    }

    //Esta função calcula a melhor jogada para o bot usando o algoritmo Minimax.
    //depth: Int →   quantos movimentos à frente o bot está a analisar.
    //isMaximizing: Boolean → Define se é a vez do bot (true) ou do jogador (false)
    // Se false, minimiza (simula a jogada do oponente).
    private fun minimax(board: IntArray, depth: Int, isMaximizing: Boolean): Int {
        //Chama getWinner(board) para verificar se o jogo terminou.
        val winner = getWinner(board)
        //Se o jogo acabou, é retornada uma pontuação
        //Se o bot vencer (winner == 2), retorna 10 - depth.
        //Se depth for pequeno (exemplo, 1 ou 2), significa que o bot venceu rapidamente, então a pontuação será alta (exemplo: 10 - 2 = 8).
        //Se depth for grande (exemplo, 8 ou 9), significa que o bot levou muitas jogadas para vencer,
        // então a pontuação será menor (exemplo: 10 - 9 = 1).
        if (winner == 2) return 10 - depth // Vitória do Bot
        // Se o jogador vencer (winner == 1), retorna depth - 10.
        //Se depth for pequeno (exemplo, 1 ou 2), significa que o jogador venceu rapidamente, então a pontuação
        // será muito baixa (exemplo: 2 - 10 = -8).
        //Se depth for grande (exemplo, 8 ou 9), significa que o jogador levou muitas jogadas para vencer,
        // então a penalização será menor (exemplo: 9 - 10 = -1).
        if (winner == 1) return depth - 10 // Vitória do Jogador
        //Se o tabuleiro estiver cheio (!board.contains(0)), retorna 0 (empate)
        if (!board.contains(0)) return 0   // Empate
        //isMaximizing == true significa que é a vez do bot jogar.
        //O bot quer maximizar a sua pontuação, então ele irá escolher a jogada com o maior valor possível.
        if (isMaximizing) {
            // Define bestScore como Int.MIN_VALUE, ou seja, o menor valor possível em Kotlin.
            // Isso garante que qualquer pontuação calculada será maior e substituirá esse valor inicial
            var bestScore = Int.MIN_VALUE
            //Percorre todas as posições do tabuleiro (board é um IntArray com 9 posições, representando as casas do jogo).
            //Cada índice (i) representa uma casa no tabuleiro.
            for (i in board.indices) {
                if (board[i] == 0) {// Se a casa estiver vazia
                    // O bot (representado por 2) preenche essa casa temporariamente.
                    // Isso simula como o jogo ficaria se o bot escolhesse essa jogada.
                    board[i] = 2
                    // Chama minimax() recursivamente para simular a jogada do jogador (isMaximizing = false).
                    //Aumenta depth + 1, indicando que o bot está a olhar um movimento mais adiante no futuro.
                    //Agora, a função minimax() tentará minimizar a pontuação, pois será a vez do jogador humano
                    val score = minimax(board, depth + 1, false)
                    //O bot desfaz a jogada de teste para não alterar o tabuleiro real.
                    //Isso permite testar outras jogadas possíveis sem modificar o estado do jogo.
                    board[i] = 0
                    //Compara score (pontuação dessa jogada simulada) com bestScore.
                    //Se score for maior, bestScore recebe esse valor.
                    //Isso garante que o bot escolha a jogada que gera a maior pontuação possível.
                    //O maxOf(a, b) é uma função embutida do Kotlin (função nativa) que retorna o maior valor entre dois números
                    bestScore = maxOf(bestScore, score)
                }
            }
            return bestScore
        }
        // É executado quando é a vez do jogador humano (isMaximizing == false).
        else {
            // Define bestScore como Int.MAX_VALUE, ou seja, o maior valor possível em Kotlin.
            // Isso garante que qualquer pontuação calculada será menor e substituirá esse valor inicial.
            // Como o jogador quer minimizar a pontuação do bot, o bot precisa encontrar a menor pontuação possível.
            var bestScore = Int.MAX_VALUE
            //Percorre todas as posições do tabuleiro (board).
            for (i in board.indices) {
                if (board[i] == 0) {
                    // Simula o jogador a fazer uma jogada naquela casa (1 representa o jogador humano).
                    //O bot agora vai prever o que acontecerá se o jogador escolher essa jogada.
                    board[i] = 1
                    // Chama minimax() recursivamente para simular a próxima jogada do bot (isMaximizing = true).
                    // Aumenta depth + 1, indicando que o bot está a olhar um movimento mais adiante no futuro.
                    //Agora, a função minimax() tentará maximizar a pontuação para o bot
                    val score = minimax(board, depth + 1, true)
                    // Desfaz a jogada de teste para não alterar o tabuleiro real.
                    // Isso permite testar outras jogadas possíveis sem modificar o estado do jogo.
                    board[i] = 0
                    //Compara score (pontuação dessa jogada simulada) com bestScore.
                    //Se score for menor, bestScore recebe esse valor.
                    //Isso garante que o jogador escolha a jogada que gera a menor pontuação para o bot.
                    bestScore = minOf(bestScore, score)
                }
            }
            //Depois de testar todas as jogadas possíveis, retorna a menor pontuação possível para o jogador.
            // O bot usará essa informação para evitar jogadas que levem a derrotas.
            return bestScore
        }
    }

    //Esta função verifica se alguém venceu o jogo ou se ainda há jogadas possíveis.
    // Ela recebe um tabuleiro (board: IntArray) e retorna um número indicando o estado do jogo:
    //1->	O Jogador 1 venceu
    //2->	O Bot (Jogador 2) venceu
    //0->	O jogo ainda não terminou (há casas vazias)
    //-1->	O jogo terminou em empate (todas as casas foram preenchidas e ninguém venceu)
    //O bot usa esta função no Minimax para	verifica quem venceu num tabuleiro simulado
    private fun getWinner(board: IntArray): Int {
        for (combination in combinationsList) {
            if (board[combination[0]] != 0 &&
                board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]]) {
                //Se for encontrada uma combinação vencedora Retorna 1 (Jogador venceu) ou 2 (Bot venceu)
                return board[combination[0]]
            }
        }
        //Se board.contains(0) == true, significa que o jogo ainda não terminou, então retorna 0.
        //Se board.contains(0) == false, significa que todas as casas estão preenchidas e ninguém venceu,
        // então retorna -1 (empate).
        return if (board.contains(0)) 0 else -1
    }
    //Esta função decide qual jogada o bot fará.
    //Pode jogar aleatoriamente (30% das vezes) ou usar o algoritmo Minimax (70% das vezes).
    private fun botMakeMove() {
        //Este código serve para encontrar quais as casas do tabuleiro que estão vazias.
        //Ele cria uma lista com os índices (index) das casas que ainda não foram ocupadas (value == 0).
        val emptyBoxes = boxPositions.toList().mapIndexedNotNull  { index, value ->
            if (value == 0) index else null
        }
        //  Modo aleatório para tornar o bot "humano"
        //"(1..100).random()" gera um número aleatório entre 1 e 100.
        val difficultyFactor = (1..100).random()
        //Isto faz com que o bot jogue aleatoriamente 30% das vezes, parecendo mais "humano"
        //Se o número for menor ou igual a 30, o bot joga aleatoriamente numa casa vazia (emptyBoxes.random()).
        if (difficultyFactor <= 30) {
            val botMove = emptyBoxes.random()
            makeMove(botMove) //executar a jogada aleatória
            return
        }
        //  Modo estratégico
        var bestMove = -1 //Nenhuma jogada escolhida ainda
        var bestScore = Int.MIN_VALUE //O bot começa com a menor pontuação possível.
        //O bot vai tentar encontrar a jogada com a maior pontuação.
        //Percorre todas as casas vazias (emptyBoxes) e testa cada jogada possível.
        for (i in emptyBoxes) {
            //O bot (2) faz uma jogada de teste na posição i.
            boxPositions[i] = 2
            //Chama minimax() para avaliar a jogada testada.
            // boxPositions → O estado atual do tabuleiro.
            //0 → Profundidade inicial da busca (primeira jogada do bot).
            //false → O próximo movimento será do jogador humano, então agora ele tentará minimizar a pontuação.
            val score = minimax(boxPositions, 0, false)
            //O bot remove sua jogada de teste, restaurando o estado original do tabuleiro.
            //Isso permite testar outras jogadas sem modificar o jogo real.
            boxPositions[i] = 0
            //Se score for maior que bestScore, significa que essa jogada é melhor.
            //Atualiza bestScore e bestMove para lembrar a melhor jogada encontrada até agora.
            if (score > bestScore) {
                bestScore = score
                bestMove = i
            }
        }
        //Se bestMove não for -1, significa que o bot encontrou a melhor jogada possível.
        //makeMove(bestMove) executa essa jogada no jogo real.
        if (bestMove != -1) {
            makeMove(bestMove)
        }
    }
    //Esta função executa a jogada do bot e atualiza a interface gráfica.
    private fun makeMove(position: Int) {
        //Recebe uma posição (position: Int), que indica onde o bot quer jogar.
        val botImageView = when (position) {
            0 -> image1
            1 -> image2
            2 -> image3
            3 -> image4
            4 -> image5
            5 -> image6
            6 -> image7
            7 -> image8
            8 -> image9
            else -> null
        }
        //let {} é uma função do Kotlin que só executa o bloco de código se o objeto não for null.
        //botImageView pode ser null se position não corresponder a nenhuma posição válida no tabuleiro.
        // it dentro do bloco let {} representa botImageView.
        //botImageView = image7  // (porque position 6 mapeia para image7)
        //image7?.let {
        //  performAction(it, 6)
        //}
        //Como image7 NÃO é null, a função performAction(image7, 6) é chamada.
        botImageView?.let {
            //EsTa função registra a jogada do bot e atualiza a interface do jogo
            performAction(it, position)
        }
    }



    //O método performAction é chamado quando o jogador seleciona uma casa/"box" no tabuleiro.
    // O método atualiza o estado do jogo com base na jogada, altera a interface visual e verifica se
    // houve um vencedor ou empate.
    private fun performAction(imageView: ImageView, selectedBoxPosition: Int) {

        //Atualizar o array boxPositions para marcar a posição clicada com o número do jogador atual (1 ou 2)
        //Se selectedBoxPosition = 4 e playerTurn = 1 (Jogador 1), então boxPositions[4] = 1,
        // indicando que o Jogador 1 ocupou a posição 4 no tabuleiro.
        boxPositions[selectedBoxPosition] = playerTurn

        //Incrementar o contador de casas ocupadas no tabuleiro.
        //Serve para verificar posteriormente se todas as casas foram preenchidas (empate)
        totalSelectedBoxes++

        //Isto permite que o nome do jogador que fez a jogada seja armazenado na variável playerName,
        // para que se possa usá-lo posteriormente, por exemplo, para exibir mensagens como "Jogador X ganhou!"
        // quando um vencedor for encontrado.
        val playerName =
            if (playerTurn == 1) playerOneName.text.toString() else playerTwoName.text.toString()

        //Este código escolhe a imagem (X ou O) que deve ser exibida no tabuleiro,
        // dependendo de quem está a jogar no momento (playerTurn).
        val imageRes = if (playerTurn == 1) {
            // Chama a função getImageResourceForSymbol() para obter a imagem correta pro jogador humano do jogador humano
            getImageResourceForSymbol(playerSymbol)
            // Se playerTurn for 2, significa que é a vez do bot.
            // Obtém a imagem correspondente ao símbolo do bot (botSymbol).
            // Se botSymbol == "X", retorna R.drawable.cruz.
            // Se botSymbol == "O", retorna R.drawable.bola.
        } else {
            getImageResourceForSymbol(botSymbol)
        }

        //Atualiza a interface alterando a imagem da casa selecionada para a correspondente ao jogador atual(X ou O)
        imageView.setImageResource(imageRes)

        //O método checkPlayerWin() serve para verificar se o jogador atual completou uma sequência vencedora.
        //Se houver uma vitória:
        //Exibe-se uma mensagem com o nome do jogador usando a classe WinDialog.
        //O diálogo de vitória impede que o utilizar continue a jogar (setCancelable(false))
        if (checkPlayerWin()) {

            if (!gameCountUpdated) {
                incrementGameCount(this)
                gameCountUpdated = true
            }

            // Armazenar quem ganhou para começar a próxima partida
            lastWinner = playerTurn


            val winDialog = WinDialog(this, "$playerName ganhou o jogo!", this)

            // Configurar a posição do diálogo para ficar numa zona que dê mais "conforto" aos jogadores
            val window = winDialog.window
            window?.setGravity(android.view.Gravity.TOP)
            val layoutParams = window?.attributes
            layoutParams?.y = 600
            window?.attributes = layoutParams

            //setCancelable(false) configura o diálogo (winDialog) para não ser cancelável.
            //Isso significa que, uma vez que o diálogo de vitória ou empate seja exibido,
            // o jogador não poderá fechá-lo ou sair dele tocando fora do diálogo
            // (em áreas fora do próprio diálogo)
            winDialog.setCancelable(false)

            // Exibir o diálogo na tela.
            winDialog.show()

            //Caso todas as 9 casas tenham sido preenchidas sem um vencedor, é declarado um empate.
            //Um diálogo é exibido informando que o jogo terminou empatado.
        } else if (totalSelectedBoxes == 9) {

            //"if (!gameCountUpdated)" é a mesma coisa que "if (gameCountUpdated == false)"
            //o if só entra no bloco de código se gameCountUpdated for false
            // (o que indica que a contagem de jogos ainda não foi incrementada).
            if (!gameCountUpdated) {
                incrementGameCount(this)
                //Após a contagem de jogos ser incrementada, a variável gameCountUpdated é marcada como true:
                //Isso garante que, após o incremento, o código não execute novamente a função incrementGameCount
                //evitando assim que a contagem de jogos seja incrementada mais de uma vez
                gameCountUpdated = true
            }

            lastWinner = 0  // Reset para 0 para garantir escolha aleatória no caso de empate


            val winDialog = WinDialog(this, "Empate!", this)


            val window = winDialog.window
            window?.setGravity(android.view.Gravity.TOP)
            val layoutParams = window?.attributes
            layoutParams?.y = 600
            window?.attributes = layoutParams

            winDialog.setCancelable(false)
            winDialog.show()

            return
        }

        //Este trecho de código gerencia a alternância de turnos entre o jogador e o bot
        //Se estiver a jogar contra o bot (playingAgainstBot == true)o código garante que o turno seja alternado corretamente
        if (playingAgainstBot) {
            //Se for a vez do jogador humano (playerTurn == 1)
            //playerTurn == 1, significa que o jogador humano acabou de fazer uma jogada.
            if (playerTurn == 1) {
                // a função changePlayerTurn(2) atualiza a interface para indicar que agora é a vez do bot.
                changePlayerTurn(2)
                // Aqui é usado um Handler para adicionar um atraso de 500ms antes do bot jogar.
                //Isso dá tempo para a interface atualizar antes do bot fazer o seu movimento, tornando a experiência mais fluida.
                //Depois de 500ms, botMakeMove() é chamado, e o bot faz a sua jogada.
                // Isso evita que o bot jogue instantaneamente, tornando o jogo mais "natural".
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    botMakeMove()
                }, 500)
                //Se for a vez do bot (playerTurn == 2)
            } else {
                // Quando o bot acaba de jogar, volta o turno para o humano
                changePlayerTurn(1)
            }
            //Se o jogo não terminou (nem vitória, nem empate), a vez é passada para o próximo jogador:
            //Se o jogador atual for o 1, troca para o 2.
            //Se for 2, troca para o 1.
            //O método changePlayerTurn também atualiza visualmente a interface, destacando de quem é a vez.
        } else {
            changePlayerTurn(if (playerTurn == 1) 2 else 1)
        }
    }


        //Definição de uma função privada chamada incrementGameCount, que aceita um parâmetro context do tipo Context
        //O context é necessário para aceder às SharedPreferences, que são usadas para salvar dados persistentes no dispositivo.
        private fun incrementGameCount(context: Context) {

            //"context.getSharedPreferences()" é um método que retorna um objeto SharedPreferences,
            // que é uma forma de armazenar dados simples, como números e textos, no armazenamento local do dispositivo.
            //O primeiro argumento "TicTacToePrefs" é o nome do arquivo onde os dados serão armazenados.
            // O arquivo pode ter qualquer nome, mas deve ser único para não sobrescrever outro arquivo.
            //O segundo argumento "Context.MODE_PRIVATE" indica que o arquivo de preferências é privado
            // e só acessível pela aplicação atual
            val prefs = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)

            //Aqui tenta-se ir buscar o valor de "games_played" nas preferências.
            // Se não for encontrado é retornado o valor 0 (o que é útil para configurar o contador na primeira execução da app)
            // O valor recuperado (seja o número de jogos jogados ou o valor padrão 0) é então armazenado na variável currentCount.
            // Então, currentCount vai armazenar o número atual de jogos jogados.
            //Se games_played já foi armazenado como 5, currentCount vai ser 5
            //Se games_played não existir (primeira execução), currentCount será 0
            val currentCount = prefs.getInt("games_played", 0)

            //esta linha modifica o valor de "games_played" para que ele seja incrementado em 1
            //Chama-se o método edit() para iniciar uma transação de edição das preferências.
            // Esse metodo cria um objeto Editor que permite modificar as preferências armazenadas
            // (como adicionar ou modificar valores).
            //O método putInt() adiciona ou modifica o valor de uma chave nas SharedPreferences. Neste caso:
            //A chave é "games_played", ou seja, estamos a manipular o contador de jogos jogados.
            //O valor será currentCount + 1, ou seja, o valor atual (recuperado na etapa anterior) é incrementado em 1.
            // Se o valor de currentCount for 5, por exemplo, o valor que será armazenado será 6.
            //Após modificar as preferências com putInt(), é necessário chamar apply() para salvar as mudanças.
            prefs.edit().putInt("games_played", currentCount + 1).apply()
        }


        //Alternar a vez entre os jogadores e atualizar a interface visual para indicar de quem é a vez.
        private fun changePlayerTurn(currentPlayerTurn: Int) {

            //O valor de playerTurn é atualizado com o jogador passado como argumento (currentPlayerTurn).
            playerTurn = currentPlayerTurn

            if (playerTurn == 1) {
                //Se for a vez do jogador 1:
                //O layout do jogador 1 recebe uma borda branca (R.drawable.white_border) para indicar que está ativo.
                playerOneLayout.setBackgroundResource(R.drawable.white_border)

                //O layout do jogador 2 muda para um fundo indicando que é a próxima vez de jogar (R.drawable.box_to_play).
                playerTwoLayout.setBackgroundResource(R.drawable.box_to_play)

                //Se for a vez do jogador 2, o processo é invertido
            } else {
                playerTwoLayout.setBackgroundResource(R.drawable.white_border)
                playerOneLayout.setBackgroundResource(R.drawable.box_to_play)
            }
        }

        //Verificar se o jogador atual preencheu uma das combinações vencedoras
        //Este metodo é chamado após cada jogada para verificar se o jogador atual venceu
        //Verifica imediatamente se o jogador atual (quem acabou de jogar) venceu a partida.
        //Se o jogador venceu, desenha a linha da vitória na interface.
        //usada após cada jogada em (performAction())
        private fun checkPlayerWin(): Boolean {

            //Inicializar a variável response com false, assumindo que o jogador ainda não venceu
            //Se uma combinação vencedora for encontrada durante a execução, a variável será alterada para true.
            var response = false

            //A estrutura de repetição "for" percorre todas as combinações vencedoras armazenadas na lista
            // combinationsList.
            for (i in combinationsList.indices) {
                val combination = combinationsList[i]
                //O código está a verificar se todas as três casas indicadas pela combinação estão ocupadas pelo
                // mesmo jogador, ou seja, se o jogador atual (representado por playerTurn)
                // preencheu todas as casas dessa combinação.
                if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn
                ) {
                    drawWinningLine(combination)
                    response = true
                    updateWinCount(playerTurn)

                }
            }
            //Se todas as três casas estiverem ocupadas pelo mesmo jogador, a função retorna true (indicando vitória).
            return response
        }

        private fun updateWinCount(winner: Int) {
            val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()

            if (winner == 1) {
                val newCount = prefs.getInt("player1_wins", 0) + 1
                editor.putInt("player1_wins", newCount)
            } else if (winner == 2) {
                val newCount = prefs.getInt("player2_wins", 0) + 1
                editor.putInt("player2_wins", newCount)
            }

            editor.apply()
        }

        //Este método verifica se a casa selecionada pelo jogador está disponível para ser marcada
        private fun isBoxSelectable(boxPosition: Int): Boolean {

            //boxPosition in 1..9:
            //Garante que o valor da posição está dentro do intervalo válido (1 a 9),
            // correspondendo às 9 casas do tabuleiro.

            //boxPositions[boxPosition - 1] == 0:
            //Verifica se a posição selecionada no array boxPositions está vazia (0 significa que a casa ainda não
            // foi marcada por nenhum jogador).
            //O índice no array começa em 0, então boxPosition - 1 ajusta corretamente a posição para o
            // índice correspondente.
            return boxPosition in 1..9 && boxPositions[boxPosition - 1] == 0
        }


        //Este método redefine o jogo para um novo começo, restaurando as variáveis e a interface do utilizador.
        fun restartMatch() {

            gameCountUpdated = false
            boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            totalSelectedBoxes = 0

            //Esta linha está a atribuir um valor à variável playerTurn, que determina qual jogador (1 ou 2)
            // começará a próxima partida.
            //Condição (if (lastWinner == 0))
            //Verifica se a variável lastWinner é igual a 0, ou seja, se não houve vencedor na última partida
            // ( devido a um empate ou início do jogo).
            //Se a condição for verdadeira (lastWinner == 0) Executa (1..2).random(),
            // que gera um número aleatório entre 1 e 2, escolhendo aleatoriamente quem começa o jogo.
            //Se a condição for falsa (lastWinner != 0)
            //Significa que houve um vencedor na última partida, e o valor de lastWinner será atribuído a playerTurn,
            // garantindo que o mesmo jogador que venceu comece a próxima partida.
            playerTurn = if (lastWinner == 0) (1..2).random() else lastWinner


            updateTurnUI()

            image1.setImageResource(R.drawable.transparente)
            image2.setImageResource(R.drawable.transparente)
            image3.setImageResource(R.drawable.transparente)
            image4.setImageResource(R.drawable.transparente)
            image5.setImageResource(R.drawable.transparente)
            image6.setImageResource(R.drawable.transparente)
            image7.setImageResource(R.drawable.transparente)
            image8.setImageResource(R.drawable.transparente)
            image9.setImageResource(R.drawable.transparente)

            //O componente boardLineDrawer desapareçe da tela, tornando-se invisível e não ocupando espaço na interface.
            boardLineDrawer.visibility = View.GONE

            //O método (clearWinningLine) apaga a linha vencedora desenhada anteriormente
            boardLineDrawer.clearWinningLine()

            // Este código verifica se o jogador está a jogar contra o bot (playingAgainstBot == true).
            //Se for a vez do bot (playerTurn == 2), adiciona um atraso antes de chamar botMakeMove().
            //Isso impede que o bot jogue imediatamente, tornando o jogo mais natural.
            if (playingAgainstBot && playerTurn == 2) {
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    botMakeMove()
                }, 500)
            }

        }


    }