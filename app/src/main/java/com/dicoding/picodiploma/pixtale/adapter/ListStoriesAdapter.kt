package com.dicoding.picodiploma.pixtale.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.pixtale.data.response.ListStoryItem
import com.dicoding.picodiploma.pixtale.view.Detail.DetailActivity

class ListStoriesAdapter(private var listStories: List<ListStoryItem>) : RecyclerView.Adapter<ListStoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(storyItem: ListStoryItem) {
            itemView.findViewById<TextView>(R.id.tv_detail_name).text = storyItem.name
            itemView.findViewById<TextView>(R.id.tv_detail_description).text = storyItem.description
            Glide.with(itemView.context)
                .load(storyItem.photoUrl)
                .into(itemView.findViewById<ImageView>(R.id.iv_detail_photo))

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("EXTRA_STORY", storyItem)
                itemView.context.startActivity(intent)
            }

            itemView.findViewById<com.google.android.material.button.MaterialButton>(R.id.actionButton).setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("EXTRA_STORY", storyItem)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_stories, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStories[position])
    }

    override fun getItemCount(): Int = listStories.size

    fun submitList(newList: List<ListStoryItem>) {
        listStories = newList
        notifyDataSetChanged()
    }
}