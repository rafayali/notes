package com.rafay.notes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rafay.notes.R
import com.rafay.notes.databinding.FragmentColorsDialogBinding

typealias OnColorSelected = (colorHex: String) -> Unit

class ColorsDialog(private val onColorSelected: OnColorSelected) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentColorsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardColorBlue.setOnClickListener {
            Integer.toHexString(ContextCompat.getColor(requireContext(), R.color.card_blue_500))
                .also { colorString ->
                    onColorSelected.invoke(colorString)
                }
            dismiss()
        }

        binding.cardColorGreen.setOnClickListener {
            Integer.toHexString(ContextCompat.getColor(requireContext(), R.color.card_green_500))
                .also { colorString ->
                    onColorSelected.invoke(colorString)
                }
            dismiss()
        }

        binding.cardColorOrange.setOnClickListener {
            Integer.toHexString(ContextCompat.getColor(requireContext(), R.color.card_orange_500))
                .also { colorString ->
                    onColorSelected.invoke(colorString)
                }
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        (dialog as BottomSheetDialog).dismissWithAnimation = true
        (dialog as BottomSheetDialog).behavior.saveFlags = BottomSheetBehavior.SAVE_ALL
    }
}
