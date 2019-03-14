package mb.pie.runtime.exec;

import mb.pie.api.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

public class BottomUpShared {
    /**
     * Returns keys of tasks that are directly affected by changed resources.
     */
    public static HashSet<TaskKey> directlyAffectedTaskKeys(StoreReadTxn txn, Collection<ResourceKey> changedResources, ResourceSystems resourceSystems, Logger logger) {
        final HashSet<TaskKey> affected = new HashSet<>();
        for(ResourceKey changedResource : changedResources) {
            logger.trace("* resource: " + changedResource);

            final Set<TaskKey> requirees = txn.requireesOf(changedResource);
            for(TaskKey key : requirees) {
                logger.trace("  * required by: " + key.toShortString(200));
                if(!txn.resourceRequires(key).stream().filter(dep -> dep.key.equals(changedResource)).allMatch(dep -> dep.isConsistent(resourceSystems))) {
                    affected.add(key);
                }
            }

            final @Nullable TaskKey provider = txn.providerOf(changedResource);
            if(provider != null) {
                logger.trace("  * provided by: " + provider.toShortString(200));
                if(!txn.resourceProvides(provider).stream().filter(dep -> dep.key.equals(changedResource)).allMatch(dep -> dep.isConsistent(resourceSystems))) {
                    affected.add(provider);
                }
            }
        }

        return affected;
    }

    /**
     * Checks whether [caller] has a transitive (or direct) task requirement to [callee].
     */
    public static boolean hasTransitiveTaskReq(StoreReadTxn txn, TaskKey caller, TaskKey callee) {
        // TODO: more efficient implementation for transitive calls?
        final Queue<TaskKey> toCheckQueue = new LinkedList<>();
        toCheckQueue.add(caller);
        while(!toCheckQueue.isEmpty()) {
            final TaskKey toCheck = toCheckQueue.poll();
            final List<TaskRequireDep> taskReqDeps = txn.taskRequires(toCheck);
            for(TaskRequireDep dep : taskReqDeps) {
                if(dep.calleeEqual(callee)) {
                    return true;
                }
                toCheckQueue.add(dep.callee);
            }
        }
        return false;
    }
}
