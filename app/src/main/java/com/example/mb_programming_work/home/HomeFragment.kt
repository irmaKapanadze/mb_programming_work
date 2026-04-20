package com.example.mb_programming_work.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mb_programming_work.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mb_programming_work.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //calculate-ze click-is shemtxvevashi,tu velebi iqneba carieli,gamoitans shecdomas.
            ikCalculateButton.setOnClickListener {
                if (ikSalaryInput.text.isNullOrEmpty() ||
                    ikRentInput.text.isNullOrEmpty() ||
                    ikFoodInput.text.isNullOrEmpty()
                ) {

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_text),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    // toDoubleOrNull() usaprtxoebis kidev erti garanti
                    val salary = ikSalaryInput.text.toString().toDoubleOrNull() ?: 0.0
                    val rent = ikRentInput.text.toString().toDoubleOrNull() ?: 0.0
                    val food = ikFoodInput.text.toString().toDoubleOrNull() ?: 0.0

                    //moxdeba navigacia argumentebit results screen-ze
                    val financeModel = FinanceModel(salary, rent, food)
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToResultFragment(financeModel)
                    )
                }
            }
        }
    }
}