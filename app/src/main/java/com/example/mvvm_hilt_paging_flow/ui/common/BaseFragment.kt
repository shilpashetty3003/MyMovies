package com.example.mvvm_hilt_paging_flow.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mvvm_hilt_paging_flow.utils.ViewLifeCycleDelegateMutable

abstract class BaseFragment<T:ViewBinding>: Fragment() {

     var binding:T by ViewLifeCycleDelegateMutable()

    abstract fun inflateBinding(inflater: LayoutInflater,container: ViewGroup?):T


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= inflateBinding(inflater,container).apply { binding=this }.root
}