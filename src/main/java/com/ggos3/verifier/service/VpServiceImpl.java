package com.ggos3.verifier.service;

import com.ggos3.verifier.common.config.ConfigBean;
import com.ggos3.verifier.config.PropertyManager;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
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
    public ResultJson getProfile(String nonce) throws Exception{
        SpProfileParam spProfileParam = propertyManager.createSpProfileParam(configBean.getCallBackUrl(), nonce);
        String spProfileJson = VerifyApi.makeSpProfile(spProfileParam);

        log.debug("spProfileJson ={}", spProfileJson);

        // 응답 데이터 설정
        ResultProfile resultJson = new ResultProfile();

        resultJson.setResult(true);
        resultJson.setProfileJson(spProfileJson);

        return resultJson;
    }

    @Override
    public Boolean verifyVP() {
        return null;
    }
}
