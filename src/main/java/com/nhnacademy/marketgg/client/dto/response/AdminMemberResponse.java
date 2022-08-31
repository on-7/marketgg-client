package com.nhnacademy.marketgg.client.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.marketgg.client.jwt.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminMemberResponse {

    private String uuid;
    private String email;
    private String name;
    private String phoneNumber;
    private String memberGrade;
    private Character gender;
    private List<String> roles;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN.name());
    }

}
