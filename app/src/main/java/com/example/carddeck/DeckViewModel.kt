package com.example.carddeck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DeckViewModel : ViewModel() {
    var deck by mutableStateOf(Deck.shuffled())
        private set

    fun draw() {
        deck = deck.draw()
    }

    fun resetAndShuffle() {
        deck = Deck.shuffled()
    }
}
