package com.capstone.acnetify.views.acne_detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.acnetify.R
import com.capstone.acnetify.data.model.ImageSubmissionsModel
import com.capstone.acnetify.data.model.ReviewsModel
import com.capstone.acnetify.databinding.ActivityAcneDetailBinding
import com.capstone.acnetify.views.home.ReviewsAdapter

class AcneDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcneDetailBinding
    private val feedsAdapter = ReviewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcneDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrive detail object from the intent extras
        val acneDetail = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ACNE_ITEM, ImageSubmissionsModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ACNE_ITEM)
        }
        Log.d(LOG, "acneDetail: $acneDetail")

        acneDetail?.let {
            Glide.with(this)
                .load(it.imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.previewImageView)

            binding.textViewAcneType.text = when (it.acneType) {
                "acne_nodules" -> "Nodules"
                "milia" -> "Milia"
                "blackhead" -> "Blackhead"
                "whitehead" -> "Whitehead"
                "papula_pustula" -> "Papula & Pustula"
                else -> "Unknown Acne Type"
            }
            binding.textViewDescription.text = when (it.acneType) {
                "acne_nodules" -> getString(R.string.acne_nodule_desc)
                "milia" -> getString(R.string.acne_milia_desc)
                "blackhead" -> getString(R.string.acne_blackhead_desc)
                "whitehead" -> getString(R.string.acne_whitehead_desc)
                "papula_pustula" -> getString(R.string.acne_papula_pustula_desc)
                else -> ""
            }
        }

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = feedsAdapter
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private val LOG = AcneDetailActivity::class.java.simpleName
        const val EXTRA_ACNE_ITEM = "extra_acne_item"
    }
}