package saloon.app.android.data.repository

import saloon.app.android.data.models.Filter


suspend fun loadCompaniesNearby(filter: Filter): List<Company> {
    return emptyList()
}

suspend fun loadFavoriteCompanies(filter: Filter? = null): List<Company> {
    return emptyList()
}
