package com.sequenceiq.cloudbreak.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "instanceGroups")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceGroupRequest extends InstanceGroupBase {

    @ApiModelProperty(ModelDescriptions.InstanceGroupModelDescription.TEMPLATE)
    private TemplateRequest template;

    @ApiModelProperty(ModelDescriptions.InstanceGroupModelDescription.SECURITYGROUP)
    private SecurityGroupRequest securityGroup;

    public TemplateRequest getTemplate() {
        return template;
    }

    public void setTemplate(TemplateRequest template) {
        this.template = template;
    }

    public SecurityGroupRequest getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(SecurityGroupRequest securityGroup) {
        this.securityGroup = securityGroup;
    }
}
