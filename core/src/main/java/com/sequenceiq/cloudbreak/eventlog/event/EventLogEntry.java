package com.sequenceiq.cloudbreak.eventlog.event;

import java.util.Calendar;
import java.util.Date;

public class EventLogEntry {
    private String eventType;

    private Long resourceId;

    private String resourceType;

    private Date timestamp;

    public EventLogEntry(String eventType, String resourceType, Long resourceId) {
        this.eventType = eventType;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public String getEventType() {
        return eventType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
