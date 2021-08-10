package com.example.androidassessment.main.userdetails

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseFragment
import com.example.androidassessment.common.AlertDialogFactory
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.databinding.FragmentUserDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(), OnMapReadyCallback {

    @Inject
    lateinit var alertDialogFactory: AlertDialogFactory

    @Inject
    lateinit var gson: Gson

    lateinit var user: NetworkUser

    override fun getToolbarTitle(): Int = R.string.user_details_title

    override fun getViewBinding(): FragmentUserDetailsBinding {
        return FragmentUserDetailsBinding.inflate(layoutInflater)
    }

    override fun enableBackButton(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        binding.map.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.details_to_list)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }


    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val v = super.onViewCreated(view, savedInstanceState)
        user = gson.fromJson(arguments?.getString("userDetails"), NetworkUser::class.java)
        binding.map.getMapAsync(this)
        return v
    }

    override fun onMapReady(map: GoogleMap?) {
        val sydney = LatLng(user.address.geo.lat, user.address.geo.long)
        map?.addMarker(
            MarkerOptions().position(sydney)
                .title("User position")
        )
        map?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
