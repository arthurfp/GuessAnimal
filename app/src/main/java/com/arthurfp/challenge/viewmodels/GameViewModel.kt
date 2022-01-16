package com.arthurfp.challenge.viewmodels

import androidx.lifecycle.ViewModel
import com.arthurfp.challenge.models.GameBinaryTree
import com.arthurfp.challenge.utils.Enums.Answer
import com.arthurfp.challenge.utils.Enums.GameEvent

class GameViewModel : ViewModel() {

    private val gameBT = GameBinaryTree()
    val event get() = gameBT.event
    val currentNode get() = gameBT.currentNode

    fun start() {
        gameBT.event = GameEvent.QUESTION
    }

    fun next(answer: Answer) {
        gameBT.answer(answer)
    }

    fun restart() {
        gameBT.restart()
    }

    fun insertData(trait: String, animal: String) {
        gameBT.insertNode(trait, animal)
    }
}