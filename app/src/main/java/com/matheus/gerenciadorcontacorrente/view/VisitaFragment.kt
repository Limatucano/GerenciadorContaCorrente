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
import com.matheus.gerenciadorcontacorrente.databinding.FragmentDepositoBinding
import com.matheus.gerenciadorcontacorrente.databinding.FragmentVisitaBinding
import com.matheus.gerenciadorcontacorrente.viewmodel.DepositoViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.DepositoViewModelFactory
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
class VisitaFragment : Fragment() {

    lateinit var contaCorrente : String
    private lateinit var viewBinding : FragmentVisitaBinding
    private lateinit var viewModel : DepositoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = VisitaFragmentArgs.fromBundle(bundle)
        contaCorrente = args.contaCorrente
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionsDAO = context?.let { Database.getDatabase(it)?.actionsDAO() }!!
        viewModel = ViewModelProvider(this, DepositoViewModelFactory(ActionsRepository(actionsDAO))).get(
            DepositoViewModel::class.java)

        viewBinding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        viewBinding.btnVisita.setOnClickListener {
            val action = Actions(
                contaCorrente = contaCorrente,
                actionType = "visita",
                value = 50.0,
                date = OffsetDateTime.now(),
                description = "Visita do gerente solicitada",
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
        viewBinding = FragmentVisitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

}