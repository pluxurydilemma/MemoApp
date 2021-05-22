package com.androiddev.memoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Data.Utils
import com.androiddev.memoapp.R
import java.text.SimpleDateFormat

class DetailedFragment : Fragment() {
    private lateinit var memoTitle : TextView
    private lateinit var memoDesc : TextView
    private lateinit var memoDate : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    fun  OnViewCreated (view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view,savedInstanceState)

        memoTitle = view.findViewById(R.id.MemoTitle)
        memoDesc = view.findViewById(R.id.MemoDesc)
        memoDate = view.findViewById(R.id.MemoDate)

        val SimpleDate = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

        val bundle = this.arguments
        val memo = bundle?.getSerializable(Utils.Key) as Memo
        memoTitle.text = memo.memoTitle
        memoDesc.text = memo.memoDesc
        memoDate.text = SimpleDate.format(memo.date).toString()

    }

}