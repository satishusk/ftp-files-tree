package com.example.lab3.service;

import com.example.lab3.client.FtpClient;
import com.example.lab3.model.FtpFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class FtpCatalogueTreeService {
  public List<String> listDirectory(FtpClient ftpClient) {
    var list = new ArrayList<String>();
    listDirectoryRecursive(ftpClient, "/", ".", 0, list);
    return list;
  }

  private void listDirectoryRecursive(FtpClient client,
                                      String parentDir,
                                      String currentDir,
                                      int level,
                                      List<String> list) {
    String dirToList = parentDir;
    dirToList += currentDir + "/";

    List<FtpFile> subFiles = client.listFiles(dirToList);
    if (subFiles == null || subFiles.size() == 0){
      return;
    }

    for (FtpFile aFile : subFiles) {
      String currentFileName = aFile.getName();
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
