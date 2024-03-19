package com.acnbank.api.whoami;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @RequestMapping("/whoami")
    public String whoAmI(@AuthenticationPrincipal Jwt jwt) {
        var sub = jwt.getClaim("sub");
        var email = jwt.getClaim("email");
        return String.format("hello %s, your email is %s", sub, email);
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
