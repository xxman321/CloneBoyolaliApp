package com.ydh.budayabyl.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentAuthBinding
import com.ydh.budayabyl.service.FirebaseUserService
import com.ydh.budayabyl.viewmodel.UserViewModel
import com.ydh.budayabyl.viewmodel.state.UserState
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

//    private val service: FirebaseUserService by lazy { FirebaseUserService }
//    private val viewModelFactory by lazy { UserViewModelFactory(service) }
    private val userViewModel by viewModel<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("AIzaSyCXdt5sJDhGstA5MBBe4bQhzLOmCU3WyPw")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        setView()
        setObserver()


        return binding.root
    }

    private fun setView(){
        binding.apply {
            btAuthGoogle.setOnClickListener {
                signIn()
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
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToNavigationHome())
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


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
//            val task =
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//            println(task)
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToNavigationHome())
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToNavigationHome())


        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
//            pbSignIn.visibility = if (isLoading) View.VISIBLE else View.GONE
//            cvSignIn.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }


}