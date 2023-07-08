package com.bookmyshow.feature_one.model

data class MovieListResponse(
    val venues: ArrayList<Venue>?=null
){
    data class Venue(
        val name: String,
        val showDate: String,
        val showtimes: ArrayList<Showtime>,
        var isSelected:Boolean=false
    ){
        data class Showtime(
            val showDateCode: Long,
            val showTime: String
        )
    }
}