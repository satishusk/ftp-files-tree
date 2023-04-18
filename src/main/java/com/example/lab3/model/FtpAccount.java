package com.example.lab3.model;

import lombok.Getter;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public enum FtpAccount {
  EMPTY("", "", "", "", ""),
  COMMON("common", "91.222.128.11", "21", "testftp_guest", "12345");

  private final String profile;
  private final String hostname;
  private final String port;
  private final String username;
  private final String password;

  FtpAccount(String profile, String hostname, String port, String username, String password) {
    this.profile = profile;
    this.hostname = hostname;
    this.port = port;
    this.username = username;
    this.password = password;
  }

  public Map<String, Object> toMap() {
    return Map.of(
      "hostname", getHostname(),
      "port", getPort(),
      "username", getUsername(),
      "password", getPassword()
    );
  }

  public static FtpAccount byProfile(ApplicationContext context) {
    List<String> profiles = Arrays.asList(context.getEnvironment().getActiveProfiles());
    FtpAccount foundedAccount = FtpAccount.EMPTY;
    for (FtpAccount ftpAccount : FtpAccount.values()) {
      if (profiles.contains(ftpAccount.profile)) {
        if (foundedAccount == FtpAccount.EMPTY) {
          foundedAccount = ftpAccount;
        } else {
          throw new IllegalStateException(
            "Non-deterministic choice of ftp account. " +
              "Profile conflict: " + foundedAccount.profile + " vs " + ftpAccount.profile
          );
        }
      }
    }
    return foundedAccount;
  }
}
