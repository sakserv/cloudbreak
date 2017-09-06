package com.sequenceiq.cloudbreak.eventlog.db.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.EventLogEntity;

@Component
public class EventLogEntityToEventLogEntryConverter extends AbstractConversionServiceAwareConverter<EventLogEntity, EventLogEntry> {
    @Override
    public EventLogEntry convert(EventLogEntity source) {
        Map<String, Object> map = source.getEventLogJson().getMap();
        return (EventLogEntry) map.get("value");
    }
}
