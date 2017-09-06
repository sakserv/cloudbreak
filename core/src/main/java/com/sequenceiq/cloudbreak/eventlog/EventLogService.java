package com.sequenceiq.cloudbreak.eventlog;

import com.sequenceiq.cloudbreak.eventlog.event.EventLogEntry;

public interface EventLogService {
    void storeEventLogEntry(EventLogEntry eventLogEntry);
}
