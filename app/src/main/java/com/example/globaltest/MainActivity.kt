package com.example.globaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.globaltest.repository.ServerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val serverRepository = ServerRepository()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = serverRepository.fetchServer().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, it.nextPath, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            )

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}
