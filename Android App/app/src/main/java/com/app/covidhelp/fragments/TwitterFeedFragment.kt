package com.app.covidhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.covidhelp.databinding.FragmentTwitterFeedBinding

class TwitterFeedFragment : Fragment() {


    lateinit var bind: FragmentTwitterFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentTwitterFeedBinding.inflate(inflater)




        return bind.root
    }


}