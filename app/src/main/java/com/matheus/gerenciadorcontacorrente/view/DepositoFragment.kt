package com.matheus.gerenciadorcontacorrente.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.Database
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import com.matheus.gerenciadorcontacorrente.databinding.FragmentDepositoBinding
import com.matheus.gerenciadorcontacorrente.databinding.FragmentMainBinding
import com.matheus.gerenciadorcontacorrente.viewmodel.*
import java.time.OffsetDateTime

class DepositoFragment : Fragment() {
    private var tipoConta : String? = null
    lateinit var contaCorrente : String
    private var nome : String? = null
    private lateinit var viewBinding : FragmentDepositoBinding
    private lateinit var viewModel : DepositoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = DepositoFragmentArgs.fromBundle(bundle)
        tipoConta = args.tipoConta
        contaCorrente = args.contaCorrente
        nome = args.nome
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionsDAO = context?.let { Database.getDatabase(it)?.actionsDAO() }!!
        viewModel = ViewModelProvider(this, DepositoViewModelFactory(ActionsRepository(actionsDAO))).get(
            DepositoViewModel::class.java)

        viewBinding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        viewBinding.btnDepositar.setOnClickListener{
            val valueField = viewBinding.inputValor.text.toString().toDouble()
            val descriptionField = viewBinding.inputDescription.text.toString()
            val action = Actions(
                contaCorrente = contaCorrente,
                actionType = "deposito",
                value = valueField,
                date = OffsetDateTime.now(),
                description = descriptionField,
                actionTo = "",
            )
            viewModel.insertNewAction(action)
            activity?.onBackPressed()


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDepositoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

}