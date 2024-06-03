package com.capstone.acnetify.data.remote

import com.capstone.acnetify.data.remote.request.SignInRequest
import com.capstone.acnetify.data.remote.request.SignUpRequest
import com.capstone.acnetify.data.remote.response.ReviewsResponse
import com.capstone.acnetify.data.remote.response.SignInResponse
import com.capstone.acnetify.data.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface defining endpoints for communicating with the remote API.
 */
interface ApiService {

    /**
     * Makes a request to register a new user.
     *
     * @param request The sign-up request containing user information.
     * @return A response indicating the outcome of the sign-up attempt.
     */
    @POST("/auth/register")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

    /**
     * Makes a request to authenticate an existing user.
     *
     * @param request The sign-in request containing user credentials.
     * @return A response indicating the outcome of the sign-in attempt.
     */
    @POST("/auth/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    /**
     * Fetches all reviews sorted by new.
     *
     * @param limit The maximum number of reviews to fetch.
     * @param offset The number of reviews to skip before starting to fetch.
     * @return A list of reviews.
     */
    @GET("/review")
    suspend fun getAllReviews(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): ReviewsResponse

    /**
     * Fetches reviews for a specific acne type sorted by likes.
     *
     * @param acneType The type of acne for which to fetch reviews.
     * @param limit The maximum number of reviews to fetch.
     * @param offset The number of reviews to skip before starting to fetch.
     * @return A list of reviews.
     */
    @GET("/review/{acne_type}")
    suspend fun getReviewsByAcneType(
        @Path("acne_type") acneType: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): ReviewsResponse
}