package com.example.lab3.help;

import com.example.lab3.model.FtpFile;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class FtpTreeNode {
  String path;
  FtpFile value;
  List<FtpTreeNode> subNodes;

  public FtpTreeNode(String path, FtpFile value, List<FtpFile> subFiles) {
    this.path = path;
    this.value = value;
    if (value.isDirectory()) {
      this.subNodes = subFiles.stream().map(ftpFile -> new FtpTreeNode(path + "/" + ftpFile.getName(), ftpFile)).toList();
    } else {
      throw new IllegalArgumentException("Ftp file is not directory, but given sub files!");
    }
  }

  public FtpTreeNode(String path, FtpFile value) {
    this.path = path;
    this.value = value;
    this.subNodes = new ArrayList<>();
  }

  public void addSubFile(FtpFile ftpFile) {
    if (value.isDirectory()) {
      subNodes.add(new FtpTreeNode(path + "/" + ftpFile.getName(), ftpFile));
    } else {
      throw new IllegalArgumentException("Cannot add sub files to file");
    }
  }
}
