package com.sequenceiq.cloudbreak.eventlog.db.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.EventLogEntity;
import com.sequenceiq.cloudbreak.domain.json.Json;
import com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry;
import com.sequenceiq.cloudbreak.util.JsonUtil;

@Component
public class EventLogEntryToEventLogEntityConverter extends AbstractConversionServiceAwareConverter<EventLogEntry, EventLogEntity> {
    @Override
    public EventLogEntity convert(EventLogEntry source) {
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("type", source.getClass().getSimpleName());
            map.put("value", source);
            return new EventLogEntity(new Json(JsonUtil.createJsonTree(map)));
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
