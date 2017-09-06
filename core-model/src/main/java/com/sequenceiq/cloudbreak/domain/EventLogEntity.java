package com.sequenceiq.cloudbreak.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sequenceiq.cloudbreak.domain.json.Json;
import com.sequenceiq.cloudbreak.domain.json.JsonToString;

@Entity
@Table(name = "eventlog")
public class EventLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eventlog_generator")
    @SequenceGenerator(name = "eventlog_generator", sequenceName = "eventlog_id_seq", allocationSize = 1)
    private Long id;

    @Convert(converter = JsonToString.class)
    @Column(columnDefinition = "TEXT")
    private Json eventLogJson;

    public EventLogEntity(Json eventLogJson) {
        this.eventLogJson = eventLogJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Json getEventLogJson() {
        return eventLogJson;
    }

    public void setEventLogJson(Json eventLogJson) {
        this.eventLogJson = eventLogJson;
    }
}
