package com.harjot.crudwithbaseadapter

import android.annotation.SuppressLint
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.harjot.crudwithbaseadapter.databinding.ItemBaseAdapterBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ListAdapter(var array: ArrayList<UserModel>): BaseAdapter() {
    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): Any {
        return array[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {
        var binding = ItemBaseAdapterBinding.inflate(LayoutInflater.from(parent?.context)
            ,parent,false)

        binding.tvName.setText(array[position].name)
        binding.tvRollNo.setText(array[position].rollNo.toString())
        binding.tvSubject.setText(array[position].subject)

        return binding.root
    }
}