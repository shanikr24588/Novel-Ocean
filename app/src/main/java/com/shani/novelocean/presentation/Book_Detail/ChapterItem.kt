package com.shani.novelocean.presentation.Book_Detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shani.novelocean.domain.model.Chapter

@Composable
fun ChapterItem(
    chapter: Chapter,
    onClick:()-> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            onClick()
        }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Chapter ${chapter.chapterNumber}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium

        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = chapter.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}