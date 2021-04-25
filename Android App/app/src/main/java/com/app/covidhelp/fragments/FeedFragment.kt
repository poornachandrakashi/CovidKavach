package com.app.covidhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.app.covidhelp.GenericFeed
import com.app.covidhelp.adapters.GenericFeedAdapter
import com.app.covidhelp.databinding.FragmentFeedBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedFragment : Fragment() {

    lateinit var bind: FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        bind = FragmentFeedBinding.inflate(inflater)

        val query = FirebaseFirestore.getInstance().collection("feeds")
            .orderBy("id")

        val options = FirestoreRecyclerOptions.Builder<GenericFeed>()
            .setQuery(query, GenericFeed::class.java)
            .setLifecycleOwner(this)
            .build()
        bind.rv.layoutManager = GridLayoutManager(context, 1)
        bind.rv.adapter = GenericFeedAdapter(options, { id: GenericFeed, flag: Int ->
            addVotes(id, flag)
        })


        return bind.root
    }


    private fun addVotes(id: GenericFeed, flag: Int) {

        if (!id.voters.contains(FirebaseAuth.getInstance().currentUser?.phoneNumber)) {
            id.trust += 1
            FirebaseAuth.getInstance().currentUser?.phoneNumber?.let { id.voters.add(it) }
            FirebaseFirestore.getInstance().collection("feeds")
                .document(id.id)
                .set(id)
                .addOnSuccessListener {

                }
        }


    }

}