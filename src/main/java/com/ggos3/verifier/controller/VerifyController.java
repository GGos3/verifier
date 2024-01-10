package com.ggos3.verifier.controller;

import com.ggos3.verifier.dto.response.QrDataResponse;
import com.ggos3.verifier.service.VerifyService;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/omniapi/vc/v2/verify")
public class VerifyController {
    private final VerifyService verifyService;

    @PostMapping("/")
    public ResultJson verifyVp(@RequestBody String request) {
        return verifyService.verifyVp(request);
    }


    @GetMapping("/profile")
    public ResultJson getProfile(@RequestParam String nonce) {
        return verifyService.getProfile(nonce);
    }


    @GetMapping("/getQRData")
    public QrDataResponse getQrData() {
        String nonce = verifyService.createNonce();

        return new QrDataResponse(
                "http://10.48.17.216:8080/omniapi/vc/v2/verify/profile?nonce=" + nonce
        );
    }
}
