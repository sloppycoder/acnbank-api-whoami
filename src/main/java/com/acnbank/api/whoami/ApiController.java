package com.acnbank.api.whoami;

import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @RequestMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public String decipherAccessToken() {
        return getDecodedToken();
    }

    private String getDecodedToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AuthenticationJsonWebToken) {
            String token = ((AuthenticationJsonWebToken) auth).getToken();
            String decoded = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
            log.info("{}", decoded);
            return decoded;
        } else {
            return "{}";
        }
    }

    @RequestMapping("/offers")
    @PreAuthorize("#oauth2.hasScope('api:prelogin')")
    public String getOffers() {
        log.info("sending offers....");
        return "{\"offers\" : [] }";
    }

    @RequestMapping("/transactions")
    @PreAuthorize("#oauth2.hasScope('api:authenticated')")
    public String getTransactionHistory() {
        log.info("sending transactions history....");
        return "{\"transactions\" : [] }";
    }

    @RequestMapping("/transfers")
    @PreAuthorize("#oauth2.hasScope('api:mfa')")
    public String performFundTransfer() {
        return "{\"status\" : \"success\"}";
    }
}
