package com.acnbank.api.whoami;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @RequestMapping("/whoami")
    public String decipherAccessToken() {
        return getDecodedToken();
    }

    private String getDecodedToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof OAuth2Authentication) {
            String token = ((OAuth2AuthenticationDetails) ((OAuth2Authentication) auth).getDetails()).getTokenValue();
            String decoded = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
            log.info("{}", decoded);
            return decoded;
        }
        return "not an OAUTH2 access token";
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
