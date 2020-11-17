package com.rafay.notes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.rafay.notes.R
import com.rafay.notes.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            viewModel.login(binding.editEmail.text.toString(), binding.editPassword.text.toString())
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.signInState.collect { enabled ->
                binding.buttonLogin.isEnabled = !enabled
                binding.buttonRegister.isEnabled = !enabled
                binding.editEmail.isEnabled = !enabled
                binding.editPassword.isEnabled = !enabled
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.message.collect {
                when (it) {
                    LoginViewModel.Message.BadRequest -> {
                        Timber.d("BadRequest")
                        Toast.makeText(
                            requireContext(),
                            "Invalid email and password",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LoginViewModel.Message.GenericError -> {
                        Toast.makeText(
                            requireContext(),
                            "Unable to login, please try again later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.navigation.collect {
                when (it) {
                    LoginViewModel.Navigation.Login -> {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }
}