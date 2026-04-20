package com.example.mb_programming_work.result

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mb_programming_work.R
import com.example.mb_programming_work.databinding.ResultFragmentBinding

class ResultFragment : Fragment() {
    private lateinit var binding: ResultFragmentBinding

    //argumentebis amosagebad
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = ResultFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val financeData = args.financeData
        //gvari->kapanadze,dabadebis tve -> November
        val manager = FinanceManager(financeData, lNameSymbols = 8, monthNumber = 11)

        binding.apply {
            ikSalaryAmount.text = getString(R.string.blank_string, financeData.salary)
            ikExpensesAmount.text = getString(R.string.blank_string, manager.totalExpenses)
            ikTotalSavingAmount.text = getString(R.string.blank_string, manager.totalSavings)
            ikSavingPercent.text = getString(R.string.percent, manager.savingsPercent)

            val statusColor = if (financeData.salary < manager.totalExpenses) {
                Color.RED
            } else {
                Color.GREEN
            }

            ikSalaryAmount.setTextColor(statusColor)
            ikExpensesAmount.setTextColor(statusColor)
            ikSavingPercent.setTextColor(statusColor)
            ikTotalSavingAmount.setTextColor(statusColor)

            ikFullName.text = "irma kapanadze"
            ikBirthYear.text = "2006"
        }
    }
}