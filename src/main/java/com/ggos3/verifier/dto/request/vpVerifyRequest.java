package com.ggos3.verifier.dto.request;

public record vpVerifyRequest(
        String data,
        String did,
        int encryptType,
        String nonce,
        String type,
        String vcId
) {
}
