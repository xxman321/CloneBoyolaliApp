package com.ydh.budayabyl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentHomeBinding
import com.ydh.budayabyl.model.Site
import com.ydh.budayabyl.service.FirebaseSiteService
import com.ydh.budayabyl.service.FirebaseUserService
import com.ydh.budayabyl.utils.Constant
import com.ydh.budayabyl.view.adapter.PagerCategoryAdapter
import com.ydh.budayabyl.view.adapter.SiteAdapter
import com.ydh.budayabyl.viewmodel.*
import com.ydh.budayabyl.viewmodel.state.SiteState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SiteAdapter.SiteListener {
    private lateinit var binding: FragmentHomeBinding

    private val adapter by lazy { SiteAdapter(requireActivity(), this) }
    private val siteViewModel by viewModel<SiteViewModel>()
    private val shareViewModel by activityViewModels<ShareViewModel>()

    private var role = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setView()
        setViewPagerCategory()
        setObserver()

        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()
        siteViewModel.getAllList()
    }

    private fun setView(){
        binding.run{
//            rv.adapter = adapter
            include.root.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotificationFragment()) }


        }

    }

    private fun setViewPagerCategory(){

        val tabName = arrayOf(
            Constant.TYPE_A,
            Constant.TYPE_B,
            Constant.TYPE_C
        )
        binding.run {
            val adapter = PagerCategoryAdapter(tabName, childFragmentManager)
            vpCategoryTab.adapter = adapter
            vpCategoryTab.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    tlProductCategory
                )
            )

            tlProductCategory.setupWithViewPager(vpCategoryTab)
            tlProductCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        vpCategoryTab.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    private fun setObserver(){
        siteViewModel.state.observe(viewLifecycleOwner){
            when(it){
                is SiteState.SuccessGetAllSite -> {
                    adapter.list = it.list.toMutableList()
                }
                is SiteState.Loading -> println(it.message)
                is SiteState.Error -> println(it.exception.toString())
                else -> println("error")
            }
        }
        shareViewModel.userMutableLiveData.observe(viewLifecycleOwner, { user ->
            role = user.role.toString()
//            binding.include.root.visibility = if (role == "buyer") View.VISIBLE else View.GONE
        })

    }

    override fun onClick(model: Site) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(model)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()

        siteViewModel.onClear()
        shareViewModel.onClear()
    }


}