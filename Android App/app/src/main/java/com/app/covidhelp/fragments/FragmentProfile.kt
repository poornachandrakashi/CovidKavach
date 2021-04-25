package com.app.covidhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.app.covidhelp.GenericFeed
import com.app.covidhelp.adapters.GenericFeedAdapter
import com.app.covidhelp.databinding.FragmentProfileBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class FragmentProfile : Fragment() {

    lateinit var bind: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        bind = FragmentProfileBinding.inflate(inflater)

        val query = FirebaseFirestore.getInstance().collection("feeds")
            .orderBy("id")

        val options = FirestoreRecyclerOptions.Builder<GenericFeed>()
            .setQuery(query, GenericFeed::class.java)
            .setLifecycleOwner(this)
            .build()
        bind.rv.layoutManager = GridLayoutManager(context, 1)
        bind.rv.adapter = GenericFeedAdapter(options, { id: GenericFeed, flag: Int ->
            applyParam(id, flag)
        },10)
        return bind.root
    }

    private fun applyParam(genericFeed: GenericFeed, mode: Int) {

        if (mode == 1) {
            refresh(genericFeed)
        } else {
            delete(genericFeed)
        }
    }

    private fun delete(genericFeed: GenericFeed) {
        FirebaseFirestore.getInstance().collection("feeds").document(genericFeed.id).delete()
    }

    private fun refresh(genericFeed: GenericFeed) {


    }


}