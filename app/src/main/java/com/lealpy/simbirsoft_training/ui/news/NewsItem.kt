package com.lealpy.simbirsoft_training.ui.news

import android.graphics.Bitmap

data class NewsItem (
    val id : Long,
    val image : Bitmap,
    val title : String,
    val abbreviatedText : String,
    val date : String,
    val fundName : String,
    val address: String,
    val phone : String,
    val image2 : Bitmap,
    val image3 : Bitmap,
    val fullText : String,
    val isChildrenCategory : Boolean,
    val isAdultsCategory : Boolean,
    val isElderlyCategory : Boolean,
    val isAnimalsCategory : Boolean,
    val isEventsCategory : Boolean
)