package com.ggos3.verifier.service;


import com.raonsecure.omnione.sdk_server_core.data.VcResult;

public interface VpService {
    String getProfile(String nonce);

    VcResult verifyVP(String vcVerifyProfileResult);
}
