package com.lealpy.simbirsoft_training.data.repository

import android.util.Log
import com.lealpy.simbirsoft_training.data.api.EventApi
import com.lealpy.simbirsoft_training.data.database.search_by_events.EventDao
import com.lealpy.simbirsoft_training.data.utils.DataUtils
import com.lealpy.simbirsoft_training.data.utils.DataUtils.Companion.LOG_TAG
import com.lealpy.simbirsoft_training.data.utils.toEventEntities
import com.lealpy.simbirsoft_training.data.utils.toEventItems
import com.lealpy.simbirsoft_training.domain.model.EventItem
import com.lealpy.simbirsoft_training.domain.repository.EventRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDao : EventDao,
    private val eventApi: EventApi,
    private val utils : DataUtils
) : EventRepository {

    override fun insertToDbEventItemsFromServer() : Completable {
        return Completable.create{ emitter ->
            eventApi.getEventItems()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                    { eventItemsFromServer ->
                        insertToDbEventItems(eventItemsFromServer)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                {
                                    emitter.onComplete()
                                },
                                { error ->
                                    Log.e(LOG_TAG, error.message.toString())
                                }
                            )
                    },
                    { error ->
                        Log.e(LOG_TAG, error.message.toString())
                        emitter.onError(error)
                    }
                )
        }
    }

    override fun insertToDbEventItemsFromFile() : Completable {
        return Completable.create { emitter ->
            val eventItemsFromFile = utils.getItemsFromFile<List<EventItem>>(EVENT_ITEMS_FILE_NAME)
            insertToDbEventItems(eventItemsFromFile)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        emitter.onComplete()
                    },
                    { error ->
                        Log.e(LOG_TAG, error.message.toString())
                    }
                )
        }
    }

    override fun getFromDbEventItemsByTitle(searchQuery : String) : Single<List<EventItem>> {
        return eventDao.getEventEntitiesByTitle(searchQuery).map { eventEntities ->
            eventEntities.toEventItems()
        }
    }

    override fun getFromDbAllEventItems() : Single<List<EventItem>> {
        return eventDao.getAllEventEntities().map { eventEntities ->
            eventEntities.toEventItems()
        }
    }

    override fun insertToDbEventItems(eventItems : List<EventItem>) : Completable {
        val eventEntities = eventItems.toEventEntities()
        return eventDao.insertEventEntities(eventEntities)
    }

    companion object {
        private const val EVENT_ITEMS_FILE_NAME = "event_items.json"
    }

}