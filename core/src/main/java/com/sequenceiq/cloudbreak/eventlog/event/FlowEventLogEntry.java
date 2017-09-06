package com.sequenceiq.cloudbreak.eventlog.event;

public class FlowEventLogEntry extends EventLogEntry {
    private String flowChainType;

    private String flowType;

    private String flowState;

    private String nextFlowState;

    private String flowEvent;

    private Long duration;

    public FlowEventLogEntry(Long stackId, String flowChainType, String flowType, String flowState, String nextFlowState, String flowEvent, Long duration) {
        super("FLOW", "STACK", stackId);
        this.flowChainType = flowChainType;
        this.flowType = flowType;
        this.flowState = flowState;
        this.nextFlowState = nextFlowState;
        this.flowEvent = flowEvent;
        this.duration = duration;
    }

    public String getFlowChainType() {
        return flowChainType;
    }

    public String getFlowType() {
        return flowType;
    }

    public String getFlowState() {
        return flowState;
    }

    public String getNextFlowState() {
        return nextFlowState;
    }

    public String getFlowEvent() {
        return flowEvent;
    }

    public Long getDuration() {
        return duration;
    }
}
