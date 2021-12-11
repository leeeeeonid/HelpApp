package com.lealpy.simbirsoft_training.ui.help

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lealpy.simbirsoft_training.R

class HelpViewModel(application: Application): AndroidViewModel(application) {

    private val _helpItems = MutableLiveData(
        listOf(
            HelpItem(0, R.drawable.child, application.getString(R.string.children)),
            HelpItem(1, R.drawable.adult, application.getString(R.string.adults)),
            HelpItem(0, R.drawable.elderly, application.getString(R.string.elderly)),
            HelpItem(0, R.drawable.animal, application.getString(R.string.animals)),
            HelpItem(0, R.drawable.event, application.getString(R.string.events))
        )
    )

    val helpItems: LiveData<List<HelpItem>> = _helpItems

}