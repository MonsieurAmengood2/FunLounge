package com.example.funlounge

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



//O Retrofit facilita a comunicação entre a app e o servidor (Node.js no nosso caso).
// O Retrofit atua como intermediário entre a app móvel e o servidor usando a interface ApiService.kt para definir as chamadas HTTP.
// O que o Retrofit faz na prática?
//-> Envia pedidos HTTP (GET, POST, PUT, DELETE) para o servidor.
//-> Recebe respostas do servidor e processa os dados.
//-> Converte JSON automaticamente para objetos Kotlin.
//-> Facilita a comunicação entre a app e o backend
//-->Fonte:https://medium.com/@ardasenbakkavaci/consuming-nodejs-backend-api-in-android-kotlin-3d93f43aa4ac
object RetrofitClient {
    //O 10.0.2.2 é o endereço usado para aceder ao localhost (127.0.0.1) dentro do Emulador Android.
    //Define o endereço do servidor Node.js--> http://10.0.2.2:3001/
    private const val BASE_URL = "http://10.0.2.2:3001/"

    //by lazy { ... }--> Faz com que o Retrofit só seja inicializado quando for necessário
    // Retrofit não será criado na inicialização da app, apenas quando for chamado.
    val instance: Retrofit by lazy {
        //Cria um objeto Retrofit que será usado para fazer chamadas de API.
        Retrofit.Builder()
            // Esse URL será usado como prefixo para todas as chamadas definidas no ApiService.kt
            // Se BASE_URL = "http://10.0.2.2:3001/", todas as requisições usarão esse URL:
            //POST /login → http://10.0.2.2:3001/login
            //POST /register → http://10.0.2.2:3001/register
            .baseUrl(BASE_URL)

            //Adiciona um "conversor" para transformar automaticamente JSON em objetos Kotlin.
            .addConverterFactory(GsonConverterFactory.create())

            //Finaliza a configuração e cria o objeto Retrofit pronto para uso.
            //Agora, podemos usar esse Retrofit para chamar a API com ApiService.kt
            .build()
    }
}