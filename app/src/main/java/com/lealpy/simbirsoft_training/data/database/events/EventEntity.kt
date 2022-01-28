package com.lealpy.simbirsoft_training.data.database.events

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "event_entities",
    indices = [Index("title")]
)
data class EventEntity (
    @PrimaryKey
    val id : Long,
    val title : String,
    val date : String
)
