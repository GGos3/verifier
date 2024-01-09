package com.ggos3.verifier.service;


import com.raonsecure.omnione.core.data.rest.ResultJson;

public interface VpService {
    ResultJson getProfile(String nonce) throws Exception;
    Boolean verifyVP();
}
