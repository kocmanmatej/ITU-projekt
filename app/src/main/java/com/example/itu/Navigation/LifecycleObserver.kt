package com.example.itu.Navigation

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.itu.Model.Repository

// Author: Matej Kocman

class LifecycleObserver(private val repository: Repository) : DefaultLifecycleObserver {
    override fun onPause(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onPause(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onDestroy(owner)
    }
}