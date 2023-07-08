package com.bookmyshow.feature_one.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.feature_one.databinding.ItemMoviesBinding
import com.bookmyshow.feature_one.model.MovieListResponse

class AdapterMovieList(private val onChildTimeClicked: (parentVenuePosition: Int, childTimePosition: Int, parentVenueItem: MovieListResponse.Venue, childTimeItem: MovieListResponse.Venue.Showtime) -> Unit) :
    RecyclerView.Adapter<AdapterMovieList.MyViewHolder>() {
    private var venuesList = ArrayList<MovieListResponse.Venue>()

    inner class MyViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, item: MovieListResponse.Venue) {
            binding.apply {
                tvVenueName.text = item.name
                tvDate.text = item.showDate
                val adapterMovieTime = AdapterMovieTime { timePosition, timeData ->
                    onChildTimeClicked(position, timePosition, item, timeData)
                }
                rvTime.adapter = adapterMovieTime
                adapterMovieTime.updateData(item.showtimes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = venuesList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = venuesList[position]
        holder.bind(position, item)
    }

    fun updateData(venuesList: ArrayList<MovieListResponse.Venue>) {
        this.venuesList = venuesList
        notifyDataSetChanged()
    }

    fun updatePosition(position: Int, item: MovieListResponse.Venue) {
        venuesList[position] = item
        notifyItemChanged(position)
    }
}