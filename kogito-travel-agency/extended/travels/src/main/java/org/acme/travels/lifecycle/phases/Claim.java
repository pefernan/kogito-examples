package org.acme.travels.lifecycle.phases;

import org.kie.kogito.process.workitem.LifeCyclePhase;

import java.util.Arrays;
import java.util.List;

public class Claim implements LifeCyclePhase {
    public static final String ID = "claim";
    public static final String STATUS = "Pending";

    private List<String> allowedTransitions = Arrays.asList(Start.ID);

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String status() {
        return STATUS;
    }

    @Override
    public boolean isTerminating() {
        return false;
    }

    @Override
    public boolean canTransition(LifeCyclePhase phase) {
        return this.allowedTransitions.contains(phase.id());
    }
}
