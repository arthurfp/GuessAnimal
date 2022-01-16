package com.arthurfp.challenge.ui.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.arthurfp.challenge.databinding.GameDialogFragmentBinding
import com.arthurfp.challenge.viewmodels.GameViewModel

class GameDialog : DialogFragment() {

    private val gameViewModel: GameViewModel by activityViewModels()

    companion object {

        const val TAG = "GameDialog"

        private var _binding: GameDialogFragmentBinding? = null
        private val binding get() = _binding!!

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_MSG = "KEY_MSG"
        private const val KEY_POSITIVE_BUTTON = "KEY_POSITIVE_BUTTON"
        private const val KEY_NEGATIVE_BUTTON = "KEY_NEGATIVE_BUTTON"
        private const val KEY_INPUT_ON = "KEY_INPUT_ON"

        lateinit var callbackPositive: (inputValue: String) -> Unit
        var callbackNegative: (() -> Unit)? = null

        fun newInstance(
            title: String,
            msg: String,
            positive_label: String,
            callbackPositiveListener: (inputValue: String) -> Unit,
            negative_label: String? = null,
            callbackNegativeListener: (() -> Unit)? = null,
            input: Boolean = false,
        ): GameDialog {

            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_MSG, msg)
            args.putString(KEY_POSITIVE_BUTTON, positive_label)
            args.putString(KEY_NEGATIVE_BUTTON, negative_label)
            args.putBoolean(KEY_INPUT_ON, input)

            callbackPositive = callbackPositiveListener
            callbackNegative = callbackNegativeListener

            val fragment = GameDialog()
            fragment.isCancelable = false
            fragment.arguments = args
            return fragment

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameDialogFragmentBinding.inflate(inflater, container, false)

        setupView()
        setupClickListeners()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView() {
        binding.dialogTitle.text = arguments?.getString(KEY_TITLE)
        binding.dialogMsg.text = arguments?.getString(KEY_MSG)
        binding.btnPositive.text = arguments?.getString(KEY_POSITIVE_BUTTON)

        val btnNegativeLabel = arguments?.getString(KEY_NEGATIVE_BUTTON)

        if (btnNegativeLabel != null) {
            binding.inputAnswer.visibility = View.VISIBLE
            binding.btnNegative.text = btnNegativeLabel
        } else {
            binding.btnNegative.visibility = View.GONE
        }

        val inputOn = arguments?.getBoolean(KEY_INPUT_ON)

        if (inputOn == true) {
            binding.inputAnswer.visibility = View.VISIBLE
        } else {
            binding.inputAnswer.visibility = View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.btnPositive.setOnClickListener {

            val inputValue = binding.inputAnswer.text.toString().trim()

            callbackPositive(inputValue)
            dismiss()
        }
        binding.btnNegative.setOnClickListener {
            callbackNegative?.let{ it() }
            dismiss()
        }
    }
}