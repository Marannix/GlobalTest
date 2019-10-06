package com.example.globaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.globaltest.repository.ServerRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var serverRepository: ServerRepository
//    private val serverRepository = ServerRepository(applicationContext)
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serverRepository = ServerRepository(applicationContext)
        fetchPath()
        fetchNextPathResponse()
    }

    private fun fetchPath() {
        val disposable = serverRepository.fetchServer()
        disposables.add(disposable)
    }

    private fun fetchNextPathResponse() {
//        val disposable = serverRepository.fetchCurrentPathResponse()
//        responseCodeFetched.text = serverRepository.path
//        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}
