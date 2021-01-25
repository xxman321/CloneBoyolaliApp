package com.ydh.budayabyl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentCategoryBinding
import com.ydh.budayabyl.utils.Constant
import com.ydh.budayabyl.view.adapter.PagerCategoryAdapter
import com.ydh.budayabyl.viewmodel.ShareViewModel
import com.ydh.budayabyl.viewmodel.SiteViewModel
import com.ydh.budayabyl.viewmodel.state.SiteState
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    private val viewModel by viewModel<SiteViewModel>()
    private val shareViewModel by activityViewModels<ShareViewModel>()

    private var role = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        setView()
        setViewPagerCategory()
        setObserver()
        return binding.root
    }

    private fun setView() {
        binding.apply {
        }
    }

    private fun setObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SiteState.Loading -> {

                }
                is SiteState.Error -> {

                    showMessage(it.toString())
                }
                is SiteState.SuccessGetAllSite -> {


                }
                is SiteState.SuccessGetSite -> {

                }
                else -> throw Exception("Unsupported state type")
            }
        }
        shareViewModel.userMutableLiveData.observe(viewLifecycleOwner, { user ->
            role = user.role.toString()
//            binding.include.root.visibility = if (role == "buyer") View.VISIBLE else View.GONE
        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setViewPagerCategory() {
        val tabName = arrayOf(
            Constant.TYPE_A,
            Constant.TYPE_B,
            Constant.TYPE_C
        )
        binding.run {
            val adapter = PagerCategoryAdapter(tabName, childFragmentManager)
//            vpCategoryTab.adapter = adapter
//            vpCategoryTab.addOnPageChangeListener(
//                TabLayout.TabLayoutOnPageChangeListener(
//                    tlProductCategory
//                )
//            )
//
//            tlProductCategory.setupWithViewPager(vpCategoryTab)
//            tlProductCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    tab?.position?.let {
//                        vpCategoryTab.currentItem = it
//                    }
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab?) {
//                }
//
//                override fun onTabReselected(tab: TabLayout.Tab?) {
//                }
//
//            })
        }
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}