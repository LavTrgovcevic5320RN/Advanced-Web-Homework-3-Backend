package com.raf.nwpdomaci3.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
