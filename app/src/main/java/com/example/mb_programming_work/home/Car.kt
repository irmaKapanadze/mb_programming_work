package com.example.mb_programming_work.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*მანქანის კლასი არის Parcelable,რადგან მისი გადაყოლება უნდა იყოს შესაძლებელი სქრინიდან სქრინზე
safe args-ის გამოყენებით.
 */
@Parcelize
data class Car(
    val id: Int, //უნიკალური აიდი
    val title: String,
    val cost: Double,
    val photoResId: Int,
    val contactPhoneNumber: String,
    val email: String,
    val discountPercent: Int = 5, //ფასდაკლების პროცენტულობა,რომელიც დავალების
    // მიხედვით ყოველთვის 5 უნდა ყოფილიყო
    val description: String,
    val expressDeliveryPrice: Double = 1700.0, //ექსპრესის არჩევის შემთხვევაში,
    // მიტანის მომსახურების ღირებულება,რომელიც ყოველთვის იყო 1700
) : Parcelable