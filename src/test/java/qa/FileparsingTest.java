package qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import model.Glossary;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FileparsingTest {




    @Test
    void pdfParse() throws Exception {

        open("https://junit.org/junit5/docs/current/user-guide");
        sleep(20000);
        File downloadedPdf= $$(".sectionbody").first().$("a[href='junit-user-guide-5.10.0.pdf']").download();
       sleep(30000);
        PDF content = new PDF(downloadedPdf);
        assertThat((content.author).contains("Sam Branner"));
    }


            ClassLoader cl = FileparsingTest.class.getClassLoader();
    @Test
    void xlsparse() throws Exception{
       try (InputStream res    =   cl.getResourceAsStream("spisok.xlsx")){
           XLS content =new XLS( res);

       }  }
    @Test
            void downloadexlx()throws Exception{
            Configuration.holdBrowserOpen = true;
            open("https://hthwater.ru/spisokgorodovrossii");
             sleep(20000);
            File downloadedxls= $("a[href='https://hthwater.ru/download/spisok.xlsx']").download();
            XLS c = new XLS(downloadedxls);
            assertThat(c.excel.getSheetAt(0).getRow(90).getCell(0).getStringCellValue()).contains("Бежецк");
        System.out.println("");
        }


@Test
    void csvParsrTest()throws Exception{
    try (InputStream res    =   cl.getResourceAsStream("csv1.csv");
         CSVReader reader =new CSVReader( new InputStreamReader(res))  ) {
        List<String[]> content=  reader.readAll();
        assertThat(content.get(1)[1]).contains("file");



    }
    }

    @Test
    void zipParsrTest()throws Exception{
        try (InputStream res    =   cl.getResourceAsStream("New Text Document.zip");
             ZipInputStream zipp = new ZipInputStream(res))  {
            ZipEntry a;
            while ((a = zipp.getNextEntry()) != null){
                  assertThat(a.getName().equals("New Text Document.txt"));
            }


        }
    }

@Test
    void JsonParse()throws Exception{
Gson gson = new Gson();
    try (InputStream res    =   cl.getResourceAsStream("json.json");
         InputStreamReader reader =new InputStreamReader( res  )) {
        JsonObject  jj= gson.fromJson(reader, JsonObject.class);
        assertThat(jj.get("title").getAsString()).isEqualTo("example glossary");
        assertThat(jj.get("GlossDiv").getAsJsonObject().get("title").getAsString()).isEqualTo("S");
        assertThat(jj.get("GlossDiv").getAsJsonObject().get("flag").getAsBoolean()).isTrue();







    }}
    @Test
    void JsonParseClass()throws Exception{

        Gson gson = new Gson();
        try (InputStream res    =   cl.getResourceAsStream("json.json");
             InputStreamReader reader =new InputStreamReader( res  ))
        {
           Glossary jsonObject= gson.fromJson(reader, Glossary.class);
           assertThat(jsonObject.glossDiv.title).isEqualTo("S");
           assertThat(jsonObject.glossDiv.flag).isTrue();
        }

}}





