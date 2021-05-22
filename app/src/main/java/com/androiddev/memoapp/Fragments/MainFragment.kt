package com.androiddev.memoapp.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Data.Utils
import com.androiddev.memoapp.MemoAdapter
import com.androiddev.memoapp.R
import com.google.gson.Gson
import java.util.*

class MainFragment : Fragment(), MemoAdapter.OnItemClickListener  {

    lateinit var memoList: RecyclerView
    lateinit var adapter: MemoAdapter
    lateinit var userNotes : MutableList<Memo>
    private val gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val preferences = context?.getSharedPreferences(Utils.SHARED_DB_NAME, Context.MODE_PRIVATE)
//        if (preferences?.getString(Utils.DATA_LIST, null) != null) {
//            userNotes.addAll(
//                gson.fromJson(
//                    preferences.getString(Utils.DATA_LIST, null),
//                    Array<Memo>::class.java
//                )
//            )
//            Log.i("MyData", userNotes.size.toString())
//        }
//        checkListSize(view)
        memoList = view.findViewById(R.id.MemoList)
        memoList.layoutManager = LinearLayoutManager(context)
        memoList.setHasFixedSize(true)

        userNotes = mutableListOf()
        updateList()
//        checkListSize(view)
        userNotes.add(Memo("Title1", "MainDesc", Date()))
        userNotes.add(Memo("Title2", "MainDesc2", Date()))
        userNotes.add(Memo("Title3", "MainDes3", Date()))
        userNotes.add(Memo("Title4", "MainDesc4", Date()))
        userNotes.add(Memo("Title5", "MainDesc5", Date()))
        userNotes.add(Memo("Title6", "MainDesc6", Date()))

        adapter = MemoAdapter(userNotes, this, this)
        memoList.adapter = adapter
        registerForContextMenu(memoList)
        setSharedPreferences(userNotes)
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.Key, userNotes[position])

        val fragment = DetailedFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onEditClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.Key, userNotes[position])

        val fragment = SecondFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }

    }

    override fun onDeleteClick(position: Int) {
        userNotes.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    fun updateList(){
        val preferences = context?.getSharedPreferences(Utils.SHARED_DB_NAME, Context.MODE_PRIVATE)
        if (preferences?.getString(Utils.DATA_LIST, null) != null) {
            userNotes.addAll(
                gson.fromJson(
                    preferences.getString(Utils.DATA_LIST, null),
                    Array<Memo>::class.java
                )
            )
            Log.i("MyData", userNotes.size.toString())
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

//    private fun checkListSize(view: View) {
//        if (userNotes.size == 0) {
//            view.findViewById<TextView>(R.id.message).visibility = View.VISIBLE
//        } else {
//            view.findViewById<TextView>(R.id.message).visibility = View.GONE
//        }
//    }



    override fun onDestroy() {
        super.onDestroy()
        setSharedPreferences(userNotes)
    }


}