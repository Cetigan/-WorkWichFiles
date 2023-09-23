package qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import static org.assertj.core.api.Assertions.assertThat;
public class SelenideFileTest {
  /*static { (если нет href)
        Configuration.fileDownload = FileDownloadMode.PROXY;
    }*/
    @Test
    void selenidDownladeTest() throws Exception {
        Configuration.holdBrowserOpen = true;
        open("https://github.com/junit-team/junit5/blob/main/README.md");

        File downloaded = $$("[data-component= buttonContent]").find(text("Raw")).parent().download();

        try( InputStream is = new  FileInputStream(downloaded)){
          byte[]bytes = is.readAllBytes();
       String readtext= new String(bytes);
        assertThat(readtext).contains("This repository is the home of _JUnit 5_.");             }



    }
    @Test
    void selenideUploadeFile(){
        Configuration.holdBrowserOpen = true;
        open("https://fineuploader.com/demos.html");
        $("[type='file']").uploadFromClasspath("Image 1.jpg");
        $(".qq-upload-file-selector").shouldHave(text("Image 1.jpg"));
    }
}
