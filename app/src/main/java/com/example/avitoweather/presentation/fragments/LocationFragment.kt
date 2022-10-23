package com.example.avitoweather.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentLocationBinding
import com.example.avitoweather.presentation.viewModels.LocationViewModel
import com.example.avitoweather.presentation.viewModels.WeatherViewModel
import com.example.avitoweather.presentation.viewModelsFactory.ViewModelFactory
import com.example.avitoweather.utils.Utils.getLocation
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationImageView.setOnClickListener {
            findCurrentLocation()
        }
    }

    private fun findCurrentLocation(){
        CoroutineScope(Dispatchers.IO).launch{
            val result = getLocation(requireContext(), requireActivity())
            if (result.isEmpty()){
                showError()
            }else{
                Snackbar.make(requireView(), R.string.city_defined, Snackbar.LENGTH_SHORT).show()
                viewModel.sendLocation(result[0], result[1])
            }
        }
    }

    private fun showError() {
        Snackbar.make(requireView(), "Error", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }

        const val FRAGMENT_NAME = "LocationFragment"
    }
}