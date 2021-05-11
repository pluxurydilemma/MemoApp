package com.androiddev.memoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.memoapp.Data.Memo
import com.androiddev.memoapp.Fragments.MainFragment

class MemoAdapter(var memoList: List<Memo>,
                  var listener: MainFragment
                   ) : RecyclerView.Adapter<MemoAdapter.MemoHolder>() {

    inner class MemoHolder(itemView : View ) : RecyclerView.ViewHolder(itemView),View.OnClickListener
    {
        val noteTitle : TextView = itemView.findViewById(R.id.noteTitle)
        val noteDesc : TextView = itemView.findViewById(R.id.noteDesc)
        private val noteDelete : ImageView = itemView.findViewById(R.id.noteDelete)
        private val noteEdit : ImageView = itemView.findViewById(R.id.noteEdit)
        private val parent : CardView = itemView.findViewById(R.id.parent)

        init {
            parent.setOnClickListener(this)
            noteDelete.setOnClickListener(this)
            noteEdit.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            when (v?.id){
                R.id.parent -> listener.onItemClick(adapterPosition)
                R.id.noteDelete -> listener.onDeleteClick(adapterPosition)
                R.id.noteEdit -> listener.onEditClick(adapterPosition)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_item,parent,false)
        return  MemoHolder(view)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    override fun onBindViewHolder(holder: MemoHolder, position: Int) {
        holder.itemView.apply {
            holder.noteTitle.text = memoList[position].memoTitle
            holder.noteDesc.text = memoList[position].memoDesc
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)

    }


}
