package com.moviesnow.features.movies.data

import android.content.Context
import android.util.Log
import com.moviesnow.features.movies.domain.Movie
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class FakeMovieService(private val context: Context) {

    fun getMovies(): List<Movie> {
        return try {
            val json = context.assets.open("filmes.json").bufferedReader().use { it.readText() }

            val data = try {
                JSONObject(json).getJSONObject("data").getJSONArray("movies")
            } catch (e: JSONException) {
                Log.e("FakeMovieService", "Erro ao processar o JSON", e)
                throw MovieServiceException("Erro ao processar os dados JSON")
            }

            (0 until data.length()).map { i ->
                val item = data.getJSONObject(i)
                Movie(
                    id = item.getString("id"),
                    title = item.getString("title"),
                    description = item.getString("description"),
                    releaseYear = item.getInt("releaseYear"),
                    durationMinutes = item.getInt("durationMinutes"),
                    rating = item.getDouble("rating"),
                    posterUrl = item.getString("posterUrl"),
                    trailerUrl = item.getString("trailerUrl")
                )
            }

        } catch (e: IOException) {
            Log.e("FakeMovieService", "Erro ao abrir o arquivo", e)
            throw MovieServiceException("Erro ao abrir o arquivo JSON")
        } catch (e: Exception) {
            Log.e("FakeMovieService", "Erro desconhecido", e)
            throw MovieServiceException("Erro desconhecido ao obter os filmes")
        }
    }
}

class MovieServiceException(message: String) : Exception(message)
