package com.example.lab3.client;

import lombok.Data;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.PrintWriter;

@Data
public class FtpClientWrapper {
  private FTPClient ftpClient;

  public void connect(String hostname, String username, String password) throws IOException {
    connect(hostname, 21, username, password);
  }
  public void connect(String hostname, Integer port, String username, String password) throws IOException {
    ftpClient = new FTPClient();
    ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    ftpClient.setControlEncoding("Cp1251");
    ftpClient.connect(hostname, port);
    ftpClient.login(username, password);
  }

  public void disconnect() throws IOException {
    ftpClient.disconnect();
  }

  public FTPClient getFtpClient() {
    return ftpClient;
  }
}
