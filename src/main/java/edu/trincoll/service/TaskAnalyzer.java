package edu.trincoll.service;

import edu.trincoll.functional.TaskPredicate;
import edu.trincoll.model.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AI Collaboration Report:
 * Tool: Chatgpt 5
 *
 * Most Helpful Prompts:
 * 1. "What are lambdas, predicates, optional, etc"
 * 2. "What's happening in this error message"
 *
 * Concepts Learned:
 * - Manipulating data using functional programming is easier than OOP
 *
 * Team: Taha, Daniel, Varvara
 */

public class TaskAnalyzer {
    private final List<Task> tasks;

    public TaskAnalyzer(List<Task> tasks) {
        // Keep a live reference so any tasks added after construction are visible
        this.tasks = java.util.Objects.requireNonNull(tasks);
    }

    // streams + filter
    public List<Task> filterTasks(Predicate<Task> predicate) {
        if (predicate == null) return new ArrayList<>(tasks);
        return tasks.stream().filter(predicate).toList();
    }

    // Optional find by id
    public Optional<Task> findTaskById(Long id) {
        return tasks.stream().filter(
                        t -> Objects.equals(t.id(), id))
                .findFirst();
    }

    // TODO: Implement using streams, sorted, and limit
    public List<Task> getTopPriorityTasks(int limit) {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams and groupingBy
    public Map<Task.Status, List<Task>> groupByStatus() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams and partitioningBy
    public Map<Boolean, List<Task>> partitionByOverdue() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams, map, and collect
    public Set<String> getAllUniqueTags() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams and reduce
    public Optional<Integer> getTotalEstimatedHours() {
        // Implementation needed
        return Optional.empty();
    }

    // TODO: Implement using streams, map, and average
    public OptionalDouble getAverageEstimatedHours() {
        // Implementation needed
        return OptionalDouble.empty();
    }

    // TODO: Implement using method references and map
    public List<String> getTaskTitles() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using custom functional interface
    public List<Task> filterWithCustomPredicate(TaskPredicate predicate) {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams and flatMap
    public List<String> getAllTagsSorted() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using streams and counting collector
    public Map<Task.Priority, Long> countTasksByPriority() {
        // Implementation needed
        return null;
    }

    // TODO: Implement using Optional operations
    public String getTaskSummary(Long taskId) {
        // Implementation needed
        return "Task not found";
    }

    // TODO: Implement using streams and anyMatch
    public boolean hasOverdueTasks() {
        // Implementation needed
        return false;
    }

    // TODO: Implement using streams and allMatch
    public boolean areAllTasksAssigned() {
        // Implementation needed
        return false;
    }
}