package com.example.carddeck

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class DeckTest {
    @Test
    fun newDeckHas52UniqueCardsAndEmptyDiscard() {
        val deck = Deck.shuffled(Random(1))
        assertEquals(52, deck.drawPile.size)
        assertEquals(52, deck.drawPile.toSet().size)
        assertTrue(deck.discardPile.isEmpty())
        assertNull(deck.lastDrawn)
    }

    @Test
    fun drawingMovesTopCardToDiscard() {
        val deck = Deck.shuffled(Random(2))
        val top = deck.drawPile.first()
        val after = deck.draw()
        assertEquals(51, after.drawPile.size)
        assertEquals(listOf(top), after.discardPile)
        assertEquals(top, after.lastDrawn)
        assertTrue(top !in after.drawPile)
    }

    @Test
    fun drawingWholeDeckNeverRepeatsACard() {
        var deck = Deck.shuffled(Random(3))
        repeat(52) { deck = deck.draw() }
        assertTrue(deck.isEmpty)
        assertEquals(52, deck.discardPile.toSet().size)
        assertEquals(Deck.FULL_DECK.toSet(), deck.discardPile.toSet())
    }

    @Test
    fun drawingFromEmptyDeckIsNoOp() {
        var deck = Deck.shuffled(Random(4))
        repeat(52) { deck = deck.draw() }
        assertSame(deck, deck.draw())
    }

    @Test
    fun shuffleProducesDifferentOrders() {
        assertTrue(Deck.shuffled(Random(5)).drawPile != Deck.shuffled(Random(6)).drawPile)
    }
}
