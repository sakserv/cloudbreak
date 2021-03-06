package com.sequenceiq.cloudbreak.core.bootstrap.config;

import java.util.Optional;

import com.sequenceiq.cloudbreak.orchestrator.model.ContainerConfig;

public class ContainerConfigBuilder {

    private final ContainerConfig containerConfig;

    private ContainerConfigBuilder(Builder builder) {
        this.containerConfig = new ContainerConfig(builder.name, builder.version, builder.queue);
    }

    private ContainerConfig getContainerConfig() {
        return containerConfig;
    }

    public static class Builder {

        private final String name;

        private final String version;

        private final String queue;

        public Builder(String imageNameAndVersion, Optional<String> queue) {
            String[] image = imageNameAndVersion.split(":");
            this.name = image[0];
            this.version = image.length > 1 ? image[1] : "latest";
            this.queue = queue.isPresent() ? queue.get() : "default";

        }

        public ContainerConfig build() {
            return new ContainerConfigBuilder(this).getContainerConfig();
        }

    }
}
