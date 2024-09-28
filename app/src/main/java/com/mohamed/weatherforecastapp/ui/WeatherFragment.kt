package com.mohamed.weatherforecastapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mohamed.core.utils.NetworkState
import com.mohamed.weatherforecastapp.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val spinnerAdapter by lazy { CitySpinnerAdapter() }
    private val weatherAdapter by lazy { WeatherAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
        observeWeather()
        observeCities()
        setupRetryView()
    }

    private fun setupRetryView() {
        binding.emptyView.retryBtn.setOnClickListener { viewModel.retry() }
    }

    private fun observeWeather() {
        lifecycleScope.launch {
            viewModel.weatherState.collectLatest { state ->
                when (state) {
                    is NetworkState.Success -> {
                        if (state.data.first.not())
                            showErrorLayout("Data not real")
                        else
                            hideErrorMessage()
                        weatherAdapter.submitList(state.data.second)
                        updateUIForSuccess()
                    }

                    is NetworkState.Error -> {
                        showErrorLayout(state.message)
                        updateUIForError()
                    }

                    else -> hideAllUIStates()
                }
            }
        }
    }

    private fun updateUIForSuccess() {
        showRetry(false)
    }

    private fun updateUIForError() {
        showRetry(true)
        weatherAdapter.clear()
    }

    private fun hideAllUIStates() {
        showRetry(false)
    }

    private fun observeCities() {
        lifecycleScope.launch {
            viewModel.cityState.collectLatest { state ->
                when (state) {
                    is NetworkState.Success -> {
                        spinnerAdapter.setItems(state.data)
                        updateUIForSuccess()
                        hideErrorMessage()
                    }

                    is NetworkState.Error -> {
                        showErrorLayout(state.message)
                        updateUIForError()
                    }

                    else -> hideAllUIStates()
                }
            }
        }
    }

    private fun setupSpinner() {
        binding.spinnerCities.adapter = spinnerAdapter
        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCity = spinnerAdapter.getItem(position)
                viewModel.getWeather(selectedCity.lat.toString(), selectedCity.lon.toString())
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewWeather.adapter = weatherAdapter
    }

    private fun hideErrorMessage() {
        binding.errorText.isVisible = false
    }

    private fun showErrorLayout(message: String?) {
        binding.errorText.text = message
        binding.errorText.isVisible = !message.isNullOrBlank()
    }

    private fun showRetry(show: Boolean) {
        binding.emptyView.root.isVisible = show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
