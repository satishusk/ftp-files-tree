package com.example.lab3.client;

import com.example.lab3.model.FtpAccount;
import com.example.lab3.model.FtpFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FtpClientTest {
  private final FtpClient ftpClient = new FtpClient(FtpAccount.COMMON);

  @ParameterizedTest
  @MethodSource("filesArgs")
  void listFiles(String uri, long expectedFilesCount) {
    List<FtpFile> ftpFiles = ftpClient.listFiles(uri);
    System.out.println("From " + uri);
    ftpFiles.forEach(System.out::println);
    System.out.println();

    assertNotNull(ftpFiles);
    assertEquals(expectedFilesCount, ftpFiles.size());
  }

  private static Stream<Arguments> filesArgs() {
    return Stream.of(
      Arguments.of("/", 6),
      Arguments.of("/TEST/", 5),
      Arguments.of("/.log/", 0),
      Arguments.of("/htdocs/qwerty/", 2)
    );
  }

}