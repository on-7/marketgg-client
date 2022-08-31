package com.nhnacademy.marketgg.client.oauth2;

import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class GoogleProfile implements OauthProfile, Serializable {

    private static final long serialVersionUID = 5941122041950251205L;

    private String email;
    private String name;
    private String provider;

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProvider() {
        return this.provider;
    }

}
