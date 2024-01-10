package com.ggos3.verifier.service.impl;

import com.ggos3.verifier.config.PropertyManager;
import com.ggos3.verifier.service.DIDAuthService;
import com.ggos3.verifier.service.VerifyService;
import com.ggos3.verifier.service.VpService;
import com.raonsecure.omnione.core.data.did.DIDDefaultAssertion;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {
    private final PropertyManager propertyManager;
    private final VpService vpService;
    private final DIDAuthService didAuthService;

    @Override
    public String createNonce() {
        return propertyManager.createNonce();
    }

    @Override
    public ResultJson getProfile(String nonce) {
        String profile = vpService.getProfile(nonce);
        ResultProfile resultJson = new ResultProfile();

        resultJson.setResult(true);
        resultJson.setProfileJson(profile);

        return resultJson;
    }

    @Override
    public ResultJson verifyVp(String vcVerifyProfileResult) {
        VcResult vcResult = vpService.verifyVP(vcVerifyProfileResult);
        ResultJson resultJson = new ResultJson();

        resultJson.setResult(vcResult.getStatus().equals("1"));

        return resultJson;
    }

    @Override
    public ResultJson verifyDIDAuth(DIDDefaultAssertion didAuth) {
        ResultJson resultJson = new ResultJson();

        try {
            didAuthService.verifyDIDAuth(didAuth);
            resultJson.setResult(true);
        } catch (BlockChainException e) {
            log.error("BlockChainError ={}", e.getErrorCode());
            e.printStackTrace();
            resultJson.setErrorCode(e.getErrorCode());
            resultJson.setErrorMsg(e.getErrorMsg());
        }

        return resultJson;
    }
}
