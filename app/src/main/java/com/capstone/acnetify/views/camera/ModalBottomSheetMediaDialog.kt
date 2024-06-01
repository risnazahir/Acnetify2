package com.capstone.acnetify.views.camera

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.acnetify.databinding.ItemBottomsheetMediaBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetMediaDialog : BottomSheetDialogFragment() {

    private lateinit var binding: ItemBottomsheetMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemBottomsheetMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // used to show the bottom sheet dialog
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog

            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutCamera.setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }

        binding.layoutGallery.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        val TAG: String = ModalBottomSheetMediaDialog::class.java.simpleName
    }
}