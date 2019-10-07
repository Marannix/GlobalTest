package com.example.globaltest

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry

import androidx.lifecycle.Observer
import com.example.globaltest.MockitoUtils.mock
import com.example.globaltest.repository.ServerRepository
import com.example.globaltest.viewmodel.ServerViewModel
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.verify

private const val COUNTER = 1
private const val RESPONSE_CODE = "4b0087a7-1df2-4644-8715-2e91257d2b6f"

class MainActivityTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context
    private lateinit var serverRepository: ServerRepository
    private lateinit var serverViewModel: ServerViewModel
    private lateinit var timeFetchedObserver: Observer<Int>
    private lateinit var responseCodeObserver: Observer<Int>
    private lateinit var lifecycle: LifecycleRegistry

    private var counter: Int = COUNTER

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        sharedPreferences = mock()
        editor = mock()
        context = mock()
        serverRepository = ServerRepository(context)
        serverViewModel = ServerViewModel(Mockito.mock(Application::class.java))
        timeFetchedObserver = mock()
        responseCodeObserver = mock()
        lifecycle = mock()

        serverViewModel.getTimesFetched().observeForever { timeFetchedObserver }
        serverViewModel.getResponseCode().observeForever { responseCodeObserver }

        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(editor)
        Mockito.`when`(sharedPreferences.getInt("COUNTER_KEY", 0)).thenReturn(counter)
    }

    @Test
    @Throws
    fun `assert counter saved in shared preference matches mocked counter`() {
        val counterFromPreference = sharedPreferences.getInt("COUNTER_KEY", 0)
        assertNotNull(counterFromPreference)
        assertEquals(counterFromPreference, counter)
    }

    @Test
    fun `assert times fetched returns counter`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        serverViewModel.getTimesFetched().observe({ lifecycle }) { timesFetched ->
            timesFetched?.let { timeFetchedObserver }
        }
        assertNull(serverViewModel.getTimesFetched().value)
        serverViewModel.getTimesFetched().postValue(COUNTER)
        assertNotNull(serverViewModel.getTimesFetched().value)
        assert(serverViewModel.getTimesFetched().value == COUNTER)
    }

    @Test
    fun `assert fetch response code returns response code`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        serverViewModel.getResponseCode().observe({ lifecycle }) { timesFetched ->
            timesFetched?.let { responseCodeObserver }
        }

        assertNull(serverViewModel.getResponseCode().value)
        serverViewModel.getResponseCode().postValue(RESPONSE_CODE)
        assertNotNull(serverViewModel.getResponseCode().value)
        assert(serverViewModel.getResponseCode().value == RESPONSE_CODE)
    }
}