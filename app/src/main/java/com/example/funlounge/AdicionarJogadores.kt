package com.example.funlounge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

   //O menu em que se inserem os nomes dos dois jogadores humanos que vão jogar
class AdicionarJogadores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adicionar_jogadores)

        //Buscar os elementos da interface gráfica (o botão e os dois campos de texto) definidos no arquivo XML
        // do layout da tela ("activity_adicionar_jogadores.xml") e associa-os a variáveis.
        // Isto permite manipular esses elementos, como obter o texto digitado pelo utilizador
        // ou definir ações quando o botão for clicado.
        val jogador1: EditText = findViewById(R.id.jogador1nome)
        val jogador2: EditText = findViewById(R.id.jogador2nome)
        val comecarJogoBtn: Button = findViewById(R.id.ComecarJogoBtn)



        //O OnClickListener é configurado para o botão comecarJogoBtn. Isso significa que, quando o utilizador
        // clicar no botão, o código dentro do bloco de código do setOnClickListener será executado.
        comecarJogoBtn.setOnClickListener {

            //Os valores de texto dos campos de entrada jogador1 e jogador2 são "capturados"
            val getPlayerOneName = jogador1.text.toString()
            val getPlayerTwoName = jogador2.text.toString()

            //Aqui, é feita uma verificação para garantir que ambos os campos (jogador 1 e jogador 2)
            // foram preenchidos. Se qualquer um dos campos estiver vazio (isEmpty()), um Toast será exibido
            // com a mensagem "Por favor insira o nome dos Jogadores", alertando o utilizador para preencher os nomes.
            if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                Toast.makeText(this, "Por favor insira o nome dos Jogadores", Toast.LENGTH_SHORT).show()


            //Se os dois nomes foram fornecidos, um Intent é criado para iniciar a MainActivity
            // Os nomes dos jogadores são passados para a MainActivity através de putExtra(), que insere os dados
            // no Intent. Isso permite que a MainActivity receba os nomes dos jogadores e os utilize.
            } else {
                savePlayerNames(getPlayerOneName, getPlayerTwoName) // Save names
                resetPlayerStats()

                val intent = Intent(this, MainActivityJogo::class.java).apply {
                    putExtra("jogador1", getPlayerOneName)
                    putExtra("jogador2", getPlayerTwoName)
                }
                //Inicia a MainActivity com os dados dos jogadores.
                startActivity(intent)
                //Fechar a tela atual (onde os nomes foram inseridos),  transferindo o controle para a MainActivity
                finish()
            }
        }

    }

    private fun resetPlayerStats() {
        val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putInt("player1_wins", 0)
        editor.putInt("player2_wins", 0)
        editor.putInt("games_played", 0)
        editor.apply()
    }

    private fun savePlayerNames(player1: String, player2: String) {
        val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("player1_name", player1)
        editor.putString("player2_name", player2)
        editor.apply()
    }
}
