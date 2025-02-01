package com.example.funlounge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val username: EditText = findViewById(R.id.username)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val confirmpassword: EditText = findViewById(R.id.confirmpassword)
        val signUpBtn: TextView = findViewById(R.id.signUpBtn)

        signUpBtn.setOnClickListener {
            //Pega o texto digitado pelo utilizador e converte-o para String.
            val usernameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()
            val confirmPasswordText = confirmpassword.text.toString().trim()

            //Se algum campo estiver vazio, exibe "Preencha todos os campos".
            if (usernameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                //Esta linha impede que o código continue dentro do bloco "setOnClickListener" e faz com que ele pare a execução ali mesmo.
                //Isso significa que qualquer código abaixo de "return@setOnClickListener" não será executado se a condição for atendida.
                //Mas não "volta" para nenhum outro lugar, apenas finaliza a execução do setOnClickListener
                return@setOnClickListener
            }

            // Verificar se o email é válido
            if (!isValidEmail(emailText)) {
                Toast.makeText(this, "Email inválido,domínio não aceite", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Verifica se a senha é válida
            if (!isValidPassword(passwordText)) {
                Toast.makeText(
                    this,
                    "A senha deve ter pelo menos 6 caracteres, uma letra maiúscula, uma minúscula e um símbolo!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }


            //Se as senhas forem diferentes, exibe "As senhas não coincidem" no Toast
            if (passwordText != confirmPasswordText) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Cria um objeto apiService que permite chamar a API (POST /register).
            val apiService = RetrofitClient.instance.create(ApiService::class.java)

            val call = apiService.registerUser(RegisterRequest(usernameText, emailText, passwordText))

            //Usa enqueue() para enviar a requisição ao servidor e aguardar a resposta assíncrona
            call.enqueue(object : Callback<Void> {
                //Se o servidor responder, processa a resposta
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    //Se response.isSuccessful == true (201 Created), significa que o utilizador foi registrado com sucesso.
                    //"isSuccessful" é um método da classe Response<T> do Retrofit.
                    // Verifica se a resposta da API tem um código HTTP entre 200 e 299 (sucesso).
                    if (response.isSuccessful) {

                        Toast.makeText(this@Register, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show()

                        //Redireciona para a tela de login (Login.kt)
                        //this@Register → Referência para a Activity atual (onde o código está a ser executado).
                        //Login::class.java → A Activity de destino (a tela que queremos abrir)
                        startActivity(Intent(this@Register, Login::class.java))

                        // Fecha a tela de registro
                        finish()

                    //Se a resposta não for 201 Created, significa que houve um erro.
                    } else {
                        Toast.makeText(this@Register, "Erro ao registrar! Tente outro e-mail ou username.", Toast.LENGTH_SHORT).show()
                    }
                }


                //Aqui tratam-se de erros de conexão (quando a app Android não se consegue  comunicar com o servidor Node.js).
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("API_ERROR", "Erro: ${t.message}")
                    Toast.makeText(this@Register, "Erro ao conectar ao servidor!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    //Verificar se um email é válido antes de permitir o registro do utilizador
    private fun isValidEmail(email: String): Boolean {
        //Esta lista será usada para verificar se o email termina com um domínio válido
        val allowedDomains = listOf("@gmail.com", "@outlook.pt", "@outlook.com", "@hotmail.com")
        //Duas condiçoes que precisam ser true para validar o email
        //"Patterns.EMAIL_ADDRESS" → É um padrão de email pré-definido no Android.
        //Patterns.EMAIL_ADDRESS.matcher("joao@gmail.com").matches() -->true (válido)
        //Patterns.EMAIL_ADDRESS.matcher("joao@gmail").matches()--> false (falta .com)
        //any { email.endsWith(it) } percorre a "listaOf" e vê se o email termina com algum desses domínios.
        //Em suma,primeiro vemos se o email segue um padrao comum e depois se o dominio pertence à lista
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && allowedDomains.any { email.endsWith(it) }
    }
    // Função que valida se uma senha atende a certos critérios antes de ser aceite.
    private fun isValidPassword(password: String): Boolean {
        //"(?=.*[a-z])"-->	Deve ter pelo menos uma letra minúscula (a-z)
        //"(?=.*[A-Z])"-->	Deve ter pelo menos uma letra maiúscula (A-Z)
        //"(?=.*[!@#\$%^&*.,?])"-->	Deve ter pelo menos um símbolo especial (!@#$%^&*.,?)
        //".{6,}$"-->Deve ter pelo menos 6 caracteres
        //"$"-->	Fim da senha
        // Regex faz parte da biblioteca Java Standard Library, então já está embutido na linguagem.
        // Portanto não é preciso importar nada, pois já está disponível.
        //O regex cria um padrão de busca para validar strings.
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*.,?]).{6,}$")

        //verifica se a senha digitada pelo utilizador corresponde ao padrão definido pelo Regex.
        //Se a senha for válida, retorna true
        return regex.matches(password)
    }
}
