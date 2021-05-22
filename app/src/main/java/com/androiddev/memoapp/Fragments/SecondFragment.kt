package com.androiddev.memoapp.Fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Data.Utils
import com.androiddev.memoapp.MemoAdapter
import com.androiddev.memoapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*


class SecondFragment : Fragment() {
    private lateinit var memoTitle: TextInputEditText
    private lateinit var memoDesc: TextInputEditText
    private lateinit var memoDateView: TextView
    private lateinit var selectDate: Button
    private lateinit var selectedDateView: TextView
    private lateinit var saveMemo: Button
    private lateinit var memoDate: Date
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memoTitle = view.findViewById(R.id.memoTitle_editText)
        memoDesc = view.findViewById(R.id.memoDesc_editText)
        memoDateView = view.findViewById(R.id.selectedDate_edit)
        selectDate = view.findViewById(R.id.selectDate_edit)
        selectedDateView = view.findViewById(R.id.selectedDate_edit)
        saveMemo = view.findViewById(R.id.saveNote)

        selectDate.setOnClickListener {
            val myCalendar = Calendar.getInstance()
            val year = myCalendar.get(Calendar.YEAR)
            val month = myCalendar.get(Calendar.MONTH)
            val day = myCalendar.get(Calendar.DAY_OF_MONTH)

            context?.let {
                DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        selectedDateView.text = (selectedDate)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                        memoDate = sdf.parse(selectedDate) as Date
                    },
                    year,
                    month,
                    day
                ).show()
            }
        }

        val SimpleDate = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val bundle = this.arguments
        val memo = bundle?.getSerializable(Utils.Key) as Memo
        memoTitle.setText(memo.memoTitle)
        memoDesc.setText(memo.memoDesc)
        memoDateView.text = SimpleDate.format(memo.date).toString()

        saveMemo.setOnClickListener {
            val noteTitleView = memoTitle.text.toString()
            val noteDescView = memoDesc.text.toString()
            val noteDate = Date()
            Log.i("MyData", "$noteTitleView  $noteDescView")
            val note = Memo(noteTitleView, noteDescView, noteDate)
            val sharedPreferences =
                context?.getSharedPreferences(Utils.SHARED_DB_NAME, Context.MODE_PRIVATE)
            if (sharedPreferences?.getString(Utils.DATA_LIST, null) != null) {
                val listType = object : TypeToken<MutableList<ContactsContract.CommonDataKinds.Note>>() {}.type
                val json = sharedPreferences.getString(Utils.DATA_LIST, null)
                val userNotes: MutableList<Memo> = gson.fromJson(json, listType)
                userNotes.remove(memo)
                userNotes.add(note)
                setSharedPreferences(userNotes)
                setFragment(MainFragment())
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    private fun setSharedPreferences(userNotes: MutableList<Memo>) {
        val sharedPreference =
            context?.getSharedPreferences(Utils.SHARED_DB_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString(Utils.DATA_LIST, userNotesString)
        editor?.apply()
    }
}