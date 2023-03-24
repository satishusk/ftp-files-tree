package com.example.lab3.controller;

import com.example.lab3.client.FtpClientWrapper;
import com.example.lab3.service.FtpCatalogueTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FtpController {
  private final FtpClientWrapper ftpClientWrapper;
  private final FtpCatalogueTreeService treeService;

  @GetMapping("/tree")
  public String filesTreePage() {
    return "tree";
  }

  @PostMapping("/tree")
  public String filesTree(
    @RequestParam("hostname") String hostname, @RequestParam("port") String port,
    @RequestParam("username") String username, @RequestParam("password") String password,
    Model model
  ) throws IOException {
    ftpClientWrapper.connect(hostname, Integer.parseInt(port), username, password);

    Map<String, Object> attributes = Map.of(
      "hostname", hostname,
      "port", port,
      "username", username,
      "directory", treeService.listDirectory(ftpClientWrapper.getFtpClient())
    );
    model.addAllAttributes(attributes);

    return "tree";
  }
}
