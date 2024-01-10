package com.ggos3.verifier.service;


import com.raonsecure.omnione.core.data.did.DIDDefaultAssertion;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;

public interface DIDAuthService {
    void verifyDIDAuth(DIDDefaultAssertion didAuth) throws BlockChainException;
}
