package com.capstone.acnetify.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.acnetify.data.model.ReviewsModel
import com.capstone.acnetify.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val feedsAdapter = ReviewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = feedsAdapter
        }

        // Provide mock data
        feedsAdapter.submitList(getMockData())
    }

    private fun getMockData(): List<ReviewsModel> {
        return listOf(
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
            ReviewsModel("1", "kisahtegar", 31491, "Acne-A", "2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Mauris", 2, true),
        )
    }
}