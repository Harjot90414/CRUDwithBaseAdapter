package com.harjot.crudwithbaseadapter

import android.app.AlertDialog
import android.app.Dialog
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.harjot.crudwithbaseadapter.databinding.ActivityMainBinding
import com.harjot.crudwithbaseadapter.databinding.DialogLayoutBinding
import com.harjot.crudwithbaseadapter.databinding.ItemBaseAdapterBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var array = ArrayList<UserModel>()
    var listAdapter = ListAdapter(array)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListAdapter(array)
        binding.listView.adapter = listAdapter
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            dialog(position)
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Delete Item")
            alertDialog.setMessage("Do you want to delete the item?")
            alertDialog.setCancelable(false)
            alertDialog.setNegativeButton("No") { _, _ ->
                alertDialog.setCancelable(true)
            }
            alertDialog.setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "The item is  deleted", Toast.LENGTH_SHORT).show()
                array.removeAt(position)
                listAdapter.notifyDataSetChanged()
            }
            alertDialog.show()
            return@setOnItemLongClickListener true
        }

        binding.fabAdd.setOnClickListener {
            dialog()
        }
    }
    fun dialog(position: Int = -1){
        var dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
        var dialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
            setCancelable(false)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            if (position==-1){
                dialogBinding.btnAdd.setText("Add")
            }else{
                dialogBinding.btnAdd.setText("Update")
                dialogBinding.etName.setText(array[position].name)
                dialogBinding.etRollNo.setText(array[position].rollNo.toString())
                dialogBinding.etSubject.setText(array[position].subject)
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etName.text.toString().trim().isNullOrEmpty()) {
                    dialogBinding.etName.error = "Enter Name"
                } else if (dialogBinding.etRollNo.text.toString().trim().isNullOrEmpty()) {
                    dialogBinding.etRollNo.error = "Enter Roll No"
                } else if (dialogBinding.etSubject.text.toString().trim().isNullOrEmpty()) {
                    dialogBinding.etSubject.error = "Enter Subject"
                } else {
                    if (position>-1){
                        setCancelable(true)
                        array[position] = UserModel(
                            dialogBinding.etName.text.toString(),
                            dialogBinding.etRollNo.text.toString().toInt(),
                            dialogBinding.etSubject.text.toString()
                        )
                        dismiss()
                    }else{
                        array.add(
                            UserModel(dialogBinding.etName.text.toString(),
                                dialogBinding.etRollNo.text.toString().toInt(),
                                dialogBinding.etSubject.text.toString()
                            ))
                        dismiss()
                    }
                    listAdapter.notifyDataSetChanged()
                }
            }
            show()
        }
    }
}