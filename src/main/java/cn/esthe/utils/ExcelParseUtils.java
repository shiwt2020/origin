package cn.esthe.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExcelParseUtils {
    public static void main(String[] args) {
        getData("C:\\Users\\Utopia\\Desktop\\excel\\aa.xlsx","C:\\Users\\Utopia\\Desktop\\excel\\积分卡处理sql.txt");
    }

    public static void getData(String filePath,String write) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

        List<String> data =new ArrayList<>();
        FileInputStream fileInput = null;
        FileOutputStream fileOutput = null;
        try {
            fileInput = new FileInputStream(filePath);
            fileOutput = new FileOutputStream(write,true);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInput);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            int rowNum = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                String cellValue = row.getCell(0).getRawValue();
                String rawValue = row.getCell(1).getRawValue();
                data.add(cellValue+"_"+rawValue);
            }
            StringBuilder sb=new StringBuilder();
            data.forEach(x->{
                String[] s = x.split("_");
                sb.append("update `sbc-marketing`.card_code set effective_end_time = '2022-12-31 23:59:59'");
                sb.append(" where card_no =");
                sb.append("'"+s[1]+"';");
                sb.append("\n");
            });
            fileOutput.write(new String(sb).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutput != null) {
                    fileOutput.close();
                }
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {

            }
        }
    }



}
