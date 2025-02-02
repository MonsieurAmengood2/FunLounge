package com.example.funlounge


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//menu em que se decide jogar ou contra o bot ou contra um amigo
class MenuDecisaoJogo: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_decisao_jogo)


        val botBtn: TextView = findViewById(R.id.BotBtn)
        val amigoBtn: TextView = findViewById(R.id.AmigoBtn)

        //Quando o botão para jogar contra o bot for clicado, vai ser aberta a ChooseSymbolActivity
        botBtn.setOnClickListener {
            val intent = Intent(this, ChooseSymbolActivity::class.java)
            intent.putExtra("playingAgainstBot", true)
            startActivity(intent)
        }

        //Quando o botão para jogar contra outra pessoa for clicado, vai ser aberta a activity AdicionarJogadores
        amigoBtn.setOnClickListener {
            val intent = Intent(this, AdicionarJogadores::class.java)
            startActivity(intent)
        }





    }
}