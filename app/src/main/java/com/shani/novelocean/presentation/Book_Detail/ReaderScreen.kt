package com.shani.novelocean.presentation.Book_Detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shani.novelocean.data.remote.BookApiService
import com.shani.novelocean.domain.UseCase.GetBookDetailsUseCase
import com.shani.novelocean.domain.UseCase.GetChapterUseCase
import com.shani.novelocean.domain.repository.BookRepository


//@Preview(showBackground = true)
//@Composable
//fun PreviewReaderScreen(getBookDetailsUseCase: GetBookDetailsUseCase, getChapterUseCase: GetChapterUseCase) {
//    ReaderScreen(
//        bookId = "book123",
//        chapterNumber = 1,
//        viewModel = BookDetailViewModel(getBookDetailsUseCase, getChapterUseCase), // Mock ViewModel, ideally use a FakeViewModel for testing
//        onBackClick = { /* Handle back */ },
//        onNextChapter = { _, _ -> /* Handle next chapter */ },
//        onPreviousChapter = { _, _ -> /* Handle previous chapter */ }
//    )
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    bookId:String,
    chapterNumber: Int,
    viewModel: BookDetailViewModel =  viewModel(factory = BookDetailViewModelFactory()),
    onBackClick: () -> Unit,
    onNextChapter:(String, Int)-> Unit,
    onPreviousChapter:(String, Int) -> Unit
    ){

//    val viewModel = BookDetailViewModel(
//        GetBookDetailsUseCase(BookRepository(BookApiService()), GetChapterUseCase(BookRepository(BookApiService())))
//    )


    LaunchedEffect(bookId, chapterNumber) {
        viewModel.loadBookDetail(bookId)
        viewModel.loadChapter(bookId, chapterNumber)
    }

    val bookState by viewModel.bookState.collectAsState()
    val chapterState by viewModel.currentChapter.collectAsState()

    val book = (bookState as? BookDetailState.Success) ?.book
    val totalChapters = book?.chapter?.size ?:0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chapter $chapterNumber" + (book?.let { " -${it.title}" }?: ""),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                            if (chapterNumber > 1){
                                onPreviousChapter(bookId, chapterNumber -1)
                            }
                        },
                        enabled = chapterNumber > 1
                    ) {
                        Text(text = "Previous")
                    }

                    Text(
                        text = "$chapterNumber/ $totalChapters",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Button(
                        onClick = {
                            if (totalChapters > 0 && chapterNumber < totalChapters){
                                onNextChapter(bookId, chapterNumber + 1)
                            }
                        }
                    ) {
                        Text(
                            text = "Next"
                        )
                    }
                }
            }
        }
    ){paddingValues ->
        when(val state = chapterState){
            is ChapterState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ChapterState.Success -> {
                val chapter = state.chapter
                ReaderContent(
                    chapter,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is ChapterState.Error -> {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message, color = MaterialTheme.colorScheme.tertiary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.loadChapter(bookId, chapterNumber)
                            }
                        ) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        }

    }
}

