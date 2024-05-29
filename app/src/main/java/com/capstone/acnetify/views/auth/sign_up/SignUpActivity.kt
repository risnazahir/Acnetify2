package com.capstone.acnetify.views.auth.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.acnetify.databinding.ActivitySignUpBinding
import com.capstone.acnetify.views.auth.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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
            // Navigate to the SignUpActivity and finish the current activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {

        }

        binding.btnBack.setOnClickListener {
            // Finish the current activity and navigate back to the previous activity (WelcomeActivity)
            finish()
        }
        
    }
}