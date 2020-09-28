package com.rafay.notes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rafay.notes.databinding.FragmentOptionsDialogBinding

class OptionsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentOptionsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textTakePhoto.setOnClickListener {
            setFragmentResult(
                KEY_RESULT, bundleOf(
                    KEY_RESULT_SERIALIZABLE_OPTION to Options.TakePhoto
                )
            )
            dismiss()
        }

        binding.textAddImage.setOnClickListener {
            setFragmentResult(
                KEY_RESULT, bundleOf(
                    KEY_RESULT_SERIALIZABLE_OPTION to Options.AddImage
                )
            )
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        (requireDialog() as BottomSheetDialog).dismissWithAnimation = true
        (requireDialog() as BottomSheetDialog).behavior.saveFlags = BottomSheetBehavior.SAVE_ALL
    }

    companion object {
        const val KEY_RESULT = "optionsDialogResult"

        const val KEY_RESULT_SERIALIZABLE_OPTION = "option"
    }

    enum class Options {
        TakePhoto,
        AddImage
    }
}
