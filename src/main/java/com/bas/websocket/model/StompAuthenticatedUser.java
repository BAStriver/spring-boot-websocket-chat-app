package com.bas.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StompAuthenticatedUser implements Principal {

    private String userId;

    private String nickName;

    @Override
    public String getName() {
        return this.userId;
    }

}

