package com.arthurfp.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.arthurfp.challenge.R
import com.arthurfp.challenge.databinding.MainFragmentBinding
import com.arthurfp.challenge.models.GameBinaryTree
import com.arthurfp.challenge.models.Node
import com.arthurfp.challenge.ui.fragments.dialogs.GameDialog
import com.arthurfp.challenge.utils.Enums
import com.arthurfp.challenge.utils.Enums.GameEvent
import com.arthurfp.challenge.viewmodels.GameViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var gameBinaryTree: GameBinaryTree


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.btnRestart.setOnClickListener {
            gameViewModel.restart()
            nextStep()
        }

        nextStep()

        return binding.root
    }

    private fun nextStep() {
        when (gameViewModel.event) {
            GameEvent.START -> showStart()
            GameEvent.QUESTION -> showQuestion()
            GameEvent.WIN -> showWin()
            GameEvent.LOST -> showLost()
        }
    }

    private fun showStart() {
        GameDialog.newInstance(
            getString(R.string.dialog_title),
            getString(R.string.dialog_msg_start),
            getString(R.string.label_ok),
            {
                gameViewModel.start()
                nextStep()
            },
            getString(R.string.label_cancel)
        ).show(requireActivity().supportFragmentManager, GameDialog.TAG)

    }

    private fun showQuestion() {

        val msg = when(gameViewModel.currentNode) {
            is Node.Question -> getString(R.string.dialog_msg_question_trait, gameViewModel.currentNode.data)
            is Node.Animal -> getString(R.string.dialog_msg_question_animal, gameViewModel.currentNode.data)
            else -> ""
        }

        GameDialog.newInstance(
            getString(R.string.dialog_title),
            msg,
            getString(R.string.label_yes),
            {
                gameViewModel.next(Enums.Answer.YES)
                nextStep()
            },
            getString(R.string.label_no),
            {
                gameViewModel.next(Enums.Answer.NO)
                nextStep()
            }

        ).show(requireActivity().supportFragmentManager, GameDialog.TAG)
    }

    private fun showWin() {
        GameDialog.newInstance(
            getString(R.string.dialog_title),
            getString(R.string.dialog_msg_win),
            getString(R.string.label_ok),
            {
                gameViewModel.restart()
                // Auto restart
                nextStep()
            }
        ).show(requireActivity().supportFragmentManager, GameDialog.TAG)
    }


    private fun showLost() {

        val lostFn = { animal: String ->

            GameDialog.newInstance(
                getString(R.string.dialog_title),
                getString(R.string.dialog_msg_input_trait, animal, gameViewModel.currentNode.data),
                getString(R.string.label_ok),

                { trait: String ->
                    gameViewModel.insertData(trait, animal)
                    gameViewModel.restart()
                    // Auto restart
                    nextStep()
                },

                null,
                null,
                true
            ).show(requireActivity().supportFragmentManager, GameDialog.TAG)
        }

        GameDialog.newInstance(
            getString(R.string.dialog_title),
            getString(R.string.dialog_msg_input_animal),
            getString(R.string.label_ok),
            lostFn,
            null,
            null,
            true
        ).show(requireActivity().supportFragmentManager, GameDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks
        _binding = null
    }


}