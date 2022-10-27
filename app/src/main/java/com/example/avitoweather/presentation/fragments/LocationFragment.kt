package com.example.avitoweather.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentLocationBinding
import com.example.avitoweather.domain.entites.LocationError
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.presentation.adapters.LocationListAdapter
import com.example.avitoweather.presentation.viewModels.LocationViewModel
import com.example.avitoweather.presentation.viewModelsFactory.ViewModelFactory
import com.example.avitoweather.utils.Utils.getLocation
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import javax.inject.Inject

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private val locationAdapter = LocationListAdapter()
    private val internetScope = CoroutineScope(Dispatchers.IO)

    override fun onDestroyView() {
        super.onDestroyView()
        internetScope.cancel()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LocationViewModel::class.java]
    }

    private val component by lazy {
        ((requireActivity().application) as App).component
    }

    override fun onAttach(context: Context) {
        component.injectLocationFragment(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupRecyclerView()
        observeLocation()
        observeLoadingFlow()
        findOrShowHistory()
    }

    //loading progress display
    private fun observeLoadingFlow() {
        lifecycleScope.launch {
            viewModel.isLoadingFlow.collect {
                setupVisibleProgressBar(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.locationRv.adapter = locationAdapter
        binding.locationRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        setupClickListenersRV()
    }

    private fun setupClickListenersRV() {
        locationAdapter.itemClickListener = {
            viewModel.sendLocation(it.lat, it.lon, it.label)
            lifecycleScope.launch(Dispatchers.Main) {
                backPressed()
            }
        }
        locationAdapter.deleteItemClickListener = {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteElementWithLabel(it.label)
                getHistoryListAndSubmit()
            }
        }
    }

    //setupClickListeners
    @SuppressLint("ClickableViewAccessibility")
    private fun setupClickListeners() {
        keyboardClickListener()
        binding.locationImageView.setOnClickListener {
            findCurrentLocation()
        }
        binding.searchEditText.addTextChangedListener {
            findOrShowHistory()
        }
        binding.searchEditText.setOnClickListener {
            findOrShowHistory()
        }
    }

    //when the text length is 3 or more, it is sent to the server to get the list of found locations,
    // otherwise the request history is loaded
    private fun findOrShowHistory() {
        val text = binding.searchEditText.text.toString()
        val isHistory = text.length <= 2
        locationAdapter.isHistory = isHistory
        if (!isHistory) {
            internetScope.launch { viewModel.findAndGetLocation(text) }
            binding.titleSearch.text = getString(R.string.founded)
        } else {
            getHistoryListAndSubmit()
            binding.titleSearch.text = getString(R.string.recently_request)
        }
    }

    private fun getHistoryListAndSubmit() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = viewModel.getHistoryList()
            locationAdapter.submitList(result)
        }
    }

    //determination of the current position, in case of an error, a snackbar is shown,
    // otherwise the current position is set and the transition to the fragment with the weather
    private fun findCurrentLocation() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = getLocation(requireContext(), requireActivity())
            withContext(Dispatchers.Main) {
                if (result.isEmpty()) {
                    showSnackbar(true)
                } else {
                    val lat = result[0]
                    val lon = result[1]
                    viewModel.sendLocation(lat, lon, CURRENT_POSITION)
                    showSnackbar(false)
                }
            }

        }
    }

    private fun showSnackbar(isError: Boolean) {
        if (isError) {
            Snackbar.make(requireView(), getString(R.string.error_location), Snackbar.LENGTH_SHORT)
                .show()
        } else {
            Snackbar.make(requireView(), R.string.city_defined, Snackbar.LENGTH_SHORT)
                .show()
            backPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    //when the search button is pressed on the keyboard, the first address in the list is selected
    private fun keyboardClickListener() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchButtonOnKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

    //when you click on the search button, if the list of found locations is not empty, the first one in the list is selected
    private fun searchButtonOnKeyboard() {
        if (locationAdapter.currentList.isEmpty()) return else {
            val location = locationAdapter.currentList[0]
            viewModel.sendLocation(location.lat, location.lon, location.label)
        }
        binding.searchEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        requireActivity().onBackPressed()
    }

    private fun setupVisibleProgressBar(boolean: Boolean) {
        binding.progressBar.isVisible = boolean
    }

    //if it is not currently a history mode, then the received locations are sent to the recyclerView
    private fun observeLocation() {
        lifecycleScope.launch {
            viewModel.findLocation.collect { listState ->
                if (!locationAdapter.isHistory) {
                    locationAdapter.submitList(newList(listState))
                }
            }
        }
    }

    private fun newList(listState: List<LocationState>?): List<LocationSuccess> {
        if (listState != null || listState?.get(0) !is LocationError) {
            val newList = mutableListOf<LocationSuccess>()
            if (listState != null) {
                for (element in listState) {
                    if (element is LocationError) {
                        return emptyList()
                    }
                    newList.add(element as LocationSuccess)
                }
            }
            return newList
        }
        return emptyList()
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }


    companion object {
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }

        const val FRAGMENT_NAME = "LocationFragment"
        private const val CURRENT_POSITION = "Current Position"
    }
}