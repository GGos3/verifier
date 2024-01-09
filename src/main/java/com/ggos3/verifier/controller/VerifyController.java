package com.ggos3.verifier.controller;

import com.ggos3.verifier.config.PropertyManager;
import com.ggos3.verifier.dto.request.EncVcRequest;
import com.ggos3.verifier.dto.response.QrDataResponse;
import com.ggos3.verifier.service.VpService;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.exception.IWException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/omniapi/vc/v2/verify")
public class VerifyController {
    private final PropertyManager propertyManager;
    private final VpService vpService;

    @GetMapping("/profile")
    public ResultJson getProfile(@RequestParam String nonce) throws Exception {
        return vpService.getProfile(nonce);
    }

    @GetMapping("/getQRData")
    public QrDataResponse getQrData() throws IWException {
        String nonce = propertyManager.createNonce();
        return new QrDataResponse(
                null, "http://10.48.17.216:8080/omniapi/vc/v2/verify/profile?nonce=" + nonce, null, null, null, null, null, null
        );
    }

    @PostMapping("/encVcVerify")
    public boolean verifyVp(@RequestBody EncVcRequest request) {
        log.info("EncVcReqeustSessionId ={}", request.sessionId());
        log.info("EncVcReqeustUserDID ={}", request.userDID());
        log.info("EncVcReqeustVcEncHex ={}", request.vcEncHex());
        log.info("EncVcReqeustVcId ={}", request.vcId());
        return true;
    }
}
