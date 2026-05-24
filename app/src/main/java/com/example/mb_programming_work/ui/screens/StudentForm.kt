package com.example.mb_programming_work.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.vm.StudentFormViewModel
import java.util.Calendar

@Composable
fun StudentForm(
    modifier: Modifier = Modifier,
    viewModel: StudentFormViewModel = viewModel(),
) {
    //uzrunvelyops recomposition-s monacemta cvlilebisas
    val nameState by viewModel.nameState.collectAsState()
    val dateState by viewModel.dateState.collectAsState()
    val emailState by viewModel.emailState.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isAgreed by viewModel.isAgreed.collectAsState()

    //mimartulebebis chamonatvali
    val directions = listOf("Android", "IOS", "Web")

    //vinaidan 1 screen-ze sakmaod bevri field iyo shesavsebi,screen gavxade scrollvadi.
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    //DatePickerDialog-is inicializacia morgebuli stilita da tarighis pormatirebit
    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CustomDatePickerTheme,//gadaveci unikaluri perebi,rom ar gamoeyenebina materialis perebi
        { _, year, month, dayOfMonth ->
            val formattedDay = String.format("%02d", dayOfMonth)
            val formattedMonth =
                String.format("%02d", month + 1)//indeqsacia 0-idan iwyeba,amitom tves emateba 1
            viewModel.onDateChange("$formattedDay/$formattedMonth/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    /*mtavari konteineri vertical scroll-is mxardawerit.yvela komponentshi romelic dablaa gamoyenebuli vcvlit
    material default ferebs*/
    Column(
        modifier
            .fillMaxSize()
            .background(MyTheme.colors.background)
            .verticalScroll(scrollState)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MyTheme.colors.primary)
                .padding(horizontal = 24.dp, vertical = 22.dp)
        ) {
            Text(
                text = stringResource(R.string.form_title),
                style = MyTheme.typography.headlineMedium,
                color = MyTheme.colors.onPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.form_subtitle),
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onPrimary
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.label_name),
            style = MyTheme.typography.bodyLarge,
            color = MyTheme.colors.onBackground,
            modifier = Modifier.padding(12.dp, 8.dp)
        )
        OutlinedTextField(
            value = nameState,
            onValueChange = { viewModel.onNameChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            placeholder = { Text(stringResource(R.string.placeholder_name)) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MyTheme.colors.surface,
                unfocusedContainerColor = MyTheme.colors.surface,
                unfocusedBorderColor = MyTheme.colors.primary,
                focusedBorderColor = MyTheme.colors.primary
            )
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.label_date),
            style = MyTheme.typography.bodyLarge,
            color = MyTheme.colors.onBackground,
            modifier = Modifier.padding(12.dp, 8.dp)
        )

        //box-shi vputavt teqstur vels,rom clickma imushavos mtel velze
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .clickable { datePickerDialog.show() } //kalendris chveneba
        ) {
            OutlinedTextField(
                value = dateState,
                onValueChange = {}, //carielia,radgan xelit arapers vwert
                readOnly = true,
                enabled = false, // etisheba shida click rom box interaqcia ar daiblokos
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.placeholder_date)) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_svgrepo_com),
                        contentDescription = "Calendar Icon",
                        tint = MyTheme.colors.textSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = MyTheme.colors.surface,
                    disabledBorderColor = MyTheme.colors.primary,
                    disabledTextColor = MyTheme.colors.onBackground,
                    disabledPlaceholderColor = MyTheme.colors.textSecondary,
                    disabledTrailingIconColor = MyTheme.colors.textSecondary
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.label_email),
            style = MyTheme.typography.bodyLarge,
            color = MyTheme.colors.onBackground,
            modifier = Modifier.padding(12.dp, 8.dp)
        )
        OutlinedTextField(
            value = emailState,
            onValueChange = { viewModel.onEmailChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            placeholder = { Text(stringResource(R.string.placeholder_email)) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MyTheme.colors.surface,
                unfocusedContainerColor = MyTheme.colors.surface,
                unfocusedBorderColor = MyTheme.colors.primary,
                focusedBorderColor = MyTheme.colors.primary
            )
        )

        Spacer(Modifier.height(16.dp))


        Text(
            text = stringResource(R.string.radio_title),
            style = MyTheme.typography.bodyLarge,
            color = MyTheme.colors.textPrimary,
            modifier = Modifier.padding(start = 12.dp, top = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        directions.forEach { direction ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onOptionSelect(direction) }
                    .padding(horizontal = 12.dp, vertical = 1.dp)
            ) {
                RadioButton(
                    selected = (selectedOption == direction),
                    onClick = { viewModel.onOptionSelect(direction) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MyTheme.colors.primary,
                        unselectedColor = MyTheme.colors.outline
                    )
                )
                Text(
                    text = direction,
                    style = MyTheme.typography.bodyLarge,
                    color = MyTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.switch_terms),
                style = MyTheme.typography.bodyLarge,
                color = MyTheme.colors.textPrimary
            )
            Switch(
                checked = isAgreed,
                onCheckedChange = { viewModel.onAgreementChange(it) },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MyTheme.colors.primary,
                    checkedThumbColor = MyTheme.colors.accent,
                    uncheckedTrackColor = MyTheme.colors.outline.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                //shedegebis mixedvit gamoaqvs toast
                if (viewModel.validateForm()) {
                    Toast.makeText(context, "Data sent!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Fill all the fields!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MyTheme.colors.primary,
                contentColor = MyTheme.colors.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.btn_submit),
                style = MyTheme.typography.labelLarge
            )
        }
    }
}