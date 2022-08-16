package com.nhnacademy.marketgg.client.jwt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ShaPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        byte[] passBytes = rawPassword.toString().getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : digested) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Objects.equals(this.encode(rawPassword), encodedPassword);
    }

}
