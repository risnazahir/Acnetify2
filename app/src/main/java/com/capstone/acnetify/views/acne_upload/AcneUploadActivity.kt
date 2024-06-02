package com.capstone.acnetify.views.acne_upload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.acnetify.databinding.ActivityAcneUploadBinding

class AcneUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcneUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcneUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}