package br.edu.up.nowbarber.ui.components

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun SelectDateAndTime(
    onDateTimeSelected: (date: Calendar, time: Calendar) -> Unit
) {
    val context = LocalContext.current

    // Exibir DatePickerDialog
    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Exibir TimePicker com horários fixos
                showTimeSelectionDialog(context) { selectedHour ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                    selectedTime.set(Calendar.MINUTE, 0)

                    onDateTimeSelected(selectedDate, selectedTime)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Botão para iniciar o processo de seleção
    Button(onClick = { showDatePicker() }) {
        Text(text = "Selecionar Data e Hora")
    }
}

fun showTimeSelectionDialog(context: Context, onTimeSelected: (hour: Int) -> Unit) {
    val availableHours = (8..18).toList() // Horários das 8h às 18h
    val hourOptions = availableHours.map { "$it:00" }.toTypedArray()

    AlertDialog.Builder(context)
        .setTitle("Selecione um horário")
        .setItems(hourOptions) { _, which ->
            onTimeSelected(availableHours[which])
        }
        .create()
        .show()
}

