package com.ggos3.verifier.dto.request;

public record EncVcRequest(
        String sessionId,
        String userDID,
        String vcEncHex,
        String vcId
) {
}
