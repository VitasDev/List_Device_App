package com.app.list_device_app.presentation.device_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.list_device_app.databinding.ListFragmentBinding
import com.app.list_device_app.presentation.DeviceUiState
import com.app.list_device_app.presentation.device_list.components.DeviceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var adapterDevice: DeviceAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterDevice = DeviceAdapter(mainViewModel)
        binding.apply {
            rvDevice.adapter = adapterDevice
            rvDevice.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.deviceUiState.collect { viewState ->
                    when (viewState) {
                        is DeviceUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.containerError.visibility = View.GONE
                            binding.rvDevice.visibility = View.VISIBLE
                            binding.txtEmptyList.visibility = View.GONE
                            adapterDevice.setData(viewState.deviceBases)
                        }

                        is DeviceUiState.Error -> {
                            Log.d("mskvmsl", "onViewCreated: " + viewState.exception.message.toString())
                            binding.progressBar.visibility = View.GONE
                            binding.containerError.visibility = View.VISIBLE
                            binding.rvDevice.visibility = View.GONE
                            binding.txtEmptyList.visibility = View.GONE
                        }

                        DeviceUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.containerError.visibility = View.GONE
                            binding.rvDevice.visibility = View.GONE
                            binding.txtEmptyList.visibility = View.GONE
                        }

                        DeviceUiState.Empty -> {
                            binding.txtEmptyList.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.containerError.visibility = View.GONE
                            binding.rvDevice.visibility = View.GONE
                            adapterDevice.clearData()
                        }
                    }
                }
            }
        }
    }
}