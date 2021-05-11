package com.androiddev.memoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Data.Utils
import com.androiddev.memoapp.MemoAdapter
import com.androiddev.memoapp.R
import java.util.*

class MainFragment : Fragment(), MemoAdapter.OnItemClickListener  {

    lateinit var memoList: RecyclerView
    lateinit var adapter: MemoAdapter
    lateinit var userNotes : MutableList<Memo>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memoList = view.findViewById(R.id.MemoList)
        memoList.layoutManager = LinearLayoutManager(context)
        memoList.setHasFixedSize(true)

        userNotes = mutableListOf()
        userNotes.add(Memo("Title1", "MainDesc", Date()))
        userNotes.add(Memo("Title2", "MainDesc2", Date()))
        userNotes.add(Memo("Title3", "MainDes3", Date()))
        userNotes.add(Memo("Title4", "MainDesc4", Date()))
        userNotes.add(Memo("Title5", "MainDesc5", Date()))
        userNotes.add(Memo("Title6", "MainDesc6", Date()))

        adapter = MemoAdapter(userNotes, this)
        memoList.adapter = adapter
    }

    override fun onItemClick(position: Int) {
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

    override fun onEditClick(position: Int) {
    Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(position: Int) {
        userNotes.removeAt(position)
        adapter.notifyDataSetChanged()
    }


}