//package com.amigoscode.bookbuddybackend.dto.response;
//
//public class AuthResponse {
//
//    private String jwt;
//
//    public AuthResponse(String jwt) {
//        this.jwt = jwt;
//    }
//
//    // Getters and Setters
//
//    public String getJwt() {
//        return jwt;
//    }
//
//    public void setJwt(String jwt) {
//        this.jwt = jwt;
//    }
//}

package com.amigoscode.bookbuddybackend.dto.response;

public class AuthResponse {

    private String jwt;

    public AuthResponse() {}

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getter and Setter
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
