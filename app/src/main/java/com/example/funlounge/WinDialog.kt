package com.example.funlounge


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView



class WinDialog(context: Context, private val message: String, private val mainActivity: MainActivity)
    : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //O layout do diálogo é baseado no arquivo XML chamado win_dialog, onde está uma TextView para mostrar a
        // mensagem e um Button para reiniciar o jogo.
        setContentView(R.layout.win_dialog)

        //Referências aos componentes do layout (a TextView que exibirá a mensagem e o Button que permitirá reiniciar
        // o jogo) são obtidas com findViewById.
        val messageTxt: TextView = findViewById(R.id.messageTxt)
        val startAgainBtn: Button = findViewById(R.id.startAgainBtn)
        val retrocederBtn:Button = findViewById(R.id.retrocederBtn)

        //A mensagem que foi passada como parâmetro é atribuída à TextView, assim, a mensagem personalizada
        // será exibida no diálogo
        messageTxt.text = message

        //Quando o botão de reiniciar é pressionado, o método restartMatch() da MainActivity é chamado
        // (Para reiniciar o jogo).
        //Em seguida, o diálogo é fechado com o método dismiss().
        startAgainBtn.setOnClickListener {
            mainActivity.restartMatch()
            dismiss()
        }

        retrocederBtn.setOnClickListener {
            val intent = Intent(context, MenuTransicao::class.java)
            context.startActivity(intent)
        }
    }
}
