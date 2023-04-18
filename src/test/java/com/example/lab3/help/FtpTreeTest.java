package com.example.lab3.help;

import com.example.lab3.model.FtpFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FtpTreeTest {
  private final FtpTree ftpTree = new FtpTree();

  @Test
  void shouldAddFiles() {
    ftpTree.add("/.", new FtpFile("a", true));
    ftpTree.add("/.", new FtpFile("b", true));
    ftpTree.add("/.", new FtpFile("c", true));
    ftpTree.add("/./a", new FtpFile("text.txt", false));
    ftpTree.add("/./b", new FtpFile("qwerty", true));
    ftpTree.add("/./b/qwerty", new FtpFile("asdfgh.txt", false));

    FtpFile ftpFileA = ftpTree.get("/./a");
    FtpFile ftpFileB = ftpTree.get("/./b");
    FtpFile ftpFileText = ftpTree.get("/./a/text.txt");

    assertTrue(ftpFileA.isDirectory());
    assertTrue(ftpFileB.isDirectory());
    assertFalse(ftpFileText.isDirectory());
  }
}