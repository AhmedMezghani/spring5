package com.example.pidevcocomarket.utils;

import lombok.Data;

@Data
public class RecaptchaResponseDTO {
    private boolean success;
    private String challenge_ts;
    private String hostname;
}
