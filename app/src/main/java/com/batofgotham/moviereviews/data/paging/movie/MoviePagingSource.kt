package com.batofgotham.moviereviews.data.paging.movie

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.repository.MovieRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class MoviePagingSource(private val movieRepo: MovieRepo): PagingSource<Int, Movie>() {

    private val TAG = this.javaClass.simpleName

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return state.pages.get(anchorPosition).prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try{
            val key = params.key ?: 1
            val response = movieRepo.getMoviesNetworkResponse(key)

            val currentPage = response.page
            val totalPages = response.totalPages
            val noOfResults = response.totalResults
            val movies = response.results

            Log.i(TAG,"The number of results is ${movies.size}")

            Log.i(TAG,"Current Page: $currentPage \n Total Pages: $totalPages \n " +
                    "No of Results: $noOfResults \n")

            Log.i(TAG,"prevKey: ${max(currentPage - 1, 1)} nextKey: ${min(currentPage + 1, totalPages)}")

            return LoadResult.Page(
                data = movies,
                prevKey = getPrevKey(currentPage),
                nextKey = min(currentPage + 1, totalPages)
            )
        }
        catch (ioException: IOException){
            return LoadResult.Error(ioException)
        }
        catch(httpException: HttpException){
            return LoadResult.Error(httpException)
        }
    }

    private fun getPrevKey(currentKey: Int): Int?{
        if(currentKey == 1)
            return null
        else
            return currentKey - 1
    }

    override val keyReuseSupported: Boolean = true
}