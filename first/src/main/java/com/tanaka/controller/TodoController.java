package com.tanaka.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tanaka.model.Todo;
import com.tanaka.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    // "/" にアクセスした場合、タスクリストを表示
    @GetMapping("/")
    public String getTodoList(Model model) {
        // 完了していないタスクのみを取得
        List<Todo> todos = todoService.getTodoList().stream()
                                      .filter(todo -> !todo.isCompleted())
                                      .collect(Collectors.toList());

        model.addAttribute("todos", todos);
        return "todo"; // "todoList.html" を返す
    }


    // 新しいタスクを追加
    @PostMapping("/todos")
    public String addTodo(@RequestParam("taskDescription") String taskDescription) {
        todoService.addTodo(taskDescription);
        return "redirect:/"; // タスク追加後にトップページ（"/"）にリダイレクト
    }

 // TODOの完了（POSTで送信）
    @PostMapping("/complete")
    public String completeTodo(@RequestParam("taskDescription") String taskDescription) {
        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            // タスク内容が空の場合のエラーハンドリング
            return "redirect:/";  // 何もせずにトップページにリダイレクト
        }
        todoService.completeTodoByDescription(taskDescription);  // タスク内容で完了処理
        return "redirect:/";  // 完了後にトップページにリダイレクト
    }


    // 完了したTODOのみを取得
    @GetMapping("/completed")
    public String getCompletedTodoList(Model model) {
        List<Todo> completedTodos = todoService.getCompletedTodoList();
        model.addAttribute("todos", completedTodos);  // 完了タスクをビューに渡す
        return "completed";  // "completed.html"に遷移
    }

 // TODOを削除（POSTで送信）
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("taskDescription") String taskDescription) {
        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            // タスク内容が空の場合のエラーハンドリング
            return "redirect:/";  // 何もせずにトップページにリダイレクト
        }
        todoService.deleteTodoByDescription(taskDescription);  // タスク内容で削除
        return "redirect:/"; // タスク削除後にトップページ（"/"）にリダイレクト
    }

    // 完了TODOを削除（POSTで送信）
    @PostMapping("/deleteCompleted")
    public String deleteCompletedTodos() {
        todoService.deleteCompletedTodos();  // 完了タスクを削除
        return "redirect:/";  // 削除後にトップページにリダイレクト
    }

 // TODOを編集（GETメソッドでフォーム表示）
    @GetMapping("/edit")
    public String editTodoForm(@RequestParam("taskDescription") String taskDescription, Model model) {
        Todo todo = todoService.getTodoList().stream()
                .filter(t -> t.getTask().equals(taskDescription))
                .findFirst()
                .orElse(null);
        model.addAttribute("todo", todo);
        return "edit"; // 編集フォームを表示
    }

    // 編集後に更新（POSTメソッドで更新）
    @PostMapping("/edit")
    public String editTodo(@RequestParam("taskDescription") String taskDescription,
                            @RequestParam("newTaskDescription") String newTaskDescription) {
        if (taskDescription == null || taskDescription.trim().isEmpty() || newTaskDescription == null || newTaskDescription.trim().isEmpty()) {
            // タスク内容が空の場合のエラーハンドリング
            return "redirect:/";  // 何もせずにトップページにリダイレクト
        }
        todoService.updateTodoByDescription(taskDescription, newTaskDescription);
        return "redirect:/"; // 編集後にトップページ（"/"）にリダイレクト
    }

}
