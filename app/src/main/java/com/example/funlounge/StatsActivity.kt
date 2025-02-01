
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

        // "gamesPlayed" recebe o número de jogos jogados, que é obtido pela função getGameCount(this)
        val gamesPlayed = getGameCount(this)

        //Atualiza o texto no TextView com o número de jogos jogados. O valor de gamesPlayed é interpolado na string e exibido.
        txtGamesPlayed.text = "Jogos Jogados: $gamesPlayed"

        //Testar funcionamento do incremento de jogos jogados
        btnResetGames.setOnClickListener {
            resetGameCount(this) // Dar reset ao contador
            txtGamesPlayed.text = "Jogos Jogados: 0" // Atualizar a interface
            Toast.makeText(this, "Número de jogos resetado!", Toast.LENGTH_SHORT).show()
        }

    }

    // Método para obter o número de jogos do SharedPreferences
    //Esta função é responsável por obter o número de jogos jogados armazenados nas SharedPreferences.
    // Uma conta de email diferente no mesmo telefone ->O número de jogos não muda, pois o SharedPreferences continua
    // armazenando os mesmos dados locais portanto se mudar de conta no mesmo telemovel, os jogos jogados ainda estarão
    // lá porque o SharedPreferences não diferencia contas
    private fun getGameCount(context: Context): Int {
        val prefs = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        return prefs.getInt("games_played", 0) // Retorna 0 se não houver valor salvo
    }

    //Testar funcionamento do incremento de jogos jogados
    //Este codigo serve so pra debug
    fun resetGameCount(context: Context) {
        val prefs = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("games_played", 0).apply()
    }


}
