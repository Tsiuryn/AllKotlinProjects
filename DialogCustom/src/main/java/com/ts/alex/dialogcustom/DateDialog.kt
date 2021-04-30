package com.ts.alex.dialogcustom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ts.alex.dialogcustom.databinding.DateDialogBinding

class DateDialog : DialogFragment() {

    private lateinit var binding: DateDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DateDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDate(minDate = 0, maxDate = 30, currentDate = 24){
            Log.d("TAG11", "onViewCreated: $it")
        }
        setUpMonth(Month.APRIL){

        }
        setUpYear(minYear = 2012, maxYear = 2022, currentYear = 2021)
    }

    private fun setUpDate(minDate: Int = 1, maxDate: Int = 30, currentDate: Int, listener:(String) -> Unit) {
        val array = createListDate(minDate + 1, maxDate + 1 )
        val date = binding.vDate
        date.minValue = 0
        date.maxValue = array.size - 1
        if (currentDate < maxDate && minDate < currentDate) {
            date.value = currentDate
        }
        date.displayedValues = array
        date.setOnValueChangedListener { _, _, newVal ->
            listener(array[newVal])
        }
    }

    private fun createListDate(min: Int, max: Int): Array<String> {
        return Array<String>(max - min ) { i ->
            val date = i + min
            if (date.toString().length == 1) {
                "0$date"
            } else {
                date.toString()
            }
        }
    }

    private fun setUpMonth(currentMonth: Month, listener:(String) -> Unit) {
        val array = createListMonth(Month.values())
        val month = binding.vMonth
        month.minValue = 0
        month.maxValue = array.size - 1
        val index = array.indexOf(currentMonth.month)
        month.value = index
        month.displayedValues = array
        month.setOnValueChangedListener { _, _, newVal ->
            val maxDate = Month.values()[newVal].date
            val nDate = binding.vDate
            if(nDate.maxValue > maxDate){
                setUpDate(
                    minDate = nDate.minValue,
                    maxDate = maxDate,
                    currentDate = nDate.value
                ){}
            }
        }
    }

    private fun createListMonth(list: Array<Month>): Array<String> {
        return Array<String>(list.size) { i ->
            list[i].month
        }
    }

    private fun setUpYear(minYear: Int = 1970, maxYear: Int = 2050, currentYear: Int) {
        val year = binding.vYear
        year.minValue = minYear
        year.maxValue = maxYear
        year.value = currentYear
        year.setOnValueChangedListener { _, _, newVal ->

        }
    }
}

enum class Month(val date: Int, val month: String) {
    JANUARY(date = 31, month = "январь"),
    FABRUARY(date = 28, month = "февраль"),
    MARCH(date = 31, month = "март"),
    APRIL(date = 30, month = "апрель"),
    MAY(date = 31, month = "май"),
    JUNE(date = 30, month = "июнь"),
    JULY(date = 31, month = "июль"),
    AUGUST(date = 31, month = "август"),
    SEPTEMBER(date = 30, month = "сентябрь"),
    OCTOBER(date = 31, month = "октябрь"),
    NOVEMBER(date = 30, month = "ноябрь"),
    DECEMBER(date = 31, month = "декабрь"),
}

