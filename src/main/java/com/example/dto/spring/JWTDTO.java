package com.example.dto.spring;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTDTO {
    private String id;

    public JWTDTO(String email, ProfileRole role) {
        this.email = email;
        this.role = role;
    }

    private String email;
    private ProfileRole role;

    public JWTDTO(String id) {
        this.id = id;
    }

    public JWTDTO(String id, String email, ProfileRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
