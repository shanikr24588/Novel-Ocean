package com.shani.novelocean.presentation.Book_Detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.shani.novelocean.domain.model.BookDetail
import com.shani.novelocean.presentation.Home_Screen.decodeBase64ToBitmap

@Composable
fun BookDetailContent(
    book:BookDetail,
    onChapterClick:(String, Int) -> Unit,
    modifier: Modifier = Modifier
){

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        item {
            Row(
                modifier = modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.coverImage.decodeBase64ToBitmap())
                            .crossfade(true)
                            .build(),
                        contentDescription = "Cover of ${book.title}",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "By ${book.author}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row{
                        Text(
                            text = book.category,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp)
                            ).padding(horizontal = 8.dp, vertical = 4.dp)

                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = book.language,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp)
                            ).padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${book.totalPage} pages",
                        style = MaterialTheme.typography.bodySmall

                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Chapter",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

          items(book.chapter){chapter->
              ChapterItem(
                  chapter = chapter,
                  onClick = {
                      onChapterClick(chapter.id, chapter.chapterNumber)
                  }
              )

              Divider()

          }

    }
}