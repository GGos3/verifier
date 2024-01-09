package com.ggos3.verifier.controller;

import com.ggos3.verifier.config.PropertyManager;
import com.ggos3.verifier.dto.request.vpVerifyRequest;
import com.ggos3.verifier.dto.response.QrDataResponse;
import com.ggos3.verifier.service.VpService;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.exception.IWException;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
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

    @PostMapping("/")
    public ResultJson verifyVp(@RequestBody String request) throws BlockChainException, HttpException {
        return vpService.verifyVP(request);
    }


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
}
