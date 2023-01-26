package com.example.trendingmovies.features.movies_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.trendingmovies.State
import com.example.trendingmovies.core.models.TrendingMoviesDto
import com.example.trendingmovies.core.source.local.models.ConfigurationEntity
import com.example.trendingmovies.core.source.repos.ConfigurationRepo
import com.example.trendingmovies.core.source.repos.TrendingMoviesRepo
import com.example.trendingmovies.util.TestCoroutineRule
import com.example.trendingmovies.util.getOrAwaitValue
import com.example.trendingmovies.utils.NetworkState
import com.example.trendingmovies.utils.NetworkStateMonitor
import io.mockk.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@OptIn(ExperimentalCoroutinesApi::class)
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

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
//        trendingMoviesViewModel = TrendingMoviesViewModel(
//            trendingMoviesRepo, configurationRepo,
//            networkStateMonitor, testDispatcher
//        )
        // https://stackoverflow.com/questions/36787449/how-to-mock-method-e-in-log
//        mockkStatic(Log::class)
//        every { Log.v(any(), any()) } returns 0
//        every { Log.d(any(), any()) } returns 0
//        every { Log.i(any(), any()) } returns 0
//        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test viewmodel init`() = testCoroutineRule.runBlockingTest {
        // GIVEN
        val networkMutableStateFlow = MutableStateFlow(NetworkState.Connected)
        every { trendingMoviesRepo.getAllMoviesFlow() } returns flowOf(emptyList())
        coEvery { trendingMoviesRepo.getMoviesForPage() } returns Unit
        coEvery { configurationRepo.getConfiguration() } returns getConfigurationEntity()
        networkMutableStateFlow.emit(NetworkState.Connected)
        every { networkStateMonitor.networkStateFlow } returns networkMutableStateFlow

        // WHEN
        testCoroutineRule.pauseDispatcher()
        trendingMoviesViewModel = TrendingMoviesViewModel(
            trendingMoviesRepo, configurationRepo,
            networkStateMonitor, Dispatchers.Main
        )

        // THEN
//        val observer = mockk<Observer<State<List<TrendingMoviesDto>>> { every { onChanged(any()) } just Runs }
//        val x = mockk<Observer<State<List<TrendingMoviesDto>>>>()
//        every { x.onChanged(any()) } just Runs


        val result = trendingMoviesViewModel.moviesLiveData.getOrAwaitValue()
        assert(result is State.Loading)
        testCoroutineRule.resumeDispatcher()
//        val result2 = trendingMoviesViewModel.moviesLiveData.getOrAwaitValue()
//        assert(result2 is State.Success)

    }

    @Test
    fun `given page table is empty when getMoviesForPage is called, `() = runTest {

        every { trendingMoviesRepo.getAllMoviesFlow() } returns flowOf(emptyList())
        coEvery { configurationRepo.getConfiguration() } returns getConfigurationEntity()

        trendingMoviesViewModel = TrendingMoviesViewModel(
            trendingMoviesRepo, configurationRepo,
            networkStateMonitor, testDispatcher
        )

        trendingMoviesViewModel.getNextPageData()


        val result = trendingMoviesViewModel.moviesLiveData.getOrAwaitValue()
        assert(result is State.Loading)
    }

    private fun getConfigurationEntity(): ConfigurationEntity {
        return ConfigurationEntity(
            baseUrl = "",
            secureBaseUrl = "",
            backdropSizes = emptyList(),
            logoSizes = emptyList(),
            posterSizes = emptyList(),
            profileSizes = emptyList(),
            stillSizes = emptyList(),
            changeKeys = emptyList()
        )
    }
}