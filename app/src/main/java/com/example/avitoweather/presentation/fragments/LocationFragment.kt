package com.example.avitoweather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentLocationBinding
import com.example.avitoweather.utils.Utils.getLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationImageView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val a = getLocation(requireContext(), requireActivity())
                println(a.toString())
            }
        }
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