package com.ydh.budayabyl.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentListCategoryBinding
import com.ydh.budayabyl.model.Site
import com.ydh.budayabyl.utils.SpacesItemDecoration
import com.ydh.budayabyl.view.adapter.SiteAdapter
import com.ydh.budayabyl.viewmodel.SiteViewModel
import com.ydh.budayabyl.viewmodel.state.SiteState
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListCategoryFragment : Fragment(), SiteAdapter.SiteListener {
    lateinit var binding: FragmentListCategoryBinding
    private val viewModel by viewModel<SiteViewModel>()
    private val adapter by lazy { SiteAdapter(requireContext(), this) }

    private var currentPage = 1
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = if (savedInstanceState != null) savedInstanceState.getString(EXTRA_TYPE_CATEGORY)
            ?: "" else arguments?.getString(
            EXTRA_TYPE_CATEGORY
        ) ?: ""
    }

    fun onSavedInstanceFragment(type: String): ListCategoryFragment {
        val fragment = ListCategoryFragment()
        val args = Bundle()

        args.putString(EXTRA_TYPE_CATEGORY, type)
        fragment.arguments = args
        return fragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(EXTRA_TYPE_CATEGORY, type)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)

        setView()
        setObserver()
        return binding.root
    }

    companion object {
        private const val EXTRA_TYPE_CATEGORY = "ExtraTypeCategory"
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() = viewModel.getAllList()

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is SiteState.SuccessGetAllSite -> {
                    showLoading(false)
                    adapter.list = it.list.toMutableList()
                }
                is SiteState.Loading -> showLoading(true)
                is SiteState.Error -> {
                    showLoading(false)
                    println(it.exception.toString())}
                else -> println("error")
            }
        }

    }

    private fun setView(){

        binding.run {
            rvSiteList.adapter = adapter

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
            rvSiteList.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        }

    }

    override fun onClick(model: Site) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(model)
        findNavController().navigate(action)
    }

    private fun showLoading(state: Boolean){
        binding.apply {
            pbListCategory.visibility = if (state) View.VISIBLE else View.GONE
            rvSiteList.visibility = if (state) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.onClear()
    }
}