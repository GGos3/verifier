package com.ggos3.verifier.dto.response;

public record VerifyVpWithResultResponse(
        String address,
        String birthDate,
        String driveLicenseNumber,
        String driveLicenseType,
        String name,
        String nation
) {
}
