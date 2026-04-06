package com.example.mb_programming_work.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mb_programming_work.databinding.PaymentFragmentBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mb_programming_work.R
import com.example.mb_programming_work.home.Car

class PaymentFragment : Fragment() {

    private lateinit var binding: PaymentFragmentBinding

    //გამოიყენება წინა სქრინიდან გადმოყოლებული არგუმენტების ამოსაღებად
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PaymentFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ამოიღება 'Car'-ის ობიექტი
        val car = args.selectedCar

        binding.apply {
            //სტატიკური მონაცემების შევსება
            phone.text = car.contactPhoneNumber
            gmail.text = car.email
            photo.setImageResource(car.photoResId)
            description.text = car.description

            discount.text = getString(R.string.discount, car.discountPercent)
            cost.text = getString(R.string.cost, car.cost)
            expressShippingCost.text = getString(R.string.cost, car.expressDeliveryPrice)

            //დეფოლტად ინიშნება სტანდარტ მიწოდება
            standardCheckBox.isChecked = true
            refreshTotal(car)

            /*Shipping არჩევის ლოგიკა(თუ ინიშნება 1,მოუნიშნავი რჩება მე-2).საჭიროა ამის გაწერა,
            ვინაიდან გამოყენებულია custom selectors.
             */

            standardCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    expressCheckBox.isChecked = false
                    refreshTotal(car)
                }
            }

            expressCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    standardCheckBox.isChecked = false
                    refreshTotal(car)
                }
            }

            //გადახდის ღილაკზე კლიკის შემთხვევაში განსახორციელებელი ლოგიკა.
            payButton.setOnClickListener {
                findNavController().navigate(R.id.action_paymentFragment_to_paymentResultFragment)
            }
        }
    }

    //საბოლოო ფასის გამოასანგარიშებელი ლოგიკა
    private fun Car.calculateFinalPrice(isExpress: Boolean): Double {
        //მათემატიკურად ფასდაკლებული ფასის გამოთვლა
        val discounted = cost * (1 - discountPercent / 100.0)

        //თუ ექსპრეს მიწოდებაა მონიშნული,ემატება მიწოდების ფასი,სხვა შემთხვევაში კი საბოლოო ფასი რჩება იგივე.
        return if (isExpress) discounted + expressDeliveryPrice else discounted
    }

    private fun refreshTotal(car: Car) {
        //საბოლოო ფასი extension  ფუნქციით გამოითვლება და ისეტება ვიზუალურ კომპონენტში
        val finalAmount = car.calculateFinalPrice(binding.expressCheckBox.isChecked)
        binding.totalCost.text = getString(R.string.cost, finalAmount)
    }
}