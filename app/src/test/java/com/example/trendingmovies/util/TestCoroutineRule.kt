@file: Suppress("NoWildcardImports")

package com.example.trendingmovies.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// https://github.com/googlecodelabs/kotlin-coroutines/blob/c03cf4447ef4a615cf8f199b38b883b74028c5d9/coroutines-codelab/finished_code/src/test/java/com/example/android/kotlincoroutines/main/utils/MainCoroutineScopeRule.kt

/**
 * TestCoroutineRule installs a TestCoroutineDispatcher for Disptachers.Main.
 *
 * Since it extends TestCoroutineScope, you can directly launch coroutines on the TestCoroutineRule
 * as a [CoroutineScope]:
 *
 * ```
 * testCoroutineRule.launch { aTestCoroutine() }
 * ```
 *
 * All coroutines started on [MainCoroutineScopeRule] must complete (including timeouts) before the test
 * finishes, or it will throw an exception.
 *
 * When using TestCoroutineRule you should always invoke runBlockingTest on it to avoid creating two
 * instances of [TestCoroutineDispatcher] or [TestCoroutineScope] in your test:
 *
 * ```
 * @Test
 * fun usingRunBlockingTest() = testCoroutineRule.runBlockingTest {
 *     aTestCoroutine()
 * }
 * ```
 *
 * You may call [DelayController] methods on [MainCoroutineScopeRule] and they will control the
 * virtual-clock.
 *
 * ```
 * testCoroutineRule.pauseDispatcher()
 * // do some coroutines
 * testCoroutineRule.advanceUntilIdle() // run all pending coroutines until the dispatcher is idle
 * ```
 *
 * By default, [MainCoroutineScopeRule] will be in a *resumed* state.
 *
 * @param dispatcher if provided, this [TestCoroutineDispatcher] will be used.
 */

@ExperimentalCoroutinesApi
class TestCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        // If your codebase allows the injection of other dispatchers like
        // Dispatchers.Default and Dispatchers.IO, consider injecting all of them here
        // and renaming this class to `CoroutineScopeRule`
        //
        // All injected dispatchers in a test should point to a single instance of
        // TestCoroutineDispatcher.
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
