package com.sequenceiq.cloudbreak.core.flow2.cluster.reset;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.statemachine.StateContext;

import com.sequenceiq.cloudbreak.cloud.event.Payload;
import com.sequenceiq.cloudbreak.core.flow2.AbstractAction;
import com.sequenceiq.cloudbreak.core.flow2.cluster.ClusterContext;
import com.sequenceiq.cloudbreak.domain.Cluster;
import com.sequenceiq.cloudbreak.domain.Stack;
import com.sequenceiq.cloudbreak.logger.MDCBuilder;
import com.sequenceiq.cloudbreak.reactor.api.event.StackFailureEvent;
import com.sequenceiq.cloudbreak.service.cluster.ClusterService;
import com.sequenceiq.cloudbreak.service.stack.StackService;

public abstract class AbstractClusterResetAction<P extends Payload> extends AbstractAction<ClusterResetState, ClusterResetEvent, ClusterContext, P> {
    @Inject
    private StackService stackService;

    @Inject
    private ClusterService clusterService;

    protected AbstractClusterResetAction(Class<P> payloadClass) {
        super(payloadClass);
    }

    @Override
    protected ClusterContext createFlowContext(String flowId, StateContext<ClusterResetState, ClusterResetEvent> stateContext, P payload) {
        Stack stack = stackService.getById(payload.getStackId());
        Cluster cluster = clusterService.retrieveClusterByStackId(stack.getId());
        MDCBuilder.buildMdcContext(stack.getId().toString(), stack.getName(), stack.getOwner(), "CLUSTER");
        return new ClusterContext(flowId, stack, cluster);
    }

    @Override
    protected Object getFailurePayload(P payload, Optional<ClusterContext> flowContext, Exception ex) {
        return new StackFailureEvent(payload.getStackId(), ex);
    }
}
