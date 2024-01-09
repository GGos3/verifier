package com.ggos3.verifier.common.config;

import com.raonsecure.omnione.core.util.GDPLogger;
import com.raonsecure.omnione.sdk_server_core.OmniOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
    private String callBackUrl;

    public void setSdkDetailLog(Boolean sdkDetailLog) {
        this.sdkDetailLog = sdkDetailLog;

        if (sdkDetailLog) {
            OmniOption.setSdkDetailLog(true);
            GDPLogger.setLevel(GDPLogger.LogLevelType.DEBUG);
        }
    }
}
