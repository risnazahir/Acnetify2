package com.capstone.acnetify.data.remote

import com.capstone.acnetify.data.remote.request.SignInRequest
import com.capstone.acnetify.data.remote.request.SignUpRequest
import com.capstone.acnetify.data.remote.response.SignInResponse
import com.capstone.acnetify.data.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

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
}