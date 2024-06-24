package com.app.list_device_app.presentation.device_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.app.list_device_app.R
import com.app.list_device_app.databinding.DetailFragmentBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding
    private val argsDetails: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            imgPlatformDevice.load(argsDetails.deviceDetails.imgPlatform) {
                placeholder(R.drawable.empty_image)
                transformations(RoundedCornersTransformation(16f))
            }

            txtTitleDevice.text = argsDetails.deviceDetails.title
            txtSnDevice.text = argsDetails.deviceDetails.sn
            txtMacAddressDevice.text = argsDetails.deviceDetails.macAddress
            txtFirmwareDevice.text = argsDetails.deviceDetails.firmware
            txtModelDevice.text = argsDetails.deviceDetails.model
        }
    }
}