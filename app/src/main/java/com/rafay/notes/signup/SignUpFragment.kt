package com.rafay.notes.signup

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.PixelSize
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialSharedAxis
import com.rafay.notes.BuildConfig
import com.rafay.notes.R
import com.rafay.notes.databinding.FragmentSignupBinding
import com.rafay.notes.ktx.ErrorMessage
import com.rafay.notes.ktx.longToast
import com.rafay.notes.util.isEmail
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

    private val viewModel by viewModel<SignUpViewModel>()

    private val launchTakePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.imageView.load(uri) {
            crossfade(true)
            size(PixelSize(128, 128))
        }
    }

    private val launchCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                File(
                    requireContext().cacheDir,
                    "${System.currentTimeMillis()}.jpg"
                ).also { file ->
                    uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${BuildConfig.APPLICATION_ID}.fileprovider",
                        file
                    ).also { fileUri -> launchTakePhoto.launch(fileUri) }
                }
            }
        }

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.textInputDob.setEndIconOnClickListener {
            showDatePicker()
        }

        binding.buttonSignUp.setOnClickListener {
            if (!validate()) {
                viewModel.singUp(
                    binding.editFirstName.text.toString(),
                    binding.editLastName.text.toString(),
                    binding.editDob.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editPassword.text.toString()
                )
            }
        }

        binding.imageTakePhoto.setOnClickListener {
            launchCameraPermission.launch(Manifest.permission.CAMERA)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.message.collect {
                when (it) {
                    ErrorMessage.BadRequest -> longToast("Bad Request")
                    ErrorMessage.GenericError -> longToast("Error signing up, please try again")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { event ->
                event?.consume()?.let { navigation ->
                    when (navigation) {
                        SignUpViewModel.Navigation.Home -> {
                            findNavController().navigate(R.id.action_signUpFragment_to_nav_graph)
                        }
                    }
                }
            }
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        datePicker.addOnPositiveButtonClickListener { epoc ->
            val date = Instant.ofEpochMilli(epoc).atZone(ZoneId.systemDefault()).toLocalDate()
            binding.editDob.setText(date.format(DateTimeFormatter.ISO_DATE))
            binding.textInputDob.error = null
        }

        datePicker.show(childFragmentManager, "DobPicker")
    }

    private fun validate(): Boolean {
        var errors = 5

        if (binding.editFirstName.text.toString().isBlank()) {
            binding.textInputFirstName.error = "Required"
        } else {
            binding.textInputFirstName.error = null
            errors--
        }

        if (binding.editLastName.text.toString().isBlank()) {
            binding.textInputLastName.error = "Required"
        } else {
            binding.textInputLastName.error = null
            errors--
        }

        if (binding.editDob.text.toString().isBlank()) {
            binding.textInputDob.error = "Required"
        } else {
            binding.textInputDob.error = null
            errors--
        }

        if (!binding.editEmail.text.toString().isEmail()) {
            binding.textInputEmail.error = "Required"
        } else {
            binding.textInputEmail.error = null
            errors--
        }

        if (binding.editPassword.text.toString().isBlank()) {
            binding.textInputPassword.error = "Required"
        } else {
            binding.textInputPassword.error = null
            errors--
        }

        return errors > 0
    }
}
