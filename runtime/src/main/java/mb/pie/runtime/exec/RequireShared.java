package mb.pie.runtime.exec;

import mb.pie.api.*;
import mb.pie.api.exec.Cancelled;
import mb.resource.ResourceService;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.HashMap;

public class RequireShared {
    private final TaskDefs taskDefs;
    private final ResourceService resourceService;
    private final Store store;
    private final ExecutorLogger executorLogger;

    private final HashMap<TaskKey, TaskData> visited;

    public RequireShared(
        TaskDefs taskDefs,
        ResourceService resourceService,
        Store store,
        ExecutorLogger executorLogger,
        HashMap<TaskKey, TaskData> visited
    ) {
        this.taskDefs = taskDefs;
        this.resourceService = resourceService;
        this.store = store;
        this.executorLogger = executorLogger;

        this.visited = visited;
    }

    /**
     * Attempt to get task data from the visited cache.
     */
    @Nullable TaskData dataFromVisited(TaskKey key) {
        executorLogger.checkVisitedStart(key);
        final @Nullable TaskData data = visited.get(key);
        executorLogger.checkVisitedEnd(key, data != null ? data.output : null);
        return data;
    }

    /**
     * Attempt to get task data from the store.
     */
    @Nullable TaskData dataFromStore(TaskKey key) {
        executorLogger.checkStoredStart(key);
        final @Nullable TaskData data;
        try(final StoreReadTxn txn = store.readTxn()) {
            data = txn.data(key);
        }
        executorLogger.checkStoredEnd(key, data != null ? data.output : null);
        return data;
    }


    /**
     * Check if input is internally consistent.
     */
    @Nullable InconsistentInput checkInput(Serializable input, Task<?> task) {
        if(!input.equals(task.input)) {
            return new InconsistentInput(input, task.input);
        }
        return null;
    }

    /**
     * Check if output is internally consistent.
     */
    @Nullable InconsistentTransientOutput checkOutputConsistency(@Nullable Serializable output) {
        return InconsistentTransientOutput.checkOutput(output);
    }

    /**
     * Check if a resource require dependency is internally consistent.
     */
    @Nullable InconsistentResourceRequire checkResourceRequire(TaskKey key, Task<?> task, ResourceRequireDep fileReq) {
        executorLogger.checkResourceRequireStart(key, task, fileReq);
        final @Nullable InconsistentResourceRequire reason = fileReq.checkConsistency(resourceService);
        executorLogger.checkResourceRequireEnd(key, task, fileReq, reason);
        return reason;
    }

    /**
     * Check if a resource provide dependency is internally consistent.
     */
    @Nullable InconsistentResourceProvide checkResourceProvide(TaskKey key, Task<?> task, ResourceProvideDep fileGen) {
        executorLogger.checkResourceProvideStart(key, task, fileGen);
        final @Nullable InconsistentResourceProvide reason = fileGen.checkConsistency(resourceService);
        executorLogger.checkResourceProvideEnd(key, task, fileGen, reason);
        return reason;
    }

    /**
     * Check if a task require dependency is totally consistent.
     */
    @Nullable InconsistentTaskReq checkTaskRequire(TaskKey key, Task<?> task, TaskRequireDep taskRequire, RequireTask requireTask, Cancelled cancel) throws ExecException, InterruptedException {
        final TaskKey calleeKey = taskRequire.callee;
        final Task<?> calleeTask;
        try(final StoreReadTxn txn = store.readTxn()) {
            calleeTask = calleeKey.toTask(taskDefs, txn);
        }
        final @Nullable Serializable calleeOutput = requireTask.require(calleeKey, calleeTask, cancel);
        executorLogger.checkTaskRequireStart(key, task, taskRequire);
        final @Nullable InconsistentTaskReq reason = taskRequire.checkConsistency(calleeOutput);
        executorLogger.checkTaskRequireEnd(key, task, taskRequire, reason);
        return reason;
    }
}
