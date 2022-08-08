package com.batofgotham.moviereviews.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.batofgotham.moviereviews.data.database.MovieDatabase
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.RemoteKey
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val database: MovieDatabase,
    private val network: ApiService
): RemoteMediator<Int, MovieEntity>() {

    val movieDao = database.movieDao
    val remoteKeyDao = database.remoteKeyDao

    private val TAG = "PagingFlow"
    private val LTAG = "LoadType"


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {

        return try {

            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    Log.i(LTAG,"Refresh called")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    Log.i(LTAG,"Prepend called")
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)

                    prevPage
                }

                LoadType.APPEND -> {
                    Log.i(LTAG,"Append called")
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)

                    nextPage
                }
            }

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    Log.i(TAG,"Database clear called")
                    movieDao.clearAllMovies()
                    remoteKeyDao.clearAll()
                }
            }

            val response = network.getMoviesApiResponse(currentPage)
            Log.i(TAG,"Response received: $response")
            val results = response.results


            val totalPages = response.totalPages
            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(currentPage == totalPages) null else currentPage + 1


            database.withTransaction {
                val movieEntities = results.map {
                    MovieEntity.fromRemote(it)
                }

                val remoteIds = movieDao.insertMovies(movieEntities)

                val keys = remoteIds.map {
                    RemoteKey(
                        remoteId = it,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }

                remoteKeyDao.insertAll(keys)

            }


            MediatorResult.Success(endOfPaginationReached = currentPage == totalPages)
        }
        catch (e: IOException){
            MediatorResult.Error(e)
        }
        catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKey? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { remoteId ->
                remoteKeyDao.getRemoteKey(remoteId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKey? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeyDao.getRemoteKey(it.remoteId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKey? {

        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeyDao.getRemoteKey(it.remoteId)
        }
    }
}