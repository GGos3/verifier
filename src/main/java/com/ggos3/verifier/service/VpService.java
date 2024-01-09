package com.ggos3.verifier.service;


import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;

public interface VpService {
    ResultJson getProfile(String nonce) throws Exception;

    ResultJson verifyVP(String vcVerifyProfileResult) throws BlockChainException, HttpException;
}
