package com.example.carddeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DeckScreen()
            }
        }
    }
}

private val TableGreen = Color(0xFF1B5E20)
private val CardBackBlue = Color(0xFF1A237E)
private val SuitRed = Color(0xFFC62828)

@Composable
fun DeckScreen(viewModel: DeckViewModel = viewModel()) {
    val deck = viewModel.deck
    Scaffold(containerColor = TableGreen) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PileColumn(label = "Deck: ${deck.drawPile.size}") {
                    if (deck.isEmpty) EmptySlot("Empty") else CardBack(onClick = viewModel::draw)
                }
                PileColumn(label = "Discard: ${deck.discardPile.size}") {
                    val card = deck.lastDrawn
                    if (card == null) EmptySlot("No cards\npicked") else CardFace(card)
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = viewModel::draw, enabled = !deck.isEmpty) {
                    Text("Pick card")
                }
                OutlinedButton(
                    onClick = viewModel::resetAndShuffle,
                    border = BorderStroke(1.dp, Color.White),
                ) {
                    Text("Reset & shuffle", color = Color.White)
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = if (deck.discardPile.isEmpty()) "Tap the deck to pick a card" else "Discard pile (latest first)",
                color = Color.White,
            )
            Spacer(Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(48.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(deck.discardPile.asReversed(), key = { it.toString() }) { card ->
                    SmallCard(card)
                }
            }
        }
    }
}

@Composable
private fun PileColumn(label: String, content: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        content()
        Spacer(Modifier.height(8.dp))
        Text(label, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

private val BigCardModifier = Modifier.size(width = 120.dp, height = 170.dp)

@Composable
private fun CardBack(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = BigCardModifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackBlue),
        border = BorderStroke(4.dp, Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("♠♥\n♦♣", color = Color.White.copy(alpha = 0.6f), fontSize = 32.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun CardFace(card: Card) {
    val color = if (card.suit.isRed) SuitRed else Color.Black
    Card(
        modifier = BigCardModifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Box(Modifier.fillMaxSize().padding(8.dp)) {
            Text(
                "${card.rank.label}\n${card.suit.symbol}",
                color = color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                modifier = Modifier.align(Alignment.TopStart),
            )
            Text(card.suit.symbol, color = color, fontSize = 56.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun EmptySlot(text: String) {
    Card(
        modifier = BigCardModifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)),
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text, color = Color.White.copy(alpha = 0.7f), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun SmallCard(card: Card) {
    Card(
        modifier = Modifier.size(width = 48.dp, height = 64.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                card.toString(),
                color = if (card.suit.isRed) SuitRed else Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
