package com.rafay.notes.create

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialContainerTransform
import com.rafay.notes.common.emptyString
import com.rafay.notes.databinding.FragmentCreateNoteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    private val viewModel by viewModel<CreateNoteViewModel> { parametersOf(arguments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.title.collect {
                if (!binding.editTitle.text.contentEquals(it)){
                    binding.editTitle.setText(it ?: emptyString)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notes.collect {
                if (!binding.editDescription.text.contentEquals(it)) {
                    binding.editDescription.setText(it ?: emptyString)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.color.collect {
                val color = if (it == null) {
                    android.R.color.transparent
                } else {
                    Color.parseColor("#$it")
                }
                binding.cardSelectColor.setCardBackgroundColor(color)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        childFragmentManager.setFragmentResultListener(
            OptionsDialog.KEY_RESULT,
            this
        ) { _, bundle ->
            (bundle.getSerializable(OptionsDialog.KEY_RESULT_SERIALIZABLE_OPTION) as OptionsDialog.Options).also { option ->
                when (option) {
                    OptionsDialog.Options.TakePhoto -> {
                    }
                    OptionsDialog.Options.AddImage -> {
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.sync()
                    findNavController().popBackStack()
                }
            }
        )
    }

    private fun initView() {
        requireArguments().getString(KEY_STRING_TRANSITION_NAME)?.also {
            binding.root.transitionName = it
        }

        binding.toolbar.setNavigationOnClickListener {
            viewModel.sync()
            findNavController().navigateUp()
        }

        binding.editTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitle(text.toString())
        }

        binding.editDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.setNotes(text.toString())
        }

        binding.cardSelectColor.setOnClickListener {
            ColorsDialog { color ->
                viewModel.setColor(color)
            }.show(childFragmentManager, ColorsDialog::class.java.simpleName)
        }

        binding.buttonOptions.setOnClickListener {
            OptionsDialog().show(childFragmentManager, OptionsDialog::class.java.simpleName)
        }
    }

    companion object {
        const val KEY_LONG_ID = "id"
        const val KEY_STRING_TITLE = "title"
        const val KEY_STRING_DESCRIPTION = "description"
        const val KEY_STRING_BG_COLOR_HEX = "bgColor"

        const val KEY_STRING_TRANSITION_NAME = "transitionName"
    }
}
