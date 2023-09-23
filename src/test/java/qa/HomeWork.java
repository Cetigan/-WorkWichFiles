package qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import model.For2json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeWork {
    ClassLoader cls = FileparsingTest.class.getClassLoader();

    @Test
    void zipParsrTestHome()throws Exception{
        try (InputStream res    =   cls.getResourceAsStream("New folder.zip");
             ZipInputStream zipp = new ZipInputStream(res))  {
            ZipEntry a;
            while ((a = zipp.getNextEntry()) != null){

                if (a.getName().endsWith(".csv")){
                    CSVReader reader =new CSVReader( new InputStreamReader(zipp));
                    List<String[]> content=  reader.readAll();
                    assertThat(content.get(1)[0]).contains("cetigan19@rambler.ru");
                    System.out.println("");
                }
                else if (a.getName().endsWith(".xlxs")){
                    XLS content =new XLS(zipp);
                    assertThat(content.excel.getSheetAt(0).getRow(90).getCell(0).getStringCellValue().contains("Бежецк"));

                }
                else if (a.getName().endsWith(".pdf")){
                    PDF content = new PDF(zipp);
                    assertThat(content.numberOfPages).isEqualTo(1);


                }


            }


        }
    }
    @Test
    void JsonParseClass()throws Exception{

        Gson gson = new Gson();
        try (InputStream res    =   cls.getResourceAsStream("json2.json");
             InputStreamReader reader =new InputStreamReader( res  ))
        {
            For2json jsonObject= gson.fromJson(reader, For2json.class);
            assertThat(jsonObject.image.width).isEqualTo("800");
            assertThat(jsonObject.image.height).isEqualTo("600");
            assertThat(jsonObject.image.title).isEqualTo("View from 15th Floor");
            assertThat(jsonObject.image.visible).isTrue();
            assertThat(jsonObject.image.ids).contains(116,943,234,38793);


        }

    }
}
