package com.capstone.acnetify.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.acnetify.R
import com.capstone.acnetify.databinding.FragmentHomeBinding
import com.capstone.acnetify.views.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        setupLoadStateListener()

        setupDropdownMenu()
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

    private fun setupLoadStateListener() {
        feedsAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Error -> {
                    // Stop the refresh animation if there is an error
                    binding.swipeRefreshFeeds.isRefreshing = false

                    // Show the error message
                    binding.rvReviews.visibility = View.GONE
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    // Show the refresh animation while loading
                    binding.swipeRefreshFeeds.isRefreshing = true

                    // Hide the error message
                    binding.rvReviews.visibility = View.VISIBLE
                    binding.errorImageView.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                }
                is LoadState.NotLoading -> {
                    // Scroll to the top of the list
                    binding.rvReviews.scrollToPosition(0)
                    // Stop the refresh animation
                    binding.swipeRefreshFeeds.isRefreshing = false
                }
            }
        }
    }

    private fun setupDropdownMenu() {
        val acneTypes = resources.getStringArray(R.array.acne_types_array)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_acne, acneTypes)
        binding.acneTypeDropdown.setAdapter(adapter)

        binding.acneTypeDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedAcneType = acneTypes[position]
            viewModel.searchReviews(selectedAcneType)
        }
    }
}