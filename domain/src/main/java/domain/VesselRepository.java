package domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class VesselRepository {
    private final ConcurrentMap<VesselId, Vessel> vessels = new ConcurrentHashMap<>();

    public Vessel read(VesselId id) {
        return vessels.get(id);
    }

    public void add(Vessel vessel) {
        vessels.put(vessel.id(), vessel);
    }
}
