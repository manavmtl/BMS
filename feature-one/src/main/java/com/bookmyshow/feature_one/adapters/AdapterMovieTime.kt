package com.bookmyshow.feature_one.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.feature_one.databinding.ItemMoviesBinding
import com.bookmyshow.feature_one.databinding.ItemTimeBinding
import com.bookmyshow.feature_one.model.MovieListResponse

class AdapterMovieTime(private val onTimeClicked: (position: Int, MovieListResponse.Venue.Showtime) -> Unit) :
    RecyclerView.Adapter<AdapterMovieTime.MyViewHolder>() {
    private var timeList = ArrayList<MovieListResponse.Venue.Showtime>()

    inner class MyViewHolder(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, item: MovieListResponse.Venue.Showtime) {
            binding.apply {
                tvTime.text = item.showTime
                root.setOnClickListener {
                    onTimeClicked(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = timeList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = timeList[position]
        holder.bind(position, item)
    }

    fun updateData(timeList: ArrayList<MovieListResponse.Venue.Showtime>) {
        this.timeList = timeList
        if (timeList.isEmpty())
            return
        notifyItemRangeChanged(0, timeList.size - 1)
    }
}