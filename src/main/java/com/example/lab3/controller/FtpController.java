package com.example.lab3.controller;

import com.example.lab3.client.FtpClient;
import com.example.lab3.model.FtpAccount;
import com.example.lab3.service.FtpCatalogueTreeService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class FtpController {
  private final FtpCatalogueTreeService treeService;
  private final Map<String, Object> ftpClientAttributes;

  public FtpController(FtpCatalogueTreeService treeService, ApplicationContext context) {
    this.treeService = treeService;
    this.ftpClientAttributes = FtpAccount.byProfile(context).toMap();
  }

  @GetMapping("/tree")
  public String filesTreePage(Model model) {
    if (ftpClientAttributes.size() > 0) {
      model.addAllAttributes(ftpClientAttributes);
    }
    return "tree";
  }

  @PostMapping("/tree")
  public String filesTree(@RequestParam("hostname") String hostname,
                          @RequestParam("port") String port,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) throws IOException {
    FtpClient ftpClient = new FtpClient(hostname, Integer.parseInt(port), username, password);
    Map<String, Object> attributes = Map.of(
      "hostname", hostname,
      "port", port,
      "username", username,
      "password", password,
      "directory", treeService.listDirectory(ftpClient)
    );
    model.addAllAttributes(attributes);

    return "tree";
  }

  @ExceptionHandler(IOException.class)
  public String handle(Model model, IOException exception) {
    exception.printStackTrace();
    model.addAttribute("error", exception.getMessage());
    return filesTreePage(model);
  }
}
