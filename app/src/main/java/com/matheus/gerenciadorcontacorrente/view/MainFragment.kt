package com.matheus.gerenciadorcontacorrente.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Database
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import com.matheus.gerenciadorcontacorrente.databinding.FragmentMainBinding
import com.matheus.gerenciadorcontacorrente.viewmodel.MainViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.MainViewModelFactory
import com.matheus.gerenciadorcontacorrente.viewmodel.loginViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.loginViewModelFactory


class MainFragment : Fragment() {
    private var tipoConta : String? = null
    lateinit var contaCorrente : String
    private var nome : String? = null
    private lateinit var viewBinding : FragmentMainBinding
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = MainFragmentArgs.fromBundle(bundle)
        tipoConta = args.tipoConta
        contaCorrente = args.contaCorrente
        nome = args.nome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionsDAO = context?.let { Database.getDatabase(it)?.actionsDAO() }!!
        val saudacaoCliente = "Olá, ${nome}"
        viewBinding.nomeCliente.text = saudacaoCliente
        viewModel = ViewModelProvider(this,MainViewModelFactory(ActionsRepository(actionsDAO))).get(MainViewModel::class.java)

        if(tipoConta == "VIP"){
            viewBinding.btnVisita.visibility = View.VISIBLE
        }
        viewBinding.btnTrocarUsuario.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
        viewBinding.btnVerSaldo.setOnClickListener {
            viewModel.getValueByuser(contaCorrente)
            viewModel.saldo.observe(requireActivity(),{
                val saldo = "R$ ${it}"
                viewBinding.saldo.text = saldo
            })
        }

        viewBinding.btnExtrato.setOnClickListener{
            val direction = MainFragmentDirections.actionMainFragmentToExtratoFragment(contaCorrente)
            view.findNavController().navigate(direction)
        }
        viewBinding.btnVisita.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToVisitaFragment(contaCorrente)
            view.findNavController().navigate(direction)
        }
        viewBinding.btnDeposito.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToDepositoFragment(tipoConta!!, nome!!, contaCorrente)
            view.findNavController().navigate(direction)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater, container,false)
        return viewBinding.root
    }

}