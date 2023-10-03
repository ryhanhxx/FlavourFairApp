package com.ch.flavourfair.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ch.flavourfair.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
*/
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

}