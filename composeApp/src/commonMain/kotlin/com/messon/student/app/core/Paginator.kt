package com.messon.student.app.core
import com.messon.student.app.utils.Result


class Paginator<Key, Item, Error>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (Key) -> Result<Item, Error>,
    private val onError: suspend (Error) -> Unit,
    private val getNextKey: suspend (Key, Item) -> Key,
    private val onSuccess: suspend (Item, Key) -> Unit,
    private val endReached: (Key, Item) -> Boolean
) {

    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isEndReached = false

    suspend fun loadNextItems() {
        if (isMakingRequest || isEndReached) return

        isMakingRequest = true
        onLoadUpdated(true)

        try {
            when (val result = onRequest(currentKey)) {

                is Result.Error -> {
                    onError(result.error)
                }

                is Result.Success -> {
                    val item = result.data

                    val newKey = getNextKey(currentKey, item)
                    currentKey = newKey

                    onSuccess(item, newKey)

                    isEndReached = endReached(currentKey, item)
                }
            }
        } finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }
    }

    fun reset() {
        currentKey = initialKey
        isEndReached = false
    }
}