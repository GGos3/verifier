package com.ggos3.verifier.config;

import com.ggos3.verifier.common.config.ConfigBean;
import com.raonsecure.omnione.core.crypto.GDPCryptoHelperClient;
import com.raonsecure.omnione.core.data.did.v2.DIDs;
import com.raonsecure.omnione.core.data.iw.profile.EncryptTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.Profile;
import com.raonsecure.omnione.core.eoscommander.crypto.digest.Sha256;
import com.raonsecure.omnione.core.exception.IWException;
import com.raonsecure.omnione.core.key.IWKeyManagerInterface;
import com.raonsecure.omnione.core.key.KeyManagerFactory;
import com.raonsecure.omnione.core.key.store.IWDIDFile;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.ServerInfo;
import com.raonsecure.omnione.sdk_server_core.data.IWApiBaseData;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class PropertyManager {

    private final ConfigBean configBean;

    // VP Properties
    private ServerInfo blockChainServerInfo;
    private IWDIDFile didFile;
    private DIDs didDoc;
    private IWKeyManagerInterface keyManager;
    private IWApiBaseData spApiBaseData;


    @PostConstruct
    public void init() throws Exception {
        // 블록체인 서버 정보
        this.blockChainServerInfo = new ServerInfo(configBean.getBlockchainServerDomain());

        // DID 문서 로드
        this.didFile = new IWDIDFile(configBean.getSpDidPath());
        this.didDoc = this.didFile.getDataFromDIDsV2();

        // DIDAuth를 위한 ApiBaseClass
        this.spApiBaseData = new IWApiBaseData(
                this.blockChainServerInfo,
                this.keyManager,
                configBean.getSpKeyId(),
                configBean.getSpAccount()
        );

        // KeyManger 설정
        this.keyManager = KeyManagerFactory.getKeyManager(
                KeyManagerFactory.KeyManagerType.DEFAULT,
                configBean.getSpKeyManagerPath(),
                configBean.getSpKeyManagerPassword().toCharArray()
        );

        // KeyManager UnLock
        unLockKeyManager(configBean.getSpKeyManagerPassword().toCharArray());
    }


    /**
     * @param callBackUrl
     * @param presentType 0(NONE), 1(AES_128), 2(AES_256), 3(ARIA_128), 4(ARIA256)
     * @return SpProfileParam
     * @throws IWException
     */

    public SpProfileParam createSpProfileParam(String callBackUrl, String nonce) throws IWException {
        Profile profile = new Profile();

        profile.setCallBackUrl(callBackUrl);
        profile.setEncryptType(EncryptTypeEnum.AES_128);
        profile.setNonce(nonce);
        profile.setSpName("jpkim");
//        profile.setKeyType(EncryptKeyTypeEnum.ALGORITHM_SECP256K1);
//        profile.setPresentType(presentType);


        return new SpProfileParam(
                this.blockChainServerInfo,
                this.keyManager,
                configBean.getSpKeyId(),
                configBean.getSpServiceCode(),
                profile,
                this.didDoc.getId(),
                configBean.getSpAccount()
        );
    }


    private void unLockKeyManager(char[] keyMangerPassword) throws IWException {
        this.keyManager.unLock(keyMangerPassword, new IWKeyManagerInterface.OnUnLockListener() {
            @Override
            public void onSuccess() {
                log.debug("Wallet UnLock Success");
            }

            @Override
            public void onFail(int i) {
                log.error("Wallet UnLock Failed");
            }

            @Override
            public void onCancel() {
                log.error("Wallet UnLock Canceled");
            }
        });
    }


    public String createNonce() throws IWException {
        return Sha256.from(
                new GDPCryptoHelperClient().generateNonce()
        ).toString();
    }
}
