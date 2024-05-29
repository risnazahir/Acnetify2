package com.capstone.acnetify.views.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.acnetify.data.model.UserModel
import com.capstone.acnetify.databinding.ActivitySignInBinding
import com.capstone.acnetify.views.auth.sign_up.SignUpActivity
import com.capstone.acnetify.views.main.MainActivity
import com.capstone.acnetify.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupObservers()
    }

    /**
     * Sets up action listeners for UI elements.
     *
     * In this case, sets up click listeners for the login and signup buttons.
     */
    private fun setupAction() {
        binding.btnSignIn.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()

            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                signInViewModel.signIn(username, password)
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            // Navigate to the SignUpActivity and finish the current activity
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.btnBack.setOnClickListener {
            // Finish the current activity and navigate back to the previous activity (WelcomeActivity)
            finish()
        }
    }

    /**
     * Sets up observers for LiveData from the ViewModel.
     */
    private fun setupObservers() {
        signInViewModel.signInResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    // Hide loading indicator
                    binding.progressBar.visibility = View.GONE
                    // Handle success, navigate to main activity
                    navigateToMainActivity(result.data)
                }

                is Result.Error -> {
                    // Hide loading indicator
                    binding.progressBar.visibility = View.GONE
                    // Show error message
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Navigates to the MainActivity and finishes the current SignInActivity.
     *
     * @param user The authenticated user model.
     */
    private fun navigateToMainActivity(user: UserModel?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}