package com.capstone.acnetify.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.acnetify.databinding.FragmentHomeBinding
import com.capstone.acnetify.views.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] subclass representing the Home screen.
 *
 * This fragment displays a list of reviews using a RecyclerView. It observes a [HomeViewModel] for data,
 * handles refresh actions, and manages view binding.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View binding for this fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Adapter for the RecyclerView to display reviews
    private val feedsAdapter = ReviewsAdapter()

    // ViewModel for this fragment, scoped to this fragment's lifecycle
    private val viewModel: HomeViewModel by viewModels()

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

        setupRecyclerView()
        observeViewModel()

        // Refresh stories when user swipes down
        binding.swipeRefreshFeeds.setOnRefreshListener {
            feedsAdapter.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Clear the binding when the view is destroyed to avoid memory leaks
        _binding = null
    }

    /**
     * Sets up the RecyclerView with a LinearLayoutManager, fixed size, and the adapter.
     */
    private fun setupRecyclerView() {
        // Set up RecyclerView
        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = feedsAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {feedsAdapter.retry()}
            )
        }
    }

    /**
     * Observes the ViewModel for data changes and submits new data to the adapter.
     */
    private fun observeViewModel() {
        viewModel.allReviews.observe(viewLifecycleOwner) {
            feedsAdapter.submitData(lifecycle, it)
            binding.rvReviews.scrollToPosition(0)
        }
    }
}