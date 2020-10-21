package org.themoviedb.data

/**
 * Load Configuration
 * https://developers.themoviedb.org/3/configuration/get-api-configuration
 */
object ConfigurationRepository {
    //TODO implement load Configuration from network
    //https://api.themoviedb.org/3/configuration?api_key=key

    val baseUrl = "http://image.tmdb.org/t/p/"
    val secureBaseUrl = "https://image.tmdb.org/t/p/"

    val imageURL780 = secureBaseUrl + "w780"
    val imageURL185 = secureBaseUrl + "w185"
    val imageURL342 = secureBaseUrl + "w342"
}