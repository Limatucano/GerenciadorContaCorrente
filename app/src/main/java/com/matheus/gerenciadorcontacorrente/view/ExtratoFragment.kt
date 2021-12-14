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
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import com.matheus.gerenciadorcontacorrente.data.localstore.Database
import com.matheus.gerenciadorcontacorrente.data.repository.ActionsRepository
import com.matheus.gerenciadorcontacorrente.databinding.FragmentExtratoBinding
import com.matheus.gerenciadorcontacorrente.view.adapter.ExtratoAdapter
import com.matheus.gerenciadorcontacorrente.viewmodel.ExtratoViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.ExtratoViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
class ExtratoFragment : Fragment() , ExtratoAdapter.OnClickItemListener{
    private lateinit var viewBinding : FragmentExtratoBinding
    private lateinit var viewModel : ExtratoViewModel
    lateinit var contaCorrente : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Bundle", "Erro ao transferir dados")
            return
        }
        val args = ExtratoFragmentArgs.fromBundle(bundle)
        contaCorrente = args.contaCorrente
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionsDAO = context?.let { Database.getDatabase(it)?.actionsDAO() }!!
        viewBinding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        viewModel = ViewModelProvider(this,ExtratoViewModelFactory(ActionsRepository(actionsDAO))).get(ExtratoViewModel::class.java)

        viewModel.getExtrato(contaCorrente)
        val layoutManager = LinearLayoutManager(context)
        viewModel.extrato.observe(requireActivity(),{
            viewBinding.rvExtratos.layoutManager = layoutManager
            viewBinding.rvExtratos.adapter = ExtratoAdapter(it,this)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentExtratoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onItemClick(items: Actions, position: Int) {
        Log.d("Clique", items.toString())
    }

}