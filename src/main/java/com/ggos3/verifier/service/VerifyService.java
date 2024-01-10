package com.ggos3.verifier.service;

import com.raonsecure.omnione.core.data.rest.ResultJson;

public interface VerifyService {
    String createNonce();
    ResultJson getProfile(String nonce);
    ResultJson verifyVp(String vcVerifyProfileResult);
}
