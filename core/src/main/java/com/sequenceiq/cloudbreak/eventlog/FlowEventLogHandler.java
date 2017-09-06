package com.sequenceiq.cloudbreak.eventlog;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.Flow;
import com.sequenceiq.cloudbreak.eventlog.event.FlowEventLogEntry;

@Component
@Scope("prototype")
public class FlowEventLogHandler<S, E> extends StateMachineListenerAdapter<S, E> {
    @Inject
    private EventLogService eventLogService;

    private String flowType;

    private Flow flow;

    private Long stackId;

    private Long lastStateChange = -1L;

    public FlowEventLogHandler(String flowType, Flow flow, Long stackId) {
        this.flowType = flowType;
        this.flow = flow;
        this.stackId = stackId;
    }

    @Override
    public void stateChanged(State<S, E> from, State<S, E> to) {
    }

    @Override
    public void stateEntered(State<S, E> state) {

    }

    @Override
    public void stateExited(State<S, E> state) {

    }

    @Override
    public void eventNotAccepted(Message<E> event) {

    }

    @Override
    public void transition(Transition<S, E> transition) {
        State<S, E> from = transition.getSource();
        State<S, E> to = transition.getTarget();
        Long currentTime = System.currentTimeMillis();
        eventLogService.storeEventLogEntry(new FlowEventLogEntry(stackId, "", flowType,
                from != null ? from.getId().toString() : "unknown", to != null ? to.getId().toString() : "unknown",
                transition.getTrigger() != null ? transition.getTrigger().getEvent().toString() : "unknown",
                lastStateChange == -1L ? -1L : currentTime - lastStateChange));
        lastStateChange = currentTime;
    }

    @Override
    public void transitionStarted(Transition<S, E> transition) {

    }

    @Override
    public void transitionEnded(Transition<S, E> transition) {

    }

    @Override
    public void stateMachineError(StateMachine<S, E> stateMachine, Exception exception) {

    }
}
