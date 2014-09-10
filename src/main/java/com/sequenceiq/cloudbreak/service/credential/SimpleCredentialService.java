package com.sequenceiq.cloudbreak.service.credential;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.controller.NotFoundException;
import com.sequenceiq.cloudbreak.domain.AzureCredential;
import com.sequenceiq.cloudbreak.domain.CbUser;
import com.sequenceiq.cloudbreak.domain.CloudPlatform;
import com.sequenceiq.cloudbreak.domain.Credential;
import com.sequenceiq.cloudbreak.domain.Status;
import com.sequenceiq.cloudbreak.domain.WebsocketEndPoint;
import com.sequenceiq.cloudbreak.repository.CredentialRepository;
import com.sequenceiq.cloudbreak.service.credential.azure.AzureCertificateService;
import com.sequenceiq.cloudbreak.websocket.WebsocketService;
import com.sequenceiq.cloudbreak.websocket.message.StatusMessage;

@Service
public class SimpleCredentialService implements CredentialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCredentialService.class);

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private AzureCertificateService azureCertificateService;

    @Autowired
    private WebsocketService websocketService;


    @Override
    public Set<Credential> retrievePrivateCredentials(CbUser user) {
        return credentialRepository.findForUser(user.getUsername());
    }

    @Override
    public Set<Credential> retrieveAccountCredentials(CbUser user) {
        Set<Credential> credentials = new HashSet<>();
        if (user.getRoles().contains("admin")) {
            credentials = credentialRepository.findAllInAccount(user.getAccount());
        } else {
            credentials = credentialRepository.findPublicsInAccount(user.getAccount());
        }
        return credentials;
    }

    @Override
    public Credential get(Long id) {
        Credential credential = credentialRepository.findOne(id);
        if (credential == null) {
            throw new NotFoundException(String.format("Credential '%s' not found.", id));
        } else {
            return credential;
        }
    }

    @Override
    public Credential create(CbUser user, Credential credential) {
        LOGGER.debug("Creating credential: [User: '{}', Account: '{}']", user.getUsername(), user.getAccount());
        credential.setOwner(user.getUsername());
        credential.setAccount(user.getAccount());
        if (CloudPlatform.AZURE.equals(credential.cloudPlatform())) {
            AzureCredential azureCredential = (AzureCredential) credential;
            if (azureCredential.getPublicKey() != null) {
                azureCertificateService.generateSshCertificate(user, azureCredential, azureCredential.getPublicKey());
            }
            azureCertificateService.generateCertificate(user, azureCredential);
        }
        Credential savedCredential = credentialRepository.save(credential);
        websocketService.sendToTopicUser(user.getUsername(), WebsocketEndPoint.CREDENTIAL,
                new StatusMessage(credential.getId(), credential.getName(), Status.AVAILABLE.name()));
        return savedCredential;
    }

    @Override
    public void delete(Long id) {
        Credential credential = credentialRepository.findOne(id);
        if (credential == null) {
            throw new NotFoundException(String.format("Credential '%s' not found.", id));
        }
        credentialRepository.delete(credential);
        websocketService.sendToTopicUser(credential.getOwner(), WebsocketEndPoint.CREDENTIAL,
                new StatusMessage(credential.getId(), credential.getName(), Status.DELETE_COMPLETED.name()));
    }
}
