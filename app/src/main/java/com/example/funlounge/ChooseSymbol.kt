package com.example.funlounge
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
class ChooseSymbolActivity : AppCompatActivity() {
    //Declaração das variáveis
    private lateinit var playerNameInput: EditText
    private lateinit var buttonChooseX: Button
    private lateinit var buttonChooseO: Button
    private lateinit var buttonStartGame: Button
    // Variável que armazena o símbolo escolhido pelo jogador (X é o padrão).
    private var selectedSymbol: String = "X"
    ///Variável para armazenar se o jogador vai enfrentar um bot ou um amigo(Padrão é jogar contra amigo)
    private var playingAgainstBot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_escolha_bot)
        //Como a Activity anterior(MenuDecisaoJogo) enviou true, playingAgainstBot será true
        playingAgainstBot = intent.getBooleanExtra("playingAgainstBot", false)
        // Vincular os elementos do layout
        playerNameInput = findViewById(R.id.playerNameInput)
        buttonChooseX = findViewById(R.id.buttonChooseX)
        buttonChooseO = findViewById(R.id.buttonChooseO)
        buttonStartGame = findViewById(R.id.buttonStartGame)
        //Se o jogador clicar no botão X, a variável selectedSymbol será "X".
        buttonChooseX.setOnClickListener { selectedSymbol = "X" }
        //Se o jogador clicar no botão O, a variável selectedSymbol será "O".
        buttonChooseO.setOnClickListener { selectedSymbol = "O" }
        // Iniciar o jogo e enviar os dados para a MainActivity
        buttonStartGame.setOnClickListener {
            //Obtém o nome digitado pelo jogador.
            val playerName = playerNameInput.text.toString()
            //Intent é um objeto que diz ao sistema para abrir outra tela (Activity).
            //O primeiro argumento (this) refere-se à Activity atual (ChooseSymbolActivity).
            //O segundo argumento (MainActivity::class.java) indica a Activity que queremos abrir.
            val intent = Intent(this, MainActivityJogo::class.java)
            //putExtra("chave", valor) adiciona informações extras à Intent.
            //Essas informações poderão ser recuperadas na MainActivity.
            // Os dados que estão a ser enviados são:
            //Nome do jogador (playerName) → O nome que o jogador digitou no campo de texto.
            //Símbolo escolhido (selectedSymbol) → "X" ou "O", dependendo do botão clicado.
            //Modo de jogo (playingAgainstBot) → true se estiver a jogar contra o BOT, false se estiver jogar contra um amigo.
            intent.putExtra("playerName", playerName)
            intent.putExtra("playerSymbol", selectedSymbol)
            intent.putExtra("playingAgainstBot", playingAgainstBot)
            // Executa a Intent, abrindo a MainActivity.
            //A MainActivity agora pode recuperar os dados enviados e configurar o jogo.
            startActivity(intent)
            // encerra a ChooseSymbolActivity, removendo-a da memória.
            finish()
        }
    }
}