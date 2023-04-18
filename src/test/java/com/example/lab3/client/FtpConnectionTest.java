package com.example.lab3.client;

import com.example.lab3.model.FtpAccount;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FtpConnectionTest {

  @Test
  void shouldConnect() {
    try(FtpConnection ftpConnection = new FtpConnection(FtpAccount.COMMON)) {
      BufferedReader reader = ftpConnection.getReader();

      assertNotNull(reader);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Test
  void shouldReturnRootFiles() {
    try(FtpConnection ftpConnection = new FtpConnection(FtpAccount.COMMON)) {
      BufferedReader reader = ftpConnection.getReader();
      long expectedCount = 6;

      System.out.println("Root files from remote ftp server");
      List<String> files = reader.lines().toList();
      files.forEach(System.out::println);
      System.out.println();
      assertEquals(expectedCount, files.size());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}