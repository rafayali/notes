package com.rafay.notes.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialSharedAxis
import com.rafay.notes.databinding.FragmentSignupBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

    private val viewModel by viewModel<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.textInputDob.setEndIconOnClickListener {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_LONG).show()

            showDatePicker()
        }

        binding.buttonSignUp.setOnClickListener {
            validate()

            viewModel.singUp(
                binding.editFirstName.text.toString(),
                binding.editLastName.text.toString(),
                binding.editDob.text.toString(),
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString()
            )
        }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
    }

    private fun validate() {
        if (binding.editFirstName.text.toString().isBlank()) {
            binding.editFirstName.error = "Required"
        } else {
            binding.editFirstName.error = null
        }

        if (binding.editDob.text.toString().isBlank()) {
            binding.textInputDob.error = "Required"
        } else {
            binding.textInputDob.error = null
        }
    }
}
