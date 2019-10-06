package com.example.globaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.globaltest.repository.ServerRepository
import com.example.globaltest.viewmodel.ServerViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var serverViewModel: ServerViewModel
    private lateinit var responseCode: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initViewModel()
        getServerResponse()
        fetchResponseCode()
        updateUI()
    }

    private fun initViewModel() {
        serverViewModel = ViewModelProviders.of(this)
            .get(ServerViewModel::class.java)
    }

    private fun getServerResponse() {
        serverViewModel.getServer()
    }

    private fun fetchResponseCode() {
        responseCode = serverViewModel.getResponseCode()
    }

    private fun updateUI() {
        responseCode.observe(this, Observer {
            responseCodeFetched.text = responseCode.value
        })

        fetchContentButton.setOnClickListener {
            getServerResponse()
        }
    }
}
