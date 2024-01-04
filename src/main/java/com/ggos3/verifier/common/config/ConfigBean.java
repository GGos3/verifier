package com.ggos3.verifier.common.config;

import com.raonsecure.omnione.core.data.did.v2.DIDs;
import com.raonsecure.omnione.core.exception.IWException;
import com.raonsecure.omnione.core.key.IWKeyManagerInterface;
import com.raonsecure.omnione.core.key.KeyManagerFactory;
import com.raonsecure.omnione.core.key.store.IWDIDFile;
import com.raonsecure.omnione.core.util.GDPLogger;
import com.raonsecure.omnione.sdk_server_core.OmniOption;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.ServerInfo;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class ConfigBean {
    private String spKeyManagerPath;
    private String spKeyManagerPassword;
    private String spAccount;
    private String spKeyId;
    private String spRsaKeyId;
    private String spDidPath;
    private String spServiceCode;
    private String verifyCheckBlockchain;
    private Boolean sdkDetailLog;
    private String blockchainServerDomain;
    private String serverDomain;

    private ServerInfo blockChainServerInfo;
    private IWKeyManagerInterface keyManager;
    private IWDIDFile didFile;
    private DIDs didDoc;

    @PostConstruct
    public void init() throws Exception{
        // 블록체인 정보 설정
        this.blockChainServerInfo = new ServerInfo(getBlockchainServerDomain());

        // KeyManager 설정 및 잠금 해제
        setKeyManager();

        // DID File, DID Doc 설정
        File didFile = ResourceUtils.getFile(this.getSpDidPath());
        String didFilePath = didFile.getAbsolutePath();

        this.didFile = new IWDIDFile(didFilePath);
        this.didDoc = this.didFile.getDataFromDIDsV2();
    }

    public void setSdkDetailLog(Boolean sdkDetailLog) {
        this.sdkDetailLog = sdkDetailLog;

        if (sdkDetailLog) {
            OmniOption.setSdkDetailLog(true);
            GDPLogger.setLevel(GDPLogger.LogLevelType.DEBUG);
        }
    }

    private void setKeyManager() throws FileNotFoundException, IWException {
        File keyManagerFile = ResourceUtils.getFile(this.getSpKeyManagerPath());
        String keyManagerPath = keyManagerFile.getAbsolutePath();

        this.keyManager = KeyManagerFactory.getKeyManager(
                KeyManagerFactory.KeyManagerType.DEFAULT,
                keyManagerPath,
                this.getSpKeyManagerPassword().toCharArray()
        );

        this.keyManager.unLock(this.getSpKeyManagerPassword().toCharArray(), new IWKeyManagerInterface.OnUnLockListener() {
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

}
