package com.example.taskmaster.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.taskmaster.models.Task
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.utils.Resource

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)
    val taskStateFlow get() = taskRepository.taskStateFlow
    val taskLiveData get() = taskRepository.statusLiveData

    fun getTaskList(){
        taskRepository.getTaskList()
    }

    fun insertTask(task: Task){
        taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task){
        taskRepository.deleteTask(task)
    }

    fun deleteTaskById(taskId: String){
        taskRepository.deleteTaskById(taskId)
    }
    fun updateTask(task: Task){
        taskRepository.updateTask(task)
    }

    fun updateTaskPaticularField(taskId: String,title: String, description: String){
        taskRepository.updateTaskPaticularField(taskId, title, description)
    }



}