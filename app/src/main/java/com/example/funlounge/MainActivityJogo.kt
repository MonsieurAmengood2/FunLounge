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


        //Se o elemento jogador1nome estiver presente no layout:"(R.layout.activity_main)", ele será encontrado e
        // referenciado pela variável playerOneName
        playerOneName = findViewById(R.id.jogador1nome)
        playerTwoName = findViewById(R.id.jogador2nome)

       //Estas duas linhas procuram os elementos da interface que têm o ID jogador1layout e jogador2layout
        // no arquivo XML activity_main.xml, e associam esses elementos às variáveis playerOneLayout e
        // playerTwoLayout, respectivamente.
        playerOneLayout = findViewById(R.id.jogador1layout)
        playerTwoLayout = findViewById(R.id.jogador2layout)

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
        val playerName = if (playerTurn == 1) playerOneName.text.toString() else playerTwoName.text.toString()

        //Escolher a imagem correta com base no jogador atual
        //R.drawable.cruz: Ícone de X para o jogador 2.
        //R.drawable.bola: Ícone de O para o jogador 1.
        val imageRes = if (playerTurn == 1) R.drawable.bola else R.drawable.cruz

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
                boxPositions[combination[2]] == playerTurn)
            {
                drawWinningLine(combination)
                response = true

            }
        }
        //Se todas as três casas estiverem ocupadas pelo mesmo jogador, a função retorna true (indicando vitória).
        return response
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

    }

}