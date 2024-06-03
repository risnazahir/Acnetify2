package com.capstone.acnetify.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.acnetify.data.model.ReviewsModel
import com.capstone.acnetify.data.paging.AllReviewsPagingSource
import com.capstone.acnetify.data.paging.ReviewsByAcneTypePagingSource
import com.capstone.acnetify.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class responsible for fetching review data from the API.
 *
 * This repository interacts with the [ApiService] to fetch reviews and provides paginated data
 * using the Paging 3 library. It supports fetching all reviews sorted by new and fetching
 * reviews by acne type sorted by likes.
 *
 * @property apiService The ApiService instance used for making network requests.
 */
class ReviewsRepository @Inject constructor(
    private var apiService: ApiService,
) {

    /**
     * Fetches all reviews sorted by new.
     *
     * This function returns a Flow of PagingData containing reviews. The reviews are fetched from
     * the API using the AllReviewsPagingSource, which handles pagination and loads data page by page.
     *
     * @return A [Flow] of [PagingData] containing the reviews.
     */
    fun getAllReviews(): Flow<PagingData<ReviewsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false // Disables placeholders for unloaded items
            ),
            pagingSourceFactory = {
                AllReviewsPagingSource(apiService)
            }
        ).flow
    }

    /**
     * Fetches reviews for a specific acne type sorted by likes.
     *
     * This function returns a Flow of PagingData containing reviews filtered by acne type.
     * The reviews are fetched from the API using the ReviewsByAcneTypePagingSource, which
     * handles pagination and loads data page by page.
     *
     * @param acneType The type of acne for which to fetch reviews.
     * @return A [Flow] of [PagingData] containing the reviews.
     */
    fun getReviewsByAcneType(acneType: String): Flow<PagingData<ReviewsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false // Disables placeholders for unloaded items
            ),
            pagingSourceFactory = {
                ReviewsByAcneTypePagingSource(apiService, acneType)
            }
        ).flow
    }
}