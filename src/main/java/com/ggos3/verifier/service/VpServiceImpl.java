package com.ggos3.verifier.service;

import com.ggos3.verifier.common.config.ConfigBean;
import com.ggos3.verifier.config.PropertyManager;
import com.google.gson.Gson;
import com.raonsecure.omnione.core.data.iw.PrivacyVcType;
import com.raonsecure.omnione.core.data.iw.Unprotected;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
import com.raonsecure.omnione.sdk_verifier.api.data.VcVerifyParam;
import com.raonsecure.omnione.sdk_verifier.api.data.VcVerifyProfileParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResultJson verifyVP(String vcVerifyRequest) throws BlockChainException, HttpException {
//        log.info("vcVerifyRequest ={}", vcVerifyRequest);
        VCVerifyProfileResult vcVerifyProfileResult = new VCVerifyProfileResult();
        vcVerifyProfileResult.fromJson(vcVerifyRequest);

        VcVerifyProfileParam vcVerifyProfileParam = propertyManager.createVcVerifyProfileParam(vcVerifyProfileResult);
        VcResult vcResult = VerifyApi.verify(vcVerifyProfileParam, false);


//        if(vcResult.getStatus().equals("1")) {
//            // 4-1. claim code-value 추출
            List<Unprotected> privacyList = vcResult.getPrivacyList();
            for (Unprotected unprotected : privacyList) {
                log.info("claim : " + unprotected.getType() + " , value : " + unprotected.getValue());
            }

//            // 4-2. vctype-(claim code-value) 추출
            List<PrivacyVcType> privacyVcTypes = vcResult.getPrivacyListWithVcType();

            for (PrivacyVcType privacyVcType : privacyVcTypes) {
                log.info("vcType: " + privacyVcType.getVcType());
                for (Unprotected unprotected : privacyVcType.getUnprotected()) {
                    log.info("claim: " + unprotected.getType());
                    log.info("value: " + unprotected.getValue());
                }
            }
            // vc-id 추출
            List<String> vcIdList = vcResult.getVcIdList();
            for(String vcId : vcIdList) {
                log.info("vcId: " + vcId);
            }

//             증명서별로 특정 claim을 추출
            List<String> claimList = new ArrayList<String>();
            claimList.add("license");
            claimList.add("name");

            List<List<Unprotected>> privacyListByVc = vcResult.getPrivacyListByVc(claimList);
            for (List<Unprotected> privacySet : privacyListByVc) {
                for (Unprotected unprotected : privacySet) {
                    String type = unprotected.getType();
                    String value = unprotected.getValue();
                }
            }

        ResultJson resultJson = new ResultJson();
        resultJson.setResult("1".equals(vcResult.getStatus()));

        return resultJson;
    }
}
