package com.example.mb_programming_work.payment_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mb_programming_work.R
import com.example.mb_programming_work.databinding.PaymentResultFragmentBinding

class PaymentResultFragment : Fragment() {
    private lateinit var binding: PaymentResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PaymentResultFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_paymentResultFragment_to_homeFragment)
        }
    }
}