
package com.karka_deh.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

  @GetMapping("/")
  public String publicPage() {
    return "Hello there";
  }

  /**
   * soley for testing purposes
   */
  @GetMapping("/home")
  public String homePage(Authentication auth) {
    if (auth != null && auth.isAuthenticated()) {
      return "Hello " + auth.getName() + "! ";
    }
    return "Not logged in";
  }
}
