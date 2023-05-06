package com.example.timeisearth.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timeisearth.R
import com.example.timeisearth.view.adapter.TodoAdapter
import com.example.timeisearth.databinding.ActivityMainBinding
import com.example.timeisearth.model.entity.Todo
import com.example.timeisearth.util.constant.TAG
import com.example.timeisearth.viewModel.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), TodoListener {
    private val dialog: TodoDialog by lazy { TodoDialog(this, this) }
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: TodoAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        initTodoList()
        binding.fabAddTodo.setOnClickListener { showDialog() }
    }

    private fun initTodoList() {
        adapter = TodoAdapter(viewModel.todoList)

        with(binding) {
            rvTodoList.adapter = adapter
            rvTodoList.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.initTodoList()
    }

    private fun showDialog() {
        dialog.show()
        Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show()
    }

    override fun notifyNewTodo(todo: Todo) {
        viewModel.insertTodo(todo)
        adapter.notifyItemInserted(viewModel.todoList.size - 1)
    }
}