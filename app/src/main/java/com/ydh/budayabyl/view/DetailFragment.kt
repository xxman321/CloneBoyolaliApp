package com.ydh.budayabyl.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.findFragment
import com.bumptech.glide.Glide
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentDetailBinding
import com.ydh.budayabyl.model.Site


class DetailFragment : Fragment() {

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var binding: FragmentDetailBinding
    private lateinit var model: Site
    private lateinit var mapFragmentView : SupportMapFragment
    lateinit var siteMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setView()
        checkLocationPermission()

        setObserver()
        return binding.root
    }

    private fun setView(){
        binding.run {

            arguments?.let { bundle ->
                val args = DetailFragmentArgs.fromBundle(bundle)
                model = args.site
            }
            tvSiteTitle.text = model.name_id
            tvSiteDescription.text = model.description
            btBack.setOnClickListener {
                activity?.onBackPressed()
            }

            Glide.with(requireContext())
                .load(model.image)
                .into(ivDetailSite)

            mapFragmentView = childFragmentManager.findFragmentByTag("map_fragment") as SupportMapFragment
            mapFragmentView.getMapAsync {
                siteMap = it

                val siteLocation = LatLng(-7.4945912, 110.4801672)
                siteMap.addMarker(MarkerOptions().position(siteLocation).title("Candi Lawang"))
                siteMap.moveCamera(CameraUpdateFactory.newLatLngZoom(siteLocation, 15f))

                siteMap.uiSettings.isCompassEnabled = true
                siteMap.uiSettings.isZoomControlsEnabled = true
                siteMap.uiSettings.isRotateGesturesEnabled = true
                siteMap.uiSettings.isZoomGesturesEnabled = true
                siteMap.uiSettings.isMapToolbarEnabled = true

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    siteMap.isMyLocationEnabled = true
                }

            }


        }
    }

    private fun setObserver(){

    }

    private fun checkLocationPermission():Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),LOCATION_PERMISSION_REQUEST_CODE)
            else
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),LOCATION_PERMISSION_REQUEST_CODE)
            false
        }else
            true
    }



}