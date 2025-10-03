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
    // sort by priority weight desc + limit
    public List<Task> getTopPriorityTasks(int limit) {
        return tasks.stream()
                .sorted(Comparator.comparingInt((Task t) -> t.priority().getWeight()).reversed())
                .limit(Math.max(0, limit))
                .toList();
    }

    // groupingBy status
    public Map<Task.Status, List<Task>> groupByStatus() {
        return tasks.stream().collect(Collectors.groupingBy(Task::status));
    }

    // partitioningBy overdue
    public Map<Boolean, List<Task>> partitionByOverdue() {
        return tasks.stream().collect(Collectors.partitioningBy(Task::isOverdue));
    }

    // unique tags via flatMap
    public Set<String> getAllUniqueTags() {
        return tasks.stream()
                .filter(t -> t.tags() != null)
                .flatMap(t -> t.tags().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // reduce / Optional total hours (handles null hours)
    public Optional<Integer> getTotalEstimatedHours() {
        int sum = tasks.stream()
                .map(Task::estimatedHours)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        return tasks.isEmpty() ? Optional.empty()
                : (sum == 0 ? Optional.of(0) : Optional.of(sum));
    }

    // average estimated hours (ignoring nulls)
    public OptionalDouble getAverageEstimatedHours() {
        return tasks.stream()
                .map(Task::estimatedHours)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average();
    }

    // method reference to titles
    public List<String> getTaskTitles() {
        return tasks.stream().map(Task::title).toList();
    }

    // custom predicate
    public List<Task> filterWithCustomPredicate(TaskPredicate predicate) {
        if (predicate == null) return new ArrayList<>(tasks);
        return tasks.stream().filter(predicate::test).toList();
    }

    // flatMap all tags, sorted
    public List<String> getAllTagsSorted() {
        System.out.println("Analyzer tasks count: " + tasks.size());
        tasks.forEach(t -> System.out.println("Task " + t.id() + " tags: " + t.tags()));

        return tasks.stream()
                .flatMap(t -> {
                    var tags = t.tags();
                    return tags == null ? java.util.stream.Stream.<String>empty()
                            : tags.stream();
                })
                .sorted()
                .toList();

    }


    // counting by priority
    public Map<Task.Priority, Long> countTasksByPriority() {
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::priority, Collectors.counting()));
    }

    // Optional chain to summary
    public String getTaskSummary(Long taskId) {
        return findTaskById(taskId)
                .map(t -> t.title() + " - " + t.status())
                .orElse("Task not found");

    }

    // any overdue?
    public boolean hasOverdueTasks() {
        return tasks.stream().anyMatch(Task::isOverdue);
    }

    // all "assigned"?
    // (No assignee field in Task; use a proxy rule: at least one tag)
    public boolean areAllTasksAssigned() {
        return tasks.stream().allMatch(t -> t.status() != Task.Status.TODO);
    }

    public List<Task> getTasks() {
        return tasks;
    }

}