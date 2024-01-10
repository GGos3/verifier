package com.ggos3.verifier.dto.response;

public record QrDataResponse(
        String type,
        String profileUrl,
        String spDid,
        String serviceCode,
        String calBackUrl,
        String nonce,
        String encryptType,
        String sessionId
) {
    public QrDataResponse(String profileUrl) {
        this(null, profileUrl, null, null, null, null, null, null);
    }
}
