package com.matheus.gerenciadorcontacorrente.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Database
import com.matheus.gerenciadorcontacorrente.data.localstore.User
import com.matheus.gerenciadorcontacorrente.data.repository.UserRepository
import com.matheus.gerenciadorcontacorrente.databinding.FragmentLoginBinding
import com.matheus.gerenciadorcontacorrente.viewmodel.loginViewModel
import com.matheus.gerenciadorcontacorrente.viewmodel.loginViewModelFactory
import java.net.HttpURLConnection
import kotlin.properties.Delegates

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class loginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding : FragmentLoginBinding
    lateinit var viewModel : loginViewModel
    var loginStatusCode : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var direction : NavDirections? = null
        val userDAO = context?.let { Database.getDatabase(it)?.userDAO() }!!
        viewModel = ViewModelProvider(this, loginViewModelFactory(UserRepository(userDAO))).get(loginViewModel::class.java)

        val matheus = User(
            contaCorrente = "12345",
            nome = "Matheus Lima",
            senha = "1234",
            tipoConta = "VIP",
        )

        val karin = User(
            contaCorrente = "11111",
            nome = "Karin",
            senha = "1234",
            tipoConta = "COMUM"
        )
        viewModel.insertNewUser(matheus)
        viewModel.insertNewUser(karin)

        viewBinding.btnLogin.setOnClickListener {
            var inputContaCorrente = viewBinding.inputContaCorrente.text
            var inputPass = viewBinding.inputPass.text
            if(!inputContaCorrente.isNullOrBlank() && !inputPass.isNullOrBlank()){
                viewModel.getUserByContaCorrente(inputContaCorrente.toString(), inputPass.toString())

                viewModel.userLogado.observe(requireActivity(),{
                    direction = loginFragmentDirections.actionLoginFragmentToMainFragment(it.tipoConta, it.nome, it.contaCorrente)
                })
                viewModel.loginStatusCode.observe(requireActivity(), Observer { statusCode ->
                    if(statusCode == HttpURLConnection.HTTP_OK){
                        if(direction !== null){
                            view.findNavController().navigate(direction!!)
                            return@Observer
                        }
                        view.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)


                    }else{
                        Toast.makeText(requireContext(), "Erro de autenticação", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{123
                Toast.makeText(requireContext(), "Necessário preencher os dois campos corretamente", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = FragmentLoginBinding.inflate(inflater,container, false)
        return viewBinding.root
    }

    companion object {

        @JvmStatic fun newInstance(param1: String, param2: String) =
                loginFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}