package com.capstone.acnetify.views.history_acne

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import android.view.ViewGroup
import com.capstone.acnetify.data.model.AcneImageModel
import com.capstone.acnetify.databinding.FragmentHistoryAcneBinding

class HistoryAcneFragment : Fragment() {

    private var _binding: FragmentHistoryAcneBinding? = null
    private val binding get() = _binding!!
    private val historyAcneAdapter = HistoryAcneAdapter()

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

        // Set up RecyclerView with GridLayoutManager and HistoryAcneAdapter
        binding.rvHistory.apply {
            // Set to grid layout with 2 columns
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = historyAcneAdapter
        }

        // Provide mock data
        historyAcneAdapter.submitList(getMockData())
    }

    private fun getMockData(): List<AcneImageModel> {
        return listOf(
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622547800000, "Acne Type A", "1"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622634200000, "Acne Type B", "2"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622720600000, "Acne Type C", "3"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622807000000, "Acne Type D", "4"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622893400000, "Acne Type E", "5"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622893400000, "Acne Type E", "5"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622893400000, "Acne Type E", "5"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622893400000, "Acne Type E", "5"),
            AcneImageModel("https://raw.githubusercontent.com/kisahtegar/machine-learning-android/barcode-scanning/preview.jpg", 1622893400000, "Acne Type E", "5"),
            // Add more items for testing
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}