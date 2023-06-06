package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialService {
    private final CredentialMapper credentialMapper;

    private final EncryptionService encryptionService;

    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public Credential getCredentialById(Integer credentialId, Integer userId) {
        return credentialMapper.getCredentialById(credentialId, userId);
    }

    public int addCredential(Credential credential) {
        encryptPassword(credential);
        return credentialMapper.addCredential(credential);
    }

    public int deleteCredentialById(Integer credentialId, Integer userId) {
        return credentialMapper.deleteCredentialById(credentialId, userId);
    }

    public int editCredentialById(Credential credential) {
        encryptPassword(credential);
        return credentialMapper.editCredentialById(credential);
    }

    public void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
    }

    public void decryptPassword(Credential credential) {
        if (credential.getKey() == null) {
            return;
        }
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(decryptedPassword);
    }

}
