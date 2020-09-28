package com.rafay.notes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rafay.notes.databinding.FragmentOptionsDialogBinding

typealias OnOptionSelected = (option: OptionsDialog.Options) -> Unit

class OptionsDialog(private val onOptionSelected: OnOptionSelected) : BottomSheetDialogFragment(){

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
            onOptionSelected.invoke(Options.TakePhoto)
            dismiss()
        }

        binding.textAddImage.setOnClickListener {
            onOptionSelected.invoke(Options.AddImage)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        (requireDialog() as BottomSheetDialog).dismissWithAnimation = true
        (requireDialog() as BottomSheetDialog).behavior.saveFlags = BottomSheetBehavior.SAVE_ALL
    }

    enum class Options{
        TakePhoto,
        AddImage
    }
}