package com.ggos3.verifier.common.service;

import com.ggos3.verifier.common.config.ConfigBean;
import com.raonsecure.omnione.core.crypto.GDPCryptoHelperClient;
import com.raonsecure.omnione.core.data.iw.profile.EncryptKeyTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.EncryptTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.Profile;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.core.eoscommander.crypto.digest.Sha256;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VpServiceImpl implements VpService {

    private final ConfigBean configBean;

    @Override
    public String makeDIDAssertion(String nonce) {
        return null;
    }

    @Override
    public String getProfile() throws Exception{

        // ProFile 생성
        Profile profile = new Profile();

        profile.setEncryptType(EncryptTypeEnum.AES_256);
        profile.setSpName(configBean.getSpAccount());
        profile.setNonce(Sha256.from(new GDPCryptoHelperClient().generateNonce()).toString());
        profile.setKeyType(EncryptKeyTypeEnum.ALGORITHM_RSA);
        profile.setPresentType(1);

        // SP ProfileParam 생성
        SpProfileParam spProfileParam = new SpProfileParam(
                configBean.getBlockChainServerInfo(),
                configBean.getKeyManager(),
                configBean.getSpKeyId(),
                configBean.getSpServiceCode(),
                profile,
                configBean.getDidDoc().getId(),
                configBean.getSpAccount()
        );
        spProfileParam.setEncryptKeyId(configBean.getSpRsaKeyId());

        // 블록체인으로 프로필 생성 요청
        String spProfileJson = VerifyApi.makeSpProfile(spProfileParam);
        ResultProfile resultJson = new ResultProfile();

        resultJson.setResult(true);
        resultJson.setProfileJson(spProfileJson);

        return resultJson.getProfileBase64();
    }

    @Override
    public Boolean verifyVP() {
        return null;
    }


}
