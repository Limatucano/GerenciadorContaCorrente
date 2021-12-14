package com.matheus.gerenciadorcontacorrente.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matheus.gerenciadorcontacorrente.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    private var tipoCompra : String? = null
    private var contaCorrente : String? = null
    private var nome : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = MainFragmentArgs.fromBundle(bundle)
        tipoCompra = args.tipoConta
        contaCorrente = args.contaCorrente
        nome = args.nome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}