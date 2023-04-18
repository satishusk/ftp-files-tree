package com.example.lab3.help;

import com.example.lab3.model.FtpFile;

import java.util.List;

public class FtpTree {
  private final FtpTreeNode start = new FtpTreeNode("/.", new FtpFile(".", true));

  public void add(String dirPath, FtpFile ftpFile) {
    FtpTreeNode ftpTreeNode = getRecursive(dirPath);
    ftpTreeNode.addSubFile(ftpFile);
  }

  public FtpFile get(String path) {
    return getRecursive(path).getValue();
  }

  private FtpTreeNode getRecursive(String path) {
    if (path.equals(start.getPath())) {
      return start;
    }
    return getRecursive(start, path);
  }

  private FtpTreeNode getRecursive(FtpTreeNode parent, String path) {
    for (FtpTreeNode node : parent.getSubNodes()) {
      FtpFile file = node.getValue();
      if (path.equals(node.getPath())) {
        return node;
      } else if (file.isDirectory() && path.startsWith(node.getPath())) {
        return getRecursive(node, path);
      }
    }
    throw new IllegalArgumentException("Cannot find FtpTreeNode with path: " + path);
  }

  public List<FtpFile> getAll(String path) {
    FtpTreeNode ftpTreeNode = getRecursive(path);
    return ftpTreeNode.getSubNodes().stream()
      .map(FtpTreeNode::getValue)
      .toList();
  }
}
