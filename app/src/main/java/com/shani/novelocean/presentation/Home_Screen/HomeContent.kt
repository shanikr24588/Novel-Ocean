package com.shani.novelocean.presentation.Home_Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shani.novelocean.domain.model.Book

@Composable
fun HomeContent(
    books: List<Book>,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val category = books.groupBy { it.category }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Weekly Featured",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            BookGrid(
                books = books.take(8),
                onBookClick = onBookClick
            )
        }

        category.forEach{(category, categoryBooks)->
            item {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                BookGrid(
                    books = categoryBooks.take(4),
                    onBookClick = onBookClick
                )
            }

        }

        item {
            Text(
                text = "New Arrivals",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            BookGrid(
                books = books.sortedByDescending { it.UploadDate }.take(4),
                onBookClick = onBookClick
            )
        }
    }

}


@Composable
fun BookGrid(
    books: List<Book>,
    onBookClick:(String)-> Unit,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(320.dp)
    ) {
        items(books){book->
            BookItem(book = book, onClick = {onBookClick(book.id)})

        }
    }
}