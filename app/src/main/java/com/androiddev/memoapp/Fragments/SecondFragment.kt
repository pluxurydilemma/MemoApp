package com.androiddev.memoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Data.Utils
import com.androiddev.memoapp.MemoAdapter
import com.androiddev.memoapp.R


class SecondFragment : Fragment() {
    lateinit var mainText : EditText
    lateinit var description : EditText
    lateinit var cancelButton : Button
    lateinit var saveButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

     fun OnViewCreated (view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)

        val bundle = Bundle()
        val memo = bundle.getSerializable(Utils.Key) as Memo

        mainText = view.findViewById(R.id.MainTextEdit)
        description = view.findViewById(R.id.DescriptionEdit)
        cancelButton = view.findViewById(R.id.cancel)
        saveButton = view.findViewById(R.id.save)


        mainText.setText(memo.memoTitle)
        description.setText(memo.memoDesc)






        
    }

}