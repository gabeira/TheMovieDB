package org.themoviedb.data

/**
 * Load Configuration
 * https://developers.themoviedb.org/3/configuration/get-api-configuration
 */
object ConfigurationRepository {
    //TODO implement load Configuration from network
    //https://api.themoviedb.org/3/configuration?api_key=key

    const val baseUrl = "http://image.tmdb.org/t/p/"
    const val secureBaseUrl = "https://image.tmdb.org/t/p/"

    const val imageURL780 = secureBaseUrl + "w780"
    const val imageURL185 = secureBaseUrl + "w185"
    const val imageURL342 = secureBaseUrl + "w342"
}
