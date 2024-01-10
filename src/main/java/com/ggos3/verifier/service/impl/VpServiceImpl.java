package com.ggos3.verifier.service.impl;

import com.ggos3.verifier.common.config.ConfigBean;
import com.ggos3.verifier.config.PropertyManager;
import com.ggos3.verifier.service.VpService;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
import com.raonsecure.omnione.sdk_verifier.api.data.VcVerifyProfileParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class VpServiceImpl implements VpService {

    private final PropertyManager propertyManager;
    private final ConfigBean configBean;

    @Override
    public String getProfile(String nonce) {
        SpProfileParam spProfileParam = propertyManager.createSpProfileParam(configBean.getCallBackUrl(), nonce);
        String spProfileJson = null;

        try {
            spProfileJson = VerifyApi.makeSpProfile(spProfileParam);
        } catch (BlockChainException e) {
            log.error("BlockChainError ={}", e.getErrorCode());
            e.printStackTrace();
        } catch (HttpException e) {
            log.error("HttpError ={}", e.getHttpErrorCode());
            e.printStackTrace();
        }

        log.debug("spProfileJson ={}", spProfileJson);
        return spProfileJson;
    }

    @Override
    public VcResult verifyVP(String vcVerifyRequest) {
        VCVerifyProfileResult vcVerifyProfileResult = new VCVerifyProfileResult();
        vcVerifyProfileResult.fromJson(vcVerifyRequest);

        VcVerifyProfileParam vcVerifyProfileParam = propertyManager.createVcVerifyProfileParam(vcVerifyProfileResult);
        VcResult vcResult = null;

        try {
            vcResult = VerifyApi.verify(vcVerifyProfileParam, false);
        } catch (BlockChainException e) {
            log.error("BlockChainError ={}", e.getErrorCode());
            e.printStackTrace();
        } catch (HttpException e) {
            log.error("HttpError ={}", e.getHttpErrorCode());
            e.printStackTrace();
        }

        return vcResult;
    }
}
