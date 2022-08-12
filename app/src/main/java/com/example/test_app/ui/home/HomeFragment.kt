package com.example.test_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test_app.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        // TODO: Use the ViewModel
        setActions()
    }

    fun setActions() {
        btnBack.setOnClickListener {
            try {
                activity?.supportFragmentManager?.popBackStackImmediate()
            } catch (e: Exception) {
                println("BACK ERROR : ${e.toString()}")
            }
        }
    }

    fun onGoBack() {
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

}