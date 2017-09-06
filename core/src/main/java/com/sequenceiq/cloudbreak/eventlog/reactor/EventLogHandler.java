package com.sequenceiq.cloudbreak.eventlog.reactor;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.eventlog.EventLogService;
import com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry;
import com.sequenceiq.cloudbreak.reactor.handler.ReactorEventHandler;

import reactor.bus.Event;

@Component
public class EventLogHandler<T extends EventLogEntry> implements ReactorEventHandler<T> {
    @Inject
    private EventLogService eventLogService;

    @Override
    public String selector() {
        return EventLogAsyncNotifier.EVENT_LOG;
    }

    @Override
    public void accept(Event<T> eventLogEntry) {
        eventLogService.storeEventLogEntry(eventLogEntry.getData());
    }
}
