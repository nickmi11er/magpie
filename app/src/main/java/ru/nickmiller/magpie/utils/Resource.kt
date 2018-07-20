package ru.nickmiller.magpie.utils

import java.util.*


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}


data class FetchProgress(val status: FetchStatus,
                         val all: Int = 0,
                         val completed: Int = 0,
                         val errors: Int = 0) {
    companion object {
        var lastFetched: Long = 0
        var checksum: Int = 0

        fun canFetch(checksum: Int): Boolean =
                if (this.checksum == checksum) {
                    lastFetched == 0L || Date().time - lastFetched >= 1000 * 60 * 5
                } else {
                    this.checksum = checksum
                    true
                }


        fun completed(all: Int, completed: Int, errors: Int): FetchProgress {
            lastFetched = Date().time
            return FetchProgress(FetchStatus.COMPLETED, all = all, completed = completed, errors = errors)
        }

        fun started(all: Int): FetchProgress {
            return FetchProgress(FetchStatus.STARTED, all = all, completed = 0, errors = 0)
        }

        fun progress(all: Int, completed: Int, errors: Int): FetchProgress {
            return FetchProgress(FetchStatus.PROGRESS, all = all, completed = completed, errors = errors)
        }
    }
}


enum class FetchStatus {
    PROGRESS,
    STARTED,
    COMPLETED,
}