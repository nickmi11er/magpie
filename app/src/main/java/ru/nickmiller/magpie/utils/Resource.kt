package ru.nickmiller.magpie.utils


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
        fun completed(all: Int, completed: Int, errors: Int): FetchProgress {
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