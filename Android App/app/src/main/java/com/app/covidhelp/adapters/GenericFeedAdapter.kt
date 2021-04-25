package com.app.covidhelp.adapters

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.covidhelp.GenericFeed
import com.app.covidhelp.databinding.LayoutFeedGenericBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.chip.Chip


class GenericFeedAdapter(
    options: FirestoreRecyclerOptions<GenericFeed>,
    val Onvoted: (id: GenericFeed, factor: Int) -> Unit, var type: Int = 0
) :
    FirestoreRecyclerAdapter<GenericFeed, GenericFeedAdapter.GenericFeedViewHolder>(options) {

    var TAG = "GenericFeedAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericFeedViewHolder {
        return GenericFeedViewHolder(
            LayoutFeedGenericBinding.inflate(
                parent.context.getSystemService(
                    LayoutInflater::class.java
                )
            )
        )

    }

    override fun onBindViewHolder(
        holder: GenericFeedViewHolder,
        position: Int,
        model: GenericFeed
    ) {

        holder.binding.apply {

            Log.d(TAG, "onBindViewHolder:$model ")
            name.text = model.username
            description.text = model.description
            contact.text = model.contact
            trust.text = model.trust.toString()
            upvote.setOnClickListener { Onvoted(model, 1) }
            downvote.setOnClickListener { Onvoted(model, -1) }
            if (type != 0) {
                Log.d(TAG, "onBindViewHolder: In here")
                upvote.text = "Refresh"
                downvote.text = "Delete"
            }
            chipgroup.removeAllViews()
            model.tags.forEach {
                val chip = Chip(name.context)
                val paddingDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    name.context?.getResources()?.getDisplayMetrics()
                ).toInt()
                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
                chip.setText(it.toString())
                chipgroup.addView(chip)
            }


        }

    }


    inner class GenericFeedViewHolder(itemView: LayoutFeedGenericBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding = itemView;

    }
}