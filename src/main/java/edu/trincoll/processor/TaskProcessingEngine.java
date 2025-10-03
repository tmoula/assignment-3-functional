package edu.trincoll.processor;

import edu.trincoll.functional.TaskPredicate;
import edu.trincoll.functional.TaskProcessor;
import edu.trincoll.functional.TaskTransformer;
import edu.trincoll.model.Task;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class TaskProcessingEngine {

    // Function composition over List<Task>
    public List<Task> processPipeline(
            List<Task> tasks,
            List<Function<List<Task>, List<Task>>> operations) {

        if (tasks == null) return null;
        if (operations == null || operations.isEmpty()) return new ArrayList<>(tasks);

        Function<List<Task>, List<Task>> pipeline =
                operations.stream().reduce(Function.identity(), Function::andThen);

        return pipeline.apply(new ArrayList<>(tasks));
    }

    // Supplier for lazy default
    public Task getOrCreateDefault(Optional<Task> taskOpt, Supplier<Task> defaultSupplier) {
        return taskOpt.orElseGet(defaultSupplier);
    }

    // Consumer side effects
    public void processTasksWithSideEffects(List<Task> tasks, Consumer<Task> sideEffect) {
        if (tasks == null || sideEffect == null) return;
        tasks.forEach(sideEffect);
    }

    // BiFunction merge
    public Task mergeTasks(Task task1, Task task2, BiFunction<Task, Task, Task> merger) {
        return (merger == null) ? null : merger.apply(task1, task2);
    }

    // UnaryOperator transform all
    public List<Task> transformAll(List<Task> tasks, UnaryOperator<Task> transformer) {
        if (tasks == null) return List.of();
        if (transformer == null) return new ArrayList<>(tasks);
        return tasks.stream().map(transformer).toList();
    }

    // Custom functional interfaces: filter + transform
    public List<Task> filterAndTransform(
            List<Task> tasks,
            TaskPredicate filter,
            TaskTransformer transformer) {

        if (tasks == null) return List.of();
        Predicate<Task> p = (filter == null) ? t -> true : filter::test;
        Function<Task, Task> m = (transformer == null) ? Function.identity() : transformer;
        return tasks.stream().filter(p).map(m).toList();
    }

    // Batch processing with TaskProcessor
    public void batchProcess(List<Task> tasks, int batchSize, TaskProcessor processor) {
        if (tasks == null || processor == null || batchSize <= 0) return;
        for (int i = 0; i < tasks.size(); i += batchSize) {
            List<Task> slice = tasks.subList(i, Math.min(i + batchSize, tasks.size()));
            processor.process(slice);
        }
    }

    // Optional chaining: highest-priority title
    public Optional<String> getHighestPriorityTaskTitle(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return Optional.empty();
        return tasks.stream()
                .max(Comparator.comparingInt(t -> t.priority().getWeight()))
                .map(Task::title);
    }

    // Infinite stream via Supplier
    public Stream<Task> generateTaskStream(Supplier<Task> taskSupplier) {
        return taskSupplier == null ? Stream.empty() : Stream.generate(taskSupplier);
    }

    // Compose multiple comparators
    public List<Task> sortByMultipleCriteria(List<Task> tasks, List<Comparator<Task>> comparators) {
        if (tasks == null) return List.of();
        if (comparators == null || comparators.isEmpty()) return new ArrayList<>(tasks);

        Comparator<Task> composite = comparators.get(0);
        for (int i = 1; i < comparators.size(); i++) {
            composite = composite.thenComparing(comparators.get(i));
        }
        return tasks.stream().sorted(composite).toList();
    }
}