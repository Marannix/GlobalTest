package com.example.globaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.globaltest.viewmodel.ServerViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var serverViewModel: ServerViewModel
    private lateinit var responseCode: MutableLiveData<String>
    private lateinit var timesFetched: MutableLiveData<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initViewModel()
        getServerResponse()
        fetchResponseCode()
        fetchTimesFetched()
        updateUI()
    }

    private fun initViewModel() {
        serverViewModel = ViewModelProviders.of(this)
            .get(ServerViewModel::class.java)
    }

    private fun getServerResponse() {
        serverViewModel.getPathResponse()
    }

    private fun fetchResponseCode() {
        responseCode = serverViewModel.getResponseCode()
    }

    private fun fetchTimesFetched() {
        timesFetched = serverViewModel.getTimesFetched()
    }

    private fun updateUI() {
        responseCode.observe(this, Observer {
            responseCodeFetched.text = responseCode.value
        })

        timesFetched.observe(this, Observer {
            timesFetchedText.text = timesFetched.value.toString()
        })

        fetchContentButton.setOnClickListener {
            getServerResponse()
        }
    }
}
