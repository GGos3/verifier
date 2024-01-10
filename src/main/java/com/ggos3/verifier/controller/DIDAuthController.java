package com.ggos3.verifier.controller;

import com.ggos3.verifier.service.VerifyService;
import com.raonsecure.omnione.core.data.did.DIDDefaultAssertion;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/omniapi/vc/v2/didAuth")
public class DIDAuthController {
    private final VerifyService verifyService;

    @PostMapping("/verify2")
    public ResultJson verifyDIDAuth(@RequestBody DIDDefaultAssertion data) {
        return verifyService.verifyDIDAuth(data);
    }
}
