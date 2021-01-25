package com.ydh.budayabyl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ydh.budayabyl.R
import com.ydh.budayabyl.databinding.FragmentLoginBinding
import com.ydh.budayabyl.databinding.FragmentNotificationBinding
import com.ydh.budayabyl.model.Notification
import com.ydh.budayabyl.view.adapter.NotificationAdapter

class NotificationFragment : Fragment(), NotificationAdapter.NotificationListener {

    lateinit var binding: FragmentNotificationBinding
    private val adapter by lazy { NotificationAdapter(requireContext(), this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        setView()

        return binding.root
    }

    private fun setView(){
        binding.run {
            rvNotification.adapter = adapter
        }
    }

    override fun onClick(model: Notification) {

    }

}