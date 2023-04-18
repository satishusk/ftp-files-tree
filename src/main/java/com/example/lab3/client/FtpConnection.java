package com.example.lab3.client;

import com.example.lab3.model.FtpAccount;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Value
@Slf4j
public class FtpConnection implements AutoCloseable {
  BufferedReader reader;

  public FtpConnection(FtpAccount ftpAccount) throws IOException {
    String hostname = ftpAccount.getHostname();
    int port = Integer.parseInt(ftpAccount.getPort());
    String username = ftpAccount.getUsername();
    String password = ftpAccount.getPassword();

    URLConnection connection = connect(hostname + ":" + port, username, password);
    InputStream inputStream = connection.getInputStream();
    InputStreamReader streamReader = new InputStreamReader(inputStream);
    reader = new BufferedReader(streamReader);
  }

  public FtpConnection(String uri, String username, String password) throws IOException {
    URLConnection connection = connect(uri, username, password);
    InputStream inputStream = connection.getInputStream();
    InputStreamReader streamReader = new InputStreamReader(inputStream);
    reader = new BufferedReader(streamReader);
  }

  private URLConnection connect(String uri, String username, String password) throws IOException {
    String address = String.format("ftp://%s:%s@%s", username, password, uri);
    URL url = new URL(address);
    return url.openConnection();
  }

  @Override
  public void close() throws Exception {
    reader.close();
  }
}