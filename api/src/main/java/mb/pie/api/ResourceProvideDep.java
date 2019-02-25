package mb.pie.api;

import mb.pie.api.stamp.ResourceStamp;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;

/**
 * Resource 'provides' (writes) dependency.
 */
public class ResourceProvideDep implements ResourceDep, Serializable {
    public final ResourceKey key;
    public final ResourceStamp<Resource> stamp;


    public ResourceProvideDep(ResourceKey key, ResourceStamp<Resource> stamp) {
        this.key = key;
        this.stamp = stamp;
    }


    @Override public @Nullable InconsistentResourceProvide checkConsistency(ResourceSystems systems) {
        final @Nullable ResourceSystem system = systems.getResourceSystem(key.getId());
        if(system == null) {
            throw new RuntimeException(
                "Cannot get resource system for resource key '" + key + "'; resource system with id '" + key.getId() + "' does not exist");
        }
        final Resource resource = system.getResource(key);
        final ResourceStamp<Resource> newStamp = stamp.getStamper().stamp(resource);
        if(stamp != newStamp) {
            return new InconsistentResourceProvide(this, newStamp);
        }
        return null;
    }

    @Override public Boolean isConsistent(ResourceSystems systems) {
        final @Nullable ResourceSystem system = systems.getResourceSystem(key.getId());
        if(system == null) {
            throw new RuntimeException(
                "Cannot get resource system for resource key '" + key + "'; resource system with id '" + key.getId() + "' does not exist");
        }
        final Resource resource = system.getResource(key);
        final ResourceStamp<Resource> newStamp = stamp.getStamper().stamp(resource);
        return stamp == newStamp;
    }


    @Override public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        final ResourceProvideDep that = (ResourceProvideDep) o;
        if(!key.equals(that.key)) return false;
        return stamp.equals(that.stamp);
    }

    @Override public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + stamp.hashCode();
        return result;
    }

    @Override public String toString() {
        return "ResourceProvideDep(" + key + ", " + stamp + ")";
    }
}
