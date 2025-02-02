package com.example.funlounge.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.funlounge.R
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.example.funlounge.AdicionarJogadores
import com.example.funlounge.MenuDecisaoJogo
import com.example.funlounge.MenuDefinicoes
import com.example.funlounge.StatsActivity

/**
 * A simple [Fragment] subclass.
 * Use the [transicaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuTransicaoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_transicao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jogarBtn: TextView = view.findViewById(R.id.JogarBtn)
        val statsBtn: TextView = view.findViewById(R.id.StatsBtn)
        val definBtn: Button = view.findViewById(R.id.DefinBtn)

        jogarBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MenuDecisaoJogo::class.java)
            startActivity(intent)
        }

        definBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MenuDefinicoes::class.java)
            startActivity(intent)
        }

        statsBtn.setOnClickListener {
            val intent = Intent(requireActivity(), StatsActivity::class.java)
            startActivity(intent)
        }
    }
}