package com.example.avitoweather.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentLocationBinding
import com.example.avitoweather.domain.entites.LocationError
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.presentation.viewModels.LocationViewModel
import com.example.avitoweather.presentation.viewModelsFactory.ViewModelFactory
import com.example.avitoweather.utils.Utils.getLocation
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import javax.inject.Inject

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

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
        observeLocation()
    }

    private fun setupClickListeners() {
        keyboardClickListener()
        binding.locationImageView.setOnClickListener {
            findCurrentLocation()
        }
    }

    private fun findCurrentLocation() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getLocation(requireContext(), requireActivity())
            if (result.isEmpty()) {
                showError()
            } else {
                viewModel.sendLocation(result[0], result[1])
                withContext(Dispatchers.Main) {
                    Snackbar.make(requireView(), R.string.city_defined, Snackbar.LENGTH_SHORT)
                        .show()
                    delayAndBackPressed()
                }
            }
        }
    }

    private fun showError() {
        Snackbar.make(requireView(), "Произошла ошибка", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun keyboardClickListener() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                sendFindAndSetLocation()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun sendFindAndSetLocation(){
        viewModel.findAndSetLocation(binding.searchEditText.text.toString())
        binding.searchEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        setupVisibleProgressBar(true)
    }

    private fun setupVisibleProgressBar(boolean: Boolean){
        binding.progressBar.isVisible = boolean
    }

    private fun observeLocation() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.findLocation.collect { state->
                setupVisibleProgressBar(false)
                when {
                    state is LocationError -> locationError()
                    state is LocationSuccess -> locationSuccess(state)
                }
            }
        }
    }

    private fun locationError(){
        showSnackBar(getString(R.string.location_error))
        viewModel.resetStateFlow()
    }
    private fun locationSuccess(state: LocationSuccess){
        showSnackBar(state.label)
        requireActivity().onBackPressed()
    }

    private fun showSnackBar(str: String){
        Snackbar.make(requireView(), str, Snackbar.LENGTH_SHORT).show()
    }

    private suspend fun delayAndBackPressed() {
        delay(Snackbar.LENGTH_SHORT.toLong())
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }

        const val FRAGMENT_NAME = "LocationFragment"
    }
}