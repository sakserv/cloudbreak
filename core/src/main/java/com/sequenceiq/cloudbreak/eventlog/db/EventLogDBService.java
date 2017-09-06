package com.sequenceiq.cloudbreak.eventlog.db;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.eventlog.EventLogService;
import com.sequenceiq.cloudbreak.domain.EventLogEntity;

@Component
public class EventLogDBService implements EventLogService {
    @Inject
    private ConversionService conversionService;

    @Inject
    private EventLogRepostiory eventLogRepostiory;

    @Override
    public void storeEventLogEntry(com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry eventLogEntry) {
        EventLogEntity eventLogEntityEntity = conversionService.convert(eventLogEntry, EventLogEntity.class);
        eventLogRepostiory.save(eventLogEntityEntity);
    }
}
