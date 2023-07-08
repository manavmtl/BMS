package com.bookmyshow.feature_one.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.feature_one.R
import com.bookmyshow.feature_one.databinding.ItemFiltersDateBinding
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.utils.CommonUtils

class AdapterFilterDate(private val onDateClicked: (position: Int, MovieListResponse.Venue) -> Unit) :
    RecyclerView.Adapter<AdapterFilterDate.MyViewHolder>() {

    private var venuesList = ArrayList<MovieListResponse.Venue>()

    inner class MyViewHolder(private val binding: ItemFiltersDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, item: MovieListResponse.Venue) {
            binding.apply {
                tvDate.text = item.showDate


                tvDate.setOnClickListener {
                    val isSelected = item.isSelected
                    venuesList.forEachIndexed { index, venue ->
                        if (index != position) {
                            venuesList[index].isSelected = false
                        } else {
                            venuesList[index].isSelected = !isSelected
                        }
                        notifyItemChanged(index)
                    }
                    item.isSelected=!isSelected
                    val data = venuesList[position]
                    onDateClicked(position, data)
                }
                if (item.isSelected) {
                    clDatesParent.setBackgroundColor(ContextCompat.getColor(root.context, R.color.red))
                    tvDate.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                } else {
                    clDatesParent.setBackgroundColor(ContextCompat.getColor(root.context, R.color.white))
                    tvDate.setTextColor(ContextCompat.getColor(root.context, R.color.black))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFiltersDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = venuesList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = venuesList[position]
        holder.bind(position, item)
    }

    fun updateData(venuesList: ArrayList<MovieListResponse.Venue>) {
        this.venuesList = venuesList
        if (venuesList.isEmpty())
            return
        notifyItemRangeChanged(0, venuesList.size - 1)
    }
}