package com.example.lab3.service;

import com.example.lab3.client.FtpClient;
import com.example.lab3.help.FtpTree;
import com.example.lab3.model.FtpFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FtpCatalogueTreeServiceTest {
  private final FtpCatalogueTreeService treeService = new FtpCatalogueTreeService();

  @Test
  void listDirectory() {
    FtpClient ftpClient = mock(FtpClient.class);
    when(ftpClient.listFiles(anyString())).then(new Answer<List<FtpFile>>() {
      private final FtpTree ftpTree = new FtpTree() {
        {
          add("/.", new FtpFile("a", true));
          add("/.", new FtpFile("b", true));
          add("/.", new FtpFile("c", true));
          add("/./a", new FtpFile("text.txt", false));
          add("/./b", new FtpFile("qwerty", true));
          add("/./b/qwerty", new FtpFile("asdfgh.txt", false));
        }
      };

      @Override
      public List<FtpFile> answer(InvocationOnMock invocationOnMock) {
        String argument = invocationOnMock.getArgument(0);
        return ftpTree.getAll(argument.substring(0, argument.length() - 1));
      }
    });
    List<String> expectedOutput = List.of(
      "[a]",
      "    text.txt",
      "[b]",
      "    [qwerty]",
      "        asdfgh.txt",
      "[c]"
    );


    System.out.println("Files from local FtpTree");
    List<String> directory = treeService.listDirectory(ftpClient);
    directory.forEach(System.out::println);
    System.out.println();

    assertLinesMatch(expectedOutput, directory);
  }
}