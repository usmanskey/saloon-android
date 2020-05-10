package saloon.app.android.ui.models

class Model {

    var item: Any? = null
        private set

    fun <T> setT(item: T) {
        this.item = item
    }
}