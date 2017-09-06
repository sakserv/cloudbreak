package com.sequenceiq.cloudbreak.eventlog.db;

import org.springframework.data.repository.CrudRepository;

import com.sequenceiq.cloudbreak.domain.EventLogEntity;
import com.sequenceiq.cloudbreak.repository.EntityType;

@EntityType(entityClass = EventLogEntity.class)
public interface EventLogRepostiory extends CrudRepository<EventLogEntity, Long> {
}
