package com.capstone.acnetify.views.auth.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.acnetify.databinding.ActivitySignInBinding
import com.capstone.acnetify.views.auth.sign_up.SignUpActivity
import com.capstone.acnetify.views.main.MainActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    /**
     * Sets up action listeners for UI elements.
     *
     * In this case, sets up click listeners for the login and signup buttons.
     */
    private fun setupAction() {
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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

}