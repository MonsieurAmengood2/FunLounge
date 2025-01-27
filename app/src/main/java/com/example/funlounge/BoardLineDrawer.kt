package com.example.funlounge

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


//Classe personalizada chamada BoardLineDrawer que herda da classe View, permitindo que o componente seja
// desenhado no ecrã.
class BoardLineDrawer(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {


    //A variável paint é criada como uma instância da classe Paint, que é usada no Android para definir estilos de desenho
    // (como cor, espessura e efeitos visuais).
    //A função .apply {} é uma função que permite configurar o objeto Paint dentro do bloco de código
    //A variável paint é privada, ou seja, só pode ser usada dentro desta classe
    private val paint = Paint().apply {
        color = android.graphics.Color.GREEN  // Cor da linha
        strokeWidth = 15f  // Espessura da linha
    }

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f
    private var isWinningLineDrawn = false

    // Configurar a linha com coordenadas específicas
    fun drawWinningLine(sX: Float, sY: Float, eX: Float, eY: Float) {
        startX = sX  //Posição inicial no eixo X (horizontal).
        startY = sY  //Posição inicial no eixo Y (vertical)
        endX = eX    //Posição final no eixo X (horizontal).
        endY = eY    //Posição final no eixo Y (vertical).

        //Esta variável booleana (true ou false)
        // indica se a linha deve ser desenhada ou não.
        isWinningLineDrawn = true

        //nvalidate() pede ao sistema para redesenhar a interface gráfica,
        // acionando automaticamente o método onDraw()
        //Quando chamamos invalidate(), a View é marcada como "inválida" e o Android sabe que precisa ser
        // redesenhada. Isso resulta na execução do método onDraw() na próxima atualização da interface do utilizador
        invalidate()  // Redesenha a view
    }

    fun clearWinningLine() {

        //Aqui é dito ao sistema para não desenhar a linha vencedora
        isWinningLineDrawn = false
        //Aqui chama-se o invalidate() novamente, para forçar a atualização da interface gráfica,
        // o que resulta na remoção da linha vencedora, já que o valor está agora a false
        invalidate()
    }


    //Personalizar o comportamento padrão do método onDraw() da classe View para criar o meu próprio desenho na tela.
    //O método onDraw recebe um objeto Canvas que permite desenhar gráficos personalizados, como textos, imagens,
    // formas geométricas, etc.
    //A variável canvas (com letra minúscula) é uma variável local dentro do método onDraw(),
    // e pertence à classe android.graphics.Canvas, que é uma classe fornecida pelo framework do Android para
    // desenhar gráficos.
    //Essa classe contém os métodos necessários para desenhar na View
    override fun onDraw(canvas: Canvas) {

        // Mantém o comportamento normal da View
        //Isto é usado para garantir que a View execute as suas operações padrão antes (ou depois) de adicionar elementos
        // gráficos personalizados.
        super.onDraw(canvas)

        //Verifica se a variável booleana isWinningLineDrawn está definida como true.
        //Se for true, significa que há uma linha vencedora para ser desenhada.
        //Se for false, o método simplesmente não fará nada, ou seja, nenhuma linha será desenhada.
        if (isWinningLineDrawn) {
            //Aqui ocorre o desenho real da linha vencedora na tela
            //Exemplo visual da linha sendo desenhada (de (startX, startY) para (endX, endY)):
            //(startX, startY) ----------------- (endX, endY)
            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }
}