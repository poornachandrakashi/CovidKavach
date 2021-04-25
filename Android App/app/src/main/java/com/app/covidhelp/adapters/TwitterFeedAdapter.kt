package com.app.covidhelp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.covidhelp.api.APIREsponse
import com.app.covidhelp.databinding.LayoutFeedGenericBinding

class TwitterFeedAdapter : RecyclerView.Adapter<TwitterFeedAdapter.GenericFeedViewHolder>() {

    var marray: ArrayList<APIREsponse> = ArrayList<APIREsponse>()

    inner class GenericFeedViewHolder(itemView: LayoutFeedGenericBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding = itemView;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericFeedViewHolder {
        return GenericFeedViewHolder(
            LayoutFeedGenericBinding.inflate(
                parent.context.getSystemService(
                    LayoutInflater::class.java
                )
            )
        )
    }

    override fun onBindViewHolder(holder: GenericFeedViewHolder, position: Int) {
        holder.binding.description.text = marray.get(position).text
    }

    override fun getItemCount(): Int {
        return marray.size
    }
}