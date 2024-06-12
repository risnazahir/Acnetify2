package com.capstone.acnetify.views.history_acne

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.acnetify.data.model.ImageSubmissionsModel
import com.capstone.acnetify.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel class for the HistoryAcneFragment, responsible for managing UI-related data in a lifecycle-conscious way.
 *
 * This ViewModel interacts with the [ImageRepository] to fetch and cache paginated image submission data,
 * exposing it to the UI as [LiveData] streams. It uses the [viewModelScope] to ensure that coroutines
 * are automatically canceled when the ViewModel is cleared.
 *
 * @property imageRepository The repository used to fetch image submission data from the API.
 * @constructor Injects the [ImageRepository] into the ViewModel using Hilt.
 */
@HiltViewModel
class HistoryAcneViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
) : ViewModel() {

    /**
     * LiveData stream of paginated [ImageSubmissionsModel] data.
     *
     * This LiveData is observed by the UI (HistoryAcneFragment) to update the RecyclerView with new data
     * when available. The data is fetched from the [imageRepository] and cached in the [viewModelScope]
     * to ensure it survives configuration changes.
     */
    val histories: LiveData<PagingData<ImageSubmissionsModel>> = imageRepository.getImagesSubmissions()
        .cachedIn(viewModelScope)
        .asLiveData()
}