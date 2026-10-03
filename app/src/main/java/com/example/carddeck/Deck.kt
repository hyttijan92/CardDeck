package com.example.carddeck

import kotlin.random.Random

enum class Suit(val symbol: String, val isRed: Boolean) {
    SPADES("♠", false),
    HEARTS("♥", true),
    DIAMONDS("♦", true),
    CLUBS("♣", false),
}

enum class Rank(val label: String) {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
    EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K"),
}

data class Card(val rank: Rank, val suit: Suit) {
    override fun toString() = "${rank.label}${suit.symbol}"
}

/**
 * Immutable state of a deck: cards still to be drawn and the discard pile.
 * The last element of [discardPile] is the most recently drawn card.
 */
data class Deck(
    val drawPile: List<Card>,
    val discardPile: List<Card> = emptyList(),
) {
    val isEmpty: Boolean get() = drawPile.isEmpty()
    val lastDrawn: Card? get() = discardPile.lastOrNull()

    /** Moves the top card of the draw pile to the discard pile. No-op when empty. */
    fun draw(): Deck {
        if (isEmpty) return this
        return Deck(drawPile.drop(1), discardPile + drawPile.first())
    }

    companion object {
        val FULL_DECK: List<Card> = Suit.entries.flatMap { suit -> Rank.entries.map { Card(it, suit) } }

        /** A full 52-card deck, shuffled, with an empty discard pile. */
        fun shuffled(random: Random = Random.Default): Deck = Deck(FULL_DECK.shuffled(random))
    }
}
