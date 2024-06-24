package com.app.list_device_app.presentation.device_list.components

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.app.list_device_app.R
import com.app.list_device_app.data.remote.dto.DeviceBase
import com.app.list_device_app.databinding.DialogDeleteItemBinding
import com.app.list_device_app.databinding.ItemDeviceBinding
import com.app.list_device_app.domain.model.DeviceDetails
import com.app.list_device_app.presentation.device_list.ListFragmentDirections
import com.app.list_device_app.presentation.device_list.MainViewModel

class DeviceAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private var listDeviceBase = emptyList<DeviceBase>().toMutableList()

    class DeviceViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val itemBinding =
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val currentItem = listDeviceBase[position]
        val context = holder.binding.root.context
        val titleItem = currentItem.title
        val snItem =
            context.getString(R.string.sn, currentItem.pkDevice.toString())
        val macAddressItem =
            context.getString(R.string.mac_address, currentItem.macAddress)
        val firmwareItem =
            context.getString(R.string.firmware, currentItem.firmware)
        val modelItem =
            context.getString(R.string.model, getTextModel(context, currentItem.platform))
        val imageItem = getImagePlatform(context, currentItem.platform)

        with(holder) {
            binding.apply {
                imgPlatformDevice.load(imageItem) {
                    placeholder(R.drawable.empty_image)
                    transformations(RoundedCornersTransformation(16f))
                }

                itemContainer.setOnLongClickListener {
                    showDeleteOptionDialog(context, currentItem)
                    true
                }

                itemContainer.setOnClickListener {
                    val itemDeviceDetails = DeviceDetails(
                        imageItem!!,
                        titleItem,
                        snItem,
                        macAddressItem,
                        firmwareItem,
                        modelItem
                    )
                    val action = ListFragmentDirections.actionListFragmentToDetailsFragment(itemDeviceDetails)
                    findNavController(it).navigate(action)
                }
                txtTitleDevice.text = titleItem
                txtSnDevice.text = snItem
            }
        }
    }

    private fun showDeleteOptionDialog(context: Context, deviceItem: DeviceBase) {
        val dialog = Dialog(context)
        val binding: DialogDeleteItemBinding = DialogDeleteItemBinding.inflate(LayoutInflater.from(context))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(binding.root)

        binding.btnDelete.setOnClickListener {
            mainViewModel.deleteDeviceFromDb(deviceItem)
            dialog.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getImagePlatform(context: Context, platform: String) : Drawable? {
        return when (platform) {
            context.getString(R.string.sercomm_g450) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_plus_big)
            }
            context.getString(R.string.sercomm_g550) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_secure_big)
            }
            context.getString(R.string.micasaverde_veralite) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_edge_big)
            }
            context.getString(R.string.sercomm_na900) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_edge_big)
            }
            context.getString(R.string.sercomm_na301) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_edge_big)
            }
            context.getString(R.string.sercomm_na930) -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_edge_big)
            }
            else -> {
                AppCompatResources.getDrawable(context, R.drawable.vera_edge_big)
            }
        }
    }

    private fun getTextModel(context: Context, platform: String) : String {
        return when (platform) {
            context.getString(R.string.sercomm_g450) -> {
                context.getString(R.string.vera_plus)
            }
            context.getString(R.string.sercomm_g550) -> {
                context.getString(R.string.vera_secure)
            }
            context.getString(R.string.micasaverde_veralite) -> {
                context.getString(R.string.vera_edge)
            }
            context.getString(R.string.sercomm_na900) -> {
                context.getString(R.string.vera_edge)
            }
            context.getString(R.string.sercomm_na301) -> {
                context.getString(R.string.vera_edge)
            }
            context.getString(R.string.sercomm_na930) -> {
                context.getString(R.string.vera_edge)
            }
            else -> {
                context.getString(R.string.vera_edge)
            }
        }
    }

    fun setData(listDeviceBase: List<DeviceBase>) {
        this.listDeviceBase = listDeviceBase.toMutableList()
        notifyDataSetChanged()
    }

    fun clearData() {
        this.listDeviceBase.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listDeviceBase.size
    }
}