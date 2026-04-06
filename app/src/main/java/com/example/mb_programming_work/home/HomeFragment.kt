package com.example.mb_programming_work.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mb_programming_work.R
import com.example.mb_programming_work.databinding.HomeFragmentBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding

    private lateinit var adapter: CarAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //მoნაცემთა იმიტირებული სია
        val cars = listOf(
            Car(
                1,
                "BMW M3(F80 generation)",
                38000.0,
                R.mipmap.car1,
                contactPhoneNumber = "+995 551 456 467",
                email = "lorem.ipsum@gmail.com",
                description = "beautiful car with lots of functionalities."
            ),
            Car(
                2,
                "Mercedes-Benz CLA-Class (Second Generation)",
                46400.0,
                R.mipmap.car2,
                contactPhoneNumber = "+995 551 456 467",
                email = "lorem.ipsum@gmail.com",
                description = "beautiful car with lots of functionalities."
            ),
            Car(
                3,
                "Porsche 911 GT3 RS (991.1 Generation)",
                189000.0,
                R.mipmap.car3,
                contactPhoneNumber = "+995 551 456 467",
                email = "lorem.ipsum@gmail.com",
                description = "beautiful car with lots of functionalities."
            ),
            Car(
                4,
                "Ferrari 488 Spider",
                260000.0,
                R.mipmap.car4,
                contactPhoneNumber = "+995 551 456 467",
                email = "lorem.ipsum@gmail.com",
                description = "beautiful car with lots of functionalities."
            )
        )
        //ადაპტერის ინიციალიზაცია და განსაზღვრა იმისა,თუ რა უნდა მოხდეს კონკრეტულ აითემზე დაკლიკების შემთხვევაში.
        adapter = CarAdapter(onClick = { car ->
            //safeargs-ის საშუალებით ხდება car ობიექტის გადაყოლება სქრინიდან სქრინზე.
            val action = HomeFragmentDirections.actionHomeFragmentToPaymentFragment(car)
            findNavController().navigate(action)
        })

        //რისაიქლერის კონფიგურაცია
        binding.carsList.adapter = adapter
        binding.carsList.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.submitList(cars)

    }
}