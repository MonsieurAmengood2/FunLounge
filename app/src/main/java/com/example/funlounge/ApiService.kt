package com.example.funlounge

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Quando um utilizador se regista, a app envia username, email e password para o servidor.
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

// Quando um utilizador faz login, a app envia o username e password.
data class LoginRequest(
    val loginInput: String,
    val password: String
)

// Se o login for bem-sucedido, o servidor responde com um token JWT para autenticação
//Esse token será guardado no app para manter a sessão do utilizador
data class LoginResponse(
    val token: String
)



// A interface define as chamadas que o Retrofit pode fazer para comunicar com o servidor.
interface ApiService {

    //Cada método corresponde a um endpoint do servidor

    //Quando um utilizador se registra (POST /register):
    //A app envia o username, email e password para o servidor.
    //Se o registro for bem-sucedido, o servidor não retorna nada(Call<Void> )
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<Void>

    //Quando um utilizador faz login (POST /login):
    //A app envia username e password para a API.
    //Se as credenciais estiverem corretas, o servidor responde com um token JWT.
    //Esse token é salvo na app para manter a sessão do utilizador
    @POST("/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

}
