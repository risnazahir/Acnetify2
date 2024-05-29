package com.capstone.acnetify.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Represents the response received after a sign-up request.
 *
 * @property data A message indicating the outcome of the sign-up attempt.
 * @property message The user information associated with the sign-up response.
 */
data class SignUpResponse(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
