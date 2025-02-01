package com.example.funlounge


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuDecisaoJogo: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_decisao_jogo)


        val BotBtn: TextView = findViewById(R.id.BotBtn)
        val AmigoBtn: TextView = findViewById(R.id.AmigoBtn)

        //Quando o botão para jogar contra o bot for clicado, vai ser aberta a ChooseSymbolActivity
        BotBtn.setOnClickListener {
            val intent = Intent(this, ChooseSymbolActivity::class.java)
            intent.putExtra("playingAgainstBot", true)
            startActivity(intent)
        }

        //Quando o botão para jogar contra outra pessoa for clicado, vai ser aberta a activity AdicionarJogadores
        AmigoBtn.setOnClickListener {
            val intent = Intent(this, AdicionarJogadores::class.java)
            startActivity(intent)
        }





    }
}