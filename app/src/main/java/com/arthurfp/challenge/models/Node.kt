package com.arthurfp.challenge.models


abstract class Node {
    abstract var data: String

    data class Question(
        override var data: String, // The trait to question
        var left: Node?, // Answer No
        var right: Node?// Answer Yes
    ) : Node()

   data class Animal(
       var parent: Question?, // The name of the animal
       override var data: String // The name of the animal
    ) : Node()
}
