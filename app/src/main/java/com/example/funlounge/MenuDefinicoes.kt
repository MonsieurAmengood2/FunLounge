package com.example.funlounge


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MenuDefinicoes: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_definicoes_utilizador)


        val EliminarPerfilBtn: TextView = findViewById(R.id.EliminarPerfilBtn)
        val SairBtn: TextView = findViewById(R.id.SairBtn)

        // Adicionar ação ao botão "Eliminar Conta"
        EliminarPerfilBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)

            if (token != null) {
                eliminarConta(token) // Chama a função com o token do utilizador autenticado
            } else {
                Toast.makeText(this, "Erro: Token não encontrado!", Toast.LENGTH_SHORT).show()
            }
        }

        // Quando clicar no botão "Sair" ir para o MenuInicial (sem eliminar a conta)
        SairBtn.setOnClickListener {
            val intent = Intent(this, MenuInicial::class.java)
            startActivity(intent)
            finish()
        }


    }


    //Esta função faz uma requisição DELETE para o servidor remoto e envia o username do utilizador para eliminá-lo.
    private fun eliminarConta(token: String) {

        //Cria uma instância de OkHttpClient, que é usada para fazer requisições HTTP.
        val client = OkHttpClient()

        //Configuração da Requisição HTTP Delete
        val request = Request.Builder()
            .url("https://web-production-43f0a.up.railway.app/deleteUser")
            .delete()
            .addHeader("Authorization", "Bearer $token") // Adiciona o token no cabeçalho da requisição
            .build()

        //Executa a requisição HTTP DELETE que foi criada anteriormente.
        client.newCall(request).enqueue(object : Callback {
            //onFailure será chamado se a requisição falhar (exemplo: erro de conexão, servidor offline).
            //Usa runOnUiThread para mostrar um Toast notificando o utilizador que ocorreu um erro.
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MenuDefinicoes, "Erro ao eliminar a conta!", Toast.LENGTH_SHORT).show()
                }
            }

           //Se a requisição acontecer
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    //Se response.isSuccessful (código 200 OK):
                    if (response.isSuccessful) {

                        Toast.makeText(this@MenuDefinicoes, "Conta eliminada com sucesso!", Toast.LENGTH_LONG).show()


                        // Criar um Intent para navegar até ao MenuInicial
                        val intent = Intent(this@MenuDefinicoes, MenuInicial::class.java)
                        startActivity(intent) // Iniciar a nova atividade

                        ////Fecha a Activity atual
                        finish()
                    } else {
                        Toast.makeText(this@MenuDefinicoes, "Erro ao eliminar a conta!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
