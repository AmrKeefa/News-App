package com.clicks.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.clicks.newsapp.data.repository.NewsRepository
import com.clicks.newsapp.utils.Resource
import com.clicks.newsapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNews() = liveData(Dispatchers.IO) {
        emit(Resource(state = ResourceState.LOADING, data = null))
        try {
            val results = newsRepository.getNews()
            emit(Resource(ResourceState.SUCCESS, data = results))
        } catch (e: Exception) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )

        } catch (e: IOException) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )

        }
    }
}