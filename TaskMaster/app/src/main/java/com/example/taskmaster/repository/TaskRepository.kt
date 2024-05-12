package com.example.taskmaster.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmaster.database.TaskDatabase
import com.example.taskmaster.models.Task
import com.example.taskmaster.utils.Resource
import com.example.taskmaster.utils.Resource.*
import com.example.taskmaster.utils.StatusResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepository(application: Application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao

    private val _taskStateFlow = MutableStateFlow<Resource<Flow<List<Task>>>>(Loading())
    val taskStateFlow : StateFlow<Resource<Flow<List<Task>>>>
        get() = _taskStateFlow

    private val _statusLiveData = MutableLiveData<Resource<StatusResult>>()
    val statusLiveData : LiveData<Resource<StatusResult>>
        get() = _statusLiveData

    fun getTaskList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _taskStateFlow.emit(Loading())
                delay(500)
                val result = taskDao.getTaskList()
                _taskStateFlow.emit(Success("loading",result))
            } catch (e: Exception) {
                _taskStateFlow.emit(Error(e.message.toString()))
            }
        }
    }

    fun insertTask(task: Task){
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                handleResult(result.toInt(), "Inserted Task Successfully", StatusResult.Added)

            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }


    fun deleteTask(task: Task){
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                handleResult(result, "Deleted Task Successfully", StatusResult.Deleted)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    fun deleteTaskById(taskId: String){
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTaskById(taskId)
                handleResult(result, "Deleted Task Successfully", StatusResult.Deleted)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    fun updateTask(task: Task){
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }
    fun updateTaskPaticularField(taskId: String,title: String, description: String){
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTaskPaticularField(taskId, title, description)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)
            }
        }catch (e: Exception){
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    private fun handleResult(result:Int,message: String, statusResult: StatusResult){
        if (result != -1){
            _statusLiveData.postValue(Success(message, statusResult))
        }else{
            _statusLiveData.postValue(Error("Something went wrong",statusResult))
        }
    }

}