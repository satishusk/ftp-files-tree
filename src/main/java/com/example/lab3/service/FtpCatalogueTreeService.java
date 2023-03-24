package com.example.lab3.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FtpCatalogueTreeService {
  public List<String> listDirectory(FTPClient ftpClient) throws IOException {
    var list = new ArrayList<String>();
    listDirectoryRecursive(ftpClient, "", ".", 0, list);
    return list;
  }

  public List<String> listDirectory(FTPClient ftpClient, String parentDir, String currentDir, int level) throws IOException {
    var list = new ArrayList<String>();
    listDirectoryRecursive(ftpClient, parentDir, currentDir, level, list);
    return list;
  }

  private void listDirectoryRecursive(
    FTPClient client, String parentDir, String currentDir, int level, List<String> list
  ) throws IOException {
    String dirToList = parentDir;
    if (!currentDir.equals("")) {
      dirToList += "/" + currentDir;
    }

    FTPFile[] subFiles = client.listFiles(dirToList);
    if (subFiles == null || subFiles.length == 0){
      return;
    }

    for (FTPFile aFile : subFiles) {
      String currentFileName = new String(aFile.getName().getBytes(Charset.forName("Cp1251")));
      if (currentFileName.equals(".") || currentFileName.equals("..")) {
        continue;
      }

      var repeat = "    ".repeat(Math.max(0, level));
      if (aFile.isDirectory()) {
        list.add(repeat + "[" + currentFileName + "]");
        listDirectoryRecursive(client, dirToList, currentFileName, level + 1, list);
      } else {
        list.add(repeat + currentFileName);
      }
    }
  }
}
