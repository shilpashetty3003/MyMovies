package com.example.mvvm_hilt_paging_flow.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ViewLifeCycleDelegateMutable<T> : ReadWriteProperty<Fragment,T>,LifecycleObserver {
    private var isObserverAdded=false
    private var value :T ? =null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
       return  value!!
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
       if(!isObserverAdded){
           thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
           isObserverAdded=true
       }
        this.value=value

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed(){
        isObserverAdded=false
        value=null
    }
}