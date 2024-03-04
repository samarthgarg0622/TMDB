package com.example.tmdb.baseComponent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(
    private val bindingInflater: (inflator: LayoutInflater) -> T
) : Fragment() {

    private var _binding: T? = null
    val binding: T
    get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null){
            throw IllegalArgumentException("BINDING_ERROR_MESSAGE")
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}