package com.javachallengers.simpson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/oauth-test")
public class OAuthController {

  @GetMapping("/")
  public String helloWorld() {
    return "if you see this you are logged in";
  }

  @GetMapping("/restricted")
  public String restricted() {
    return "if you see this you are logged in";
  }

}
