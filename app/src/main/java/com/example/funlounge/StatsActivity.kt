
package com.example.funlounge
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val btnResetGames: Button = findViewById(R.id.btnResetGames)
        val txtGamesPlayed: TextView = findViewById(R.id.txtGamesPlayed)

        val txtPlayer1Wins: TextView = findViewById(R.id.txtPlayer1Wins)
        val txtPlayer2Wins: TextView = findViewById(R.id.txtPlayer2Wins)

        val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)

        val player1Wins = prefs.getInt("player1_wins", 0)
        val player2Wins = prefs.getInt("player2_wins", 0)
        val player1Name = prefs.getString("player1_name", "Jogador 1")
        val player2Name = prefs.getString("player2_name", "Jogador 2")

        // "gamesPlayed" recebe o número de jogos jogados, que é obtido pela função getGameCount(this)
        val gamesPlayed = getGameCount(this)
        //Atualiza o texto no TextView com o número de jogos jogados. O valor de gamesPlayed é interpolado na string e exibido.
        txtGamesPlayed.text = "Jogos Jogados: $gamesPlayed"

        //dá a estatística das vitórias por jogador
        txtPlayer1Wins.text = "$player1Name - Vitórias: $player1Wins"
        txtPlayer2Wins.text = "$player2Name - Vitórias: $player2Wins"

        //Testar funcionamento do incremento de jogos jogados
        btnResetGames.setOnClickListener {
            resetGameCount(this) // Resetar o contador
            txtGamesPlayed.text = "Jogos Jogados: 0" // Atualizar a interface

            txtPlayer1Wins.text = "$player1Name - Vitórias: 0"
            txtPlayer2Wins.text = "$player2Name - Vitórias: 0"

            Toast.makeText(this, "Número de jogos reiniciado!", Toast.LENGTH_SHORT).show()
        }

    }

    // Método para obter o número de jogos do SharedPreferences
    //Esta função é responsável por obter o número de jogos jogados armazenados nas SharedPreferences.
    private fun getGameCount(context: Context): Int {
        val prefs = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        return prefs.getInt("games_played", 0) // Retorna 0 se não houver valor salvo
    }

    //Testar funcionamento do incremento de jogos jogados
    fun resetGameCount(context: Context) {
        val prefs = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("games_played", 0).apply()
    }

}
