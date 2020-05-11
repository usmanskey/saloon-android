package saloon.app.android.data.models

class Model {

    var item: Any? = null
        private set

    fun <T> setT(item: T) {
        this.item = item
    }
}