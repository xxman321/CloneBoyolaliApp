package com.ydh.budayabyl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentLoginBinding
import com.ydh.budayabyl.viewmodel.UserViewModel
import com.ydh.budayabyl.viewmodel.state.UserState
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val userViewModel by viewModel<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setView()
        setObserver()
        return binding.root
    }

    private fun setView() {
        binding.run {
            btLogToRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

            tvResetPassword.setOnClickListener {
                if (etLoginUserEmail.text.toString().isNotEmpty()) {
                    userViewModel.resetPassword(etLoginUserEmail.text.toString())
                } else {
                    showMessage("please fill your email")
                }

            }

            btLogLogin.setOnClickListener {
                if (etLoginUserEmail.text.toString().isNotEmpty() &&
                    etLoginUserPassword.text.toString().isNotEmpty()
                ) {
                    userViewModel.loginUser(
                        etLoginUserEmail.text.toString(),
                        etLoginUserPassword.text.toString()
                    )
                } else {
                    showMessage("Email dan password tidak boleh kosong")
                }

            }
        }

    }

    private fun setObserver() {
        userViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is UserState.Loading -> {
                    showLoading(true)
//                    session.token = it.data.token
                }
                is UserState.SuccessSendResetPassword -> {
                    showLoading(false)
                    showMessage(it.email)
                }
                is UserState.SuccessLoginUser -> {
                    showLoading(false)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigation2())
                }
                is UserState.Failed -> {
                    showLoading(false)
                    showMessage(it.msg)
                }
                is UserState.Error -> {
                    showLoading(false)
                    showMessage(it.exception.message.toString())
                }
                else -> throw Exception("Unsupported state type")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
            pbSignIn.visibility = if (isLoading) View.VISIBLE else View.GONE
            cvSignIn.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }


}