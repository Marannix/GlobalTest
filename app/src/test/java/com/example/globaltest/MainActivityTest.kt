package com.example.globaltest

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

class MainActivityTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context
    private var counter: Int = 1

    @Before
    fun setUp() {
        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        editor = Mockito.mock(SharedPreferences.Editor::class.java)
        context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(editor)
        Mockito.`when`(sharedPreferences.getInt("COUNTER_KEY", 0)).thenReturn(counter)
    }

    @Test
    @Throws
    fun `assert counter matches the counter saved in shared preference`() {
        val counterFromPreference = sharedPreferences.getInt("COUNTER_KEY", 0)
        assertNotNull(counterFromPreference)
        assertEquals(counterFromPreference, counter)
    }
}