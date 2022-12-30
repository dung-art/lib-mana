package com.lib.manage.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String id;
    private String token; //jwt
    private String type = "Bearer";
    private String username;
    private String accountType;

    public JwtResponse(String accessToken, String id, String username, String roles) {
        this.token = accessToken;
        this.username = username;
        this.accountType = roles;
        this.id = id;
    }
}
