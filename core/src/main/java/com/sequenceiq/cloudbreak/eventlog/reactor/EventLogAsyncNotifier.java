package com.sequenceiq.cloudbreak.eventlog.reactor;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.service.ErrorHandlerAwareFlowEventFactory;
import com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class EventLogAsyncNotifier {
    public static final String EVENT_LOG = "EVENT_LOG";

    @Inject
    private ErrorHandlerAwareFlowEventFactory eventFactory;

    @Inject
    private EventBus eventBus;

    public void notifyEventLogEntry(EventLogEntry eventLogEntry) {
        sendAsyncEvent(EVENT_LOG, eventFactory.createEvent(eventLogEntry));
    }

    private void sendAsyncEvent(String selector, Event<?> event) {
        eventBus.notify(selector, event);
    }
}
