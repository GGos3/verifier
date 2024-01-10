package com.ggos3.verifier.service.impl;

import com.ggos3.verifier.config.PropertyManager;
import com.ggos3.verifier.service.DIDAuthService;
import com.raonsecure.omnione.core.data.did.DIDDefaultAssertion;
import com.raonsecure.omnione.sdk_server_core.api.DidAuthVerify;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DIDAuthServiceImpl implements DIDAuthService {
    private final PropertyManager propertyManager;


    @Override
    public void verifyDIDAuth(DIDDefaultAssertion didAuth) throws BlockChainException {
        DidAuthVerify didAuthVerify = new DidAuthVerify();

        didAuthVerify.verify(propertyManager.getSpApiBaseData(), didAuth.toJson(), false);
    }
}
