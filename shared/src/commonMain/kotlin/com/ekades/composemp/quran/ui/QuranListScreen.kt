import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import com.ekades.composemp.quran.domain.model.QuranSurah
import com.ekades.composemp.quran.presentation.quran.QuranIntent
import com.ekades.composemp.quran.presentation.quran.QuranViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranListScreen(viewModel: QuranViewModel, onSurahClick: (Int) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(QuranIntent.LoadSurahList)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val nestedScrollConnection = remember { scrollBehavior.nestedScrollConnection }
    val primaryColor = Color(0xFF0fa1a6)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Al-Qur'an", color = Color.White) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = primaryColor,
                    scrolledContainerColor = primaryColor
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = primaryColor,
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
            ) {
                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    state.error != null -> {
                        Text(
                            text = "Error: ${state.error}",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Red
                        )
                    }

                    else -> {
                        SurahList(state.surahList, onSurahClick)
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahList(surahList: List<QuranSurah>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val primaryColor = Color(0xFF0fa1a6)

        itemsIndexed(surahList) { index, surah ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(surah.nomor) }
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(primaryColor, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = surah.nomor.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = surah.namaLatin,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                            )

                            Text(
                                text = surah.nama,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.Serif
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = surah.arti,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                            Text(
                                text = "${surah.jumlahAyat} Ayat",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
            }
        }
    }
}



