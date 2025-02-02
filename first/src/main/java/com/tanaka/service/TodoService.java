package com.tanaka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanaka.dao.TodoDao;
import com.tanaka.model.Todo;

@Service
public class TodoService {

    @Autowired
    private TodoDao todoDao;

    // タスクのリストを取得
    public List<Todo> getTodoList() {
        return todoDao.getTodoList();
    }
    
    // 完了したタスクを取得
    public List<Todo> getCompletedTodoList() {
        return todoDao.getCompletedTodoList();  // Dao層で完了したタスクを取得
    }


    // タスクを追加
    public void addTodo(String taskDescription) {
        todoDao.addTodo(taskDescription);
    }
    
 // タスクを完了にする
    public void completeTodoByDescription(String taskDescription) {
        todoDao.completeTodoByDescription(taskDescription);  // Dao層で完了処理
    }

    // タスクを削除する
    public void deleteTodoByDescription(String taskDescription) {
        todoDao.deleteTodoByDescription(taskDescription);  // Dao層で削除処理
    }

    // タスクを編集する
    public void updateTodoByDescription(String taskDescription, String newTaskDescription) {
        todoDao.updateTodoByDescription(taskDescription, newTaskDescription);  // Dao層で編集処理
    }



 // 完了タスクを削除
    public void deleteCompletedTodos() {
        todoDao.deleteCompletedTodos(); // 完了タスクを削除
    }

}
