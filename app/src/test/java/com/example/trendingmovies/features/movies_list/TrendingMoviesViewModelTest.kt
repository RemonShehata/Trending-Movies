package com.example.trendingmovies.features.movies_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trendingmovies.core.source.repos.ConfigurationRepo
import com.example.trendingmovies.core.source.repos.TrendingMoviesRepo
import com.example.trendingmovies.util.TestCoroutineRule
import com.example.trendingmovies.utils.NetworkStateMonitor
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule


internal class TrendingMoviesViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var trendingMoviesViewModel: TrendingMoviesViewModel

    @MockK
    private lateinit var trendingMoviesRepo: TrendingMoviesRepo

    @MockK
    private lateinit var configurationRepo: ConfigurationRepo

    @MockK
    private lateinit var networkStateMonitor: NetworkStateMonitor

    private val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        trendingMoviesViewModel = TrendingMoviesViewModel(
            trendingMoviesRepo, configurationRepo,
            networkStateMonitor, testDispatcher
        )
        // https://stackoverflow.com/questions/44382540/mocking-extension-function-in-kotlin
//        mockkStatic("com.example.newsapp.utils.ExtentionsUtilKt")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}