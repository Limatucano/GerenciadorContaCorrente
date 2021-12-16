package com.matheus.gerenciadorcontacorrente.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.Database
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import com.matheus.gerenciadorcontacorrente.databinding.FragmentTransferenciaBinding
import com.matheus.gerenciadorcontacorrente.viewmodel.MainViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.MainViewModelFactory
import com.matheus.gerenciadorcontacorrente.viewmodel.TransferenciaViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.TransferenciaViewModelFactory
import java.time.OffsetDateTime


class TransferenciaFragment : Fragment() {

    private lateinit var viewBinding : FragmentTransferenciaBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var transferenciaViewModel : TransferenciaViewModel
    lateinit var contaCorrente : String
    lateinit var tipoConta : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = TransferenciaFragmentArgs.fromBundle(bundle)
        contaCorrente = args.contaCorrente
        tipoConta = args.tipoConta

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionsDAO = context?.let { Database.getDatabase(it)?.actionsDAO() }!!
        val userDAO = context?.let { Database.getDatabase(it)?.userDAO() }!!
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(ActionsRepository(actionsDAO))).get(MainViewModel::class.java)
        transferenciaViewModel = ViewModelProvider(this, TransferenciaViewModelFactory(ActionsRepository(actionsDAO), UserRepository(userDAO))).get(TransferenciaViewModel::class.java)

        mainViewModel.getValueByuser(contaCorrente)
        mainViewModel.saldo.observe(requireActivity(),{
            val saldo = "R$ ${it}"
            viewBinding.saldo.text = saldo
        })
        viewBinding.tipoConta.text = tipoConta

        viewBinding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        viewBinding.btnTransferencia.setOnClickListener {
            val contaCorrenteValue = viewBinding.inputContaCorrente.text.toString()
            val saldoValue = viewBinding.inputValor.text.toString()
            val descriptionValue = "Transferência de $contaCorrente para $contaCorrenteValue no valor de R$ $saldoValue"
            val action = Actions(
                actionType = "transferencia",
                value = saldoValue.toDouble(),
                date = OffsetDateTime.now(),
                description = descriptionValue,
                actionTo = contaCorrenteValue,
                contaCorrente = contaCorrente
            )
            transferenciaViewModel.insertNewAction(action,tipoConta)
            transferenciaViewModel.errorTransferencia.observe(requireActivity(),{
               Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            })
            activity?.onBackPressed()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTransferenciaBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

}