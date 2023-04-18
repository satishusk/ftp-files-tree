package com.example.lab3.model;

import lombok.Value;

@Value
public class FtpFile {
  String name;
  boolean isDirectory;
}
