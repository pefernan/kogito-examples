/**
 *  Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.acme.travels.lifecycle.phases;

import org.jbpm.process.instance.impl.humantask.HumanTaskWorkItemImpl;
import org.kie.api.runtime.process.HumanTaskWorkItem;
import org.kie.api.runtime.process.WorkItem;
import org.kie.kogito.auth.SecurityPolicy;
import org.kie.kogito.process.workitem.LifeCyclePhase;
import org.kie.kogito.process.workitem.Policy;
import org.kie.kogito.process.workitem.Transition;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Start life cycle phase that applies to any human task.
 * It will set the status to "Started" 
 *
 * It can transition from
 * <ul>
 *  <li>Start</li>
 * </ul>
 * 
 */
public class Cancel implements LifeCyclePhase {

    public static final String ID = "cancel";
    public static final String STATUS = "Cancelled";
    
    private List<String> allowedTransitions = Arrays.asList(Start.ID, Claim.ID);
    
    @Override
    public String id() {
        return ID;
    }

    @Override
    public String status() {
        return STATUS;
    }

    public boolean isTerminating() {
        return false;
    }

    public boolean canTransition(LifeCyclePhase phase) {
        return this.allowedTransitions.contains(phase.id());
    }
}
