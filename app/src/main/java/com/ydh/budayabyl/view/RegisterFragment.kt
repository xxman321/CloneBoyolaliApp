package com.ydh.budayabyl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ydh.budayabyl.databinding.FragmentRegisterBinding
import com.ydh.budayabyl.viewmodel.UserViewModel
import com.ydh.budayabyl.viewmodel.state.UserState
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setView()
        setObserver()
        return binding.root
    }

    private fun setView(){
        binding.run {
            btRegRegister.setOnClickListener {
                if (etRegisterUserEmail.text.toString().isNotEmpty() &&
                    etRegisterUserPassword.text.toString().isNotEmpty()
                ) {
                    userViewModel.registerUser(
                        etRegisterUserEmail.text.toString(),
                        etRegisterUserPassword.text.toString(),
                        etRegisterUserName.text.toString()
                    )

                } else {
                    showMessage("Email dan password tidak boleh kosong")
                }
            }
        }
    }

    private fun setObserver(){
        userViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is UserState.Loading -> {
                    showLoading(true)
//                    session.token = it.data.token
                }
                is UserState.SuccessRegisterUser -> {
                    showLoading(false)
                    findNavController().navigateUp()
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
            pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
            cvRegister.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }



}