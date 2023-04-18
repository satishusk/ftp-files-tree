package com.example.lab3.client;

import com.example.lab3.model.FtpAccount;
import com.example.lab3.model.FtpFile;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
@Slf4j
public class FtpClient {
  private final String hostname;
  private final Integer port;
  private final String username;
  private final String password;

  public FtpClient(FtpAccount ftpAccount) {
    this.hostname = ftpAccount.getHostname();
    this.port = Integer.parseInt(ftpAccount.getPort());
    this.username = ftpAccount.getUsername();
    this.password = ftpAccount.getPassword();
  }

  public FtpClient(String hostname, Integer port, String username, String password) {
    this.hostname = hostname;
    this.port = port;
    this.username = username;
    this.password = password;
  }

  public List<FtpFile> listFiles(String dir) {
    String uri = hostname + ":" + port + dir;

    try (FtpConnection ftpConnection = new FtpConnection(uri, username, password)) {
      BufferedReader reader = ftpConnection.getReader();
      return reader.lines()
        .map(s -> new FtpFile(getName(s), isDirectory(s)))
        .toList();
    } catch (Exception ex) {
      log.warn("Cannot create connection of {}", uri);
      throw new RuntimeException(ex);
    }
  }

  private String getName(String file) {
    String[] split = Arrays.stream(file.split(" "))
      .filter(s -> !s.equals(""))
      .toArray(String[]::new);

    String[] nameSplit = new String[split.length - 8];
    System.arraycopy(split, 8, nameSplit, 0, split.length - 8);

    String name = String.join(" ", nameSplit);
    return name.strip();
  }

  private boolean isDirectory(String file) {
    return file.startsWith("d");
  }
}
