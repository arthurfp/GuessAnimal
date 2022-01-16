package com.arthurfp.challenge.models

import com.arthurfp.challenge.utils.Enums.Answer
import com.arthurfp.challenge.utils.Enums.Answer.NO
import com.arthurfp.challenge.utils.Enums.Answer.YES
import com.arthurfp.challenge.utils.Enums.GameEvent

class GameBinaryTree {

    private val root: Node = defaultNode()

    var currentNode = root
    var event: GameEvent = GameEvent.START

    fun answer(answer: Answer) {
        when(answer) {
            YES -> {
                when (currentNode) {
                    is Node.Question -> answerQuestion((currentNode as Node.Question).right)
                    is Node.Animal -> win()
                }
            }
            NO -> {
                when (currentNode) {
                    is Node.Question -> answerQuestion((currentNode as Node.Question).left)
                    is Node.Animal -> lose()
                }
            }
        }
    }

    private fun answerQuestion(node: Node?) {
        node?.let{
            currentNode = node
            event = GameEvent.QUESTION
        }
    }

    private fun win() {
        event = GameEvent.WIN
    }

    private fun lose() {
        event = GameEvent.LOST
    }

    fun restart() {
        currentNode = root
        event = GameEvent.START
    }

    fun insertNode(trait: String, animal: String) {
        val newQuestion = Node.Question(trait, currentNode, null)
        val newAnimal = Node.Animal(newQuestion, animal)
        newQuestion.right = newAnimal

        val parent = (currentNode as Node.Animal).parent

        if(parent?.left == currentNode) {
            parent?.left = newQuestion
        } else {
            parent?.right = newQuestion
        }


    }
}

fun defaultNode() : Node.Question {
    val leftAnimal = Node.Animal(parent = null, data = "monkey")
    val rightAnimal = Node.Animal(parent = null, data = "shark")
    val defaultQuestion =Node.Question(data = "lives in water", left = leftAnimal, right = rightAnimal)

    leftAnimal.parent = defaultQuestion
    rightAnimal.parent = defaultQuestion

    return defaultQuestion;
}
