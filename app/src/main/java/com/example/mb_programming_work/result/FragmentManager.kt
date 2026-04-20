package com.example.mb_programming_work.result

import com.example.mb_programming_work.home.FinanceModel

class FinanceManager(
    model: FinanceModel,
    lNameSymbols: Int, //count of symbols in our last name
    monthNumber: Int,  //our birth month number
) {
    /*pirobashi zustad ar iyo mititebuli am kalkulaciebidan romeli unda gametana shedegis
    screen-ze,amitom gadavwyvite qveda 3 kalkulacia da xelpasi gametana.
     */
    //according to formula
    val savingsPercent = lNameSymbols + monthNumber

    //total expenses=rent+food
    val totalExpenses = model.rent + model.food

    //(Salary - Expenses) * (savingsPercent / 100.0)
    val totalSavings = (model.salary - totalExpenses) * (savingsPercent / 100.0)

}