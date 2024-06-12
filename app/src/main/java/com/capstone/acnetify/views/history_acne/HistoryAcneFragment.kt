package com.capstone.acnetify.views.history_acne

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.capstone.acnetify.databinding.FragmentHistoryAcneBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * HistoryAcneFragment is responsible for displaying the user's acne history.
 *
 * This Fragment shows a grid of acne images using a RecyclerView. It uses the Paging library for efficient
 * data loading and handles different loading states to provide a smooth user experience.
 * The ViewModel associated with this Fragment handles data fetching and business logic.
 */
@AndroidEntryPoint
class HistoryAcneFragment : Fragment() {

    // View binding instance to access UI elements
    private var _binding: FragmentHistoryAcneBinding? = null
    private val binding get() = _binding!!

    // Adapter for the RecyclerView to display acne history images
    private val historyAcneAdapter = HistoryAcneAdapter()

    // ViewModel for this fragment, scoped to this fragment's lifecycle
    private val viewModel: HistoryAcneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryAcneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components and observe data
        setupRecyclerView()
        observeViewModel()
        setupRefreshListener()
    }

    /**
     * Called when the view is destroyed to clean up the view binding and avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Configures the RecyclerView with a GridLayoutManager, fixed size optimization, and the HistoryAcneAdapter.
     */
    private fun setupRecyclerView() {
        // Set up RecyclerView with GridLayoutManager and HistoryAcneAdapter
        binding.rvHistory.apply {
            // Set to grid layout with 2 columns
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = historyAcneAdapter
        }
    }

    /**
     * Observes the ViewModel for changes in the history data and submits new data to the adapter.
     * Also listens for loading state changes to handle UI updates based on data load status.
     */
    private fun observeViewModel() {
        // Observe paginated history data from ViewModel
        viewModel.histories.observe(viewLifecycleOwner) {
            historyAcneAdapter.submitData(lifecycle, it)
            binding.rvHistory.scrollToPosition(0)
        }

        // Listen to load state changes and handle error states
        lifecycleScope.launch {
            historyAcneAdapter.loadStateFlow.collectLatest {
                handleLoadStates(it)
            }
        }
    }

    /**
     * Sets up the SwipeRefreshLayout to refresh the history data when a swipe-to-refresh gesture is detected.
     */
    private fun setupRefreshListener() {
        binding.swipeRefreshFeeds.setOnRefreshListener {
            historyAcneAdapter.refresh()
        }
    }

    /**
     * Handles different loading states of the PagingDataAdapter and updates the UI accordingly.
     * This function is responsible for showing or hiding the RecyclerView and error UI elements
     * based on the current load state. It also provides specific error messages to the user.
     *
     * @param loadStates The current loading states of the PagingDataAdapter.
     */
    private fun handleLoadStates(loadStates: CombinedLoadStates) {
        // Check if the refresh state is an error state
        val isError = loadStates.refresh is LoadState.Error
        // Show or hide the swipe-to-refresh loading indicator based on the refresh load state
        binding.swipeRefreshFeeds.isRefreshing = loadStates.refresh is LoadState.Loading

        // Toggle visibility of the RecyclerView and error UI elements based on the error state
        binding.rvHistory.visibility = if (isError) View.GONE else View.VISIBLE
        binding.errorImageView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorTextView.visibility = if (isError) View.VISIBLE else View.GONE

        // If there is an error state, determine the type of error and display an appropriate message
        val errorState = loadStates.refresh as? LoadState.Error
        errorState?.let {
            val errorMessage = when (it.error) {
                is HttpException -> {
                    // Handle HTTP exceptions with specific error codes
                    val errorCode = (it.error as HttpException).code()
                    Log.d("HistoryAcneFragment", "HttpException: Code $errorCode")
                    if (errorCode == 401) "Please log in to view history." else "Error loading data. Please try again."
                }
                is IOException -> "No internet connection."
                else -> "An unexpected error occurred."
            }
            // Set the error message to the TextView to inform the user
            binding.errorTextView.text = errorMessage
        }
    }
}