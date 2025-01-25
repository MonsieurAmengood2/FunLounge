package com.example.funlounge

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdicionarJogadores : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adicionar_jogadores)

        //Buscar os elementos da interface gráfica (o botão e os dois campos de texto) definidos no arquivo XML
        // do layout da tela ("activity_adicionar_jogadores.xml") e associa-os a variáveis.
        // Isto permite manipular esses elementos, como obter o texto digitado pelo utilizar
        // ou definir ações quando o botão for clicado.
        val jogador1: EditText = findViewById(R.id.jogador1nome)
        val jogador2: EditText = findViewById(R.id.jogador2nome)
        val comecarJogoBtn: Button = findViewById(R.id.ComecarJogoBtn)


        comecarJogoBtn.setOnClickListener {
            val getPlayerOneName = jogador1.text.toString()
            val getPlayerTwoName = jogador2.text.toString()

            if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                Toast.makeText(this, "Por favor insira o nome dos Jogadores", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("jogador1", getPlayerOneName)
                    putExtra("jogador2", getPlayerTwoName)
                }
                startActivity(intent)
            }
        }
    }
}