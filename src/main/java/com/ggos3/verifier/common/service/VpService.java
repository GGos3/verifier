package com.ggos3.verifier.common.service;



public interface VpService {
    String makeDIDAssertion(String nonce);
    String getProfile() throws Exception;
    Boolean verifyVP();
}
