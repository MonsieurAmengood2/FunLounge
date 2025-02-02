package com.example.funlounge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

   // O método onCreate() da classe AppCompatActivity é chamado automaticamente quando a Activity é iniciada
   //Recebe um Bundle? (savedInstanceState), que pode conter dados salvos da última vez que a tela foi aberta.
   //O método onCreate() sempre é chamado primeiro quando uma Activity começa
    override fun onCreate(savedInstanceState: Bundle?) {

        //Se não chamarmos "super.onCreate(savedInstanceState)" a Activity pode não funcionar corretamente
        super.onCreate(savedInstanceState)

        //carregar o arquivo XML login.xml
        setContentView(R.layout.login)

        //Conectar os elementos do layout (EditText e TextView) ao código Kotlin
        val loginInput: EditText = findViewById(R.id.user)
        val password: EditText = findViewById(R.id.password)
        val signInBtn: TextView = findViewById(R.id.signInBtn)

        // Aqui define-se um "ouvinte" (listener) que detecta quando o botão signInBtn é clicado.
        //Quando o utilizador clica no botão, o código dentro das { } é executado.
        signInBtn.setOnClickListener {

            //val username = "   joao123   ".trim()
            //println(username)  // "joao123" (sem espaços)
            //".trim()"->Remove espaços vazios no início e no final da string evitando erros de login
            // causados por espaços extras
            val userText = loginInput.text.toString().trim()
            val passwordText = password.text.toString().trim()

            //Se o utilizador tentar clicar sem preencher os campos, verá esta mensagem
            if (userText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Cria um cliente Retrofit (apiService) que chamará a API.
            val apiService = RetrofitClient.instance.create(ApiService::class.java)

            // requisição "POST /login" para a API Node.js(que está a rodar na backend) usando Retrofit
            val call = apiService.loginUser(LoginRequest(userText, passwordText))

            //call.enqueue(...) → Envia a requisição de login para o servidor.
            //Callback<LoginResponse> → Espera uma resposta do tipo LoginResponse (que contém o token).
            call.enqueue(object : Callback<LoginResponse> {

                //Se o servidor responder, esta função é chamada.
                // É chamada automaticamente quando o servidor responde à requisição feita pelo Retrofit.
                // Processa a resposta HTTP enviada pelo servidor Node.js.
                // Isto significa que, quando a API responde, a app Android executa automaticamente este código.

                //Call<LoginResponse>-->Diz que essa requisição espera receber um objeto do tipo LoginResponse como resposta.
                // É uma espécie de "contrato" entre o Retrofit e a API.

                //Response<LoginResponse>--> representa a resposta que chegou
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    //Verifica se a resposta foi bem-sucedida
                    //response.isSuccessful->verifica se o código HTTP está entre 200 e 299, ou seja,
                    // se a requisição foi bem-sucedida
                    if (response.isSuccessful) {

                        //response.body()-->contém o corpo da resposta JSON que veio do servidor.
                        //token recebe o valor do campo "token" da resposta.
                        val token = response.body()?.token

                        //Se o token não for null, significa que o servidor enviou um token válido.
                        if (token != null) {

                            // Salvar o token no SharedPreferences
                            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("token", token) // Armazenar o token
                            editor.apply() // Salva as mudanças

                            //Exibe um Toast ("Login bem-sucedido!") para informar o utilizador
                            Toast.makeText(this@Login, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                            //Cria uma Intent para abrir a próxima tela (MenuTransicao)
                            val intent = Intent(this@Login, MenuTransicao::class.java)

                            //Envia o token para a próxima tela
                            intent.putExtra("TOKEN", token)

                            //Inicia a próxima tela
                            startActivity(intent)

                            //Se o token for null, significa que o servidor não enviou um token válido.
                            //Exibe "Erro: Token não recebido!" para alertar o utilizador.
                        } else {
                            Toast.makeText(this@Login, "Erro: Token não recebido!", Toast.LENGTH_SHORT).show()
                        }
                    // Se o login falhar (401 Unauthorized ou outro erro)
                    } else {
                        when (response.code()) {

                            //Se for "401 Unauthorized", significa que as credenciais estavam erradas, então exibe "Credenciais inválidas!"
                            401 -> Toast.makeText(this@Login, "Credenciais inválidas!", Toast.LENGTH_SHORT).show()

                            //Se for qualquer outro erro (500, 403, etc.), exibe "Erro ao fazer login! Código: X", onde X é o código do erro.
                            else -> Toast.makeText(this@Login, "Erro ao fazer login! Código: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //Aqui tratam-se de erros de conexão (quando a app Android não se consegue  comunicar com o servidor Node.js).
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Erro ao conectar: ${t.message}")
                    Toast.makeText(this@Login, "Erro ao conectar ao servidor!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
