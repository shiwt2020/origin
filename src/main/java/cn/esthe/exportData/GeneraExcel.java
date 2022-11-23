package cn.esthe.exportData;

import com.alibaba.druid.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.*;
import java.math.BigDecimal;

public class GeneraExcel {
    public static void main(String[] args) {

        BigDecimal costPrice=new BigDecimal("123");
        int i = costPrice.compareTo(new BigDecimal("100"));
        Boolean b=1>=0;
        System.out.println(b);

    }

    public static void errorExcel(String ext) {
        Workbook workbook = null;
        //文件名称
        String errorFileName = "D:\\excel\\xioming.".concat(ext);
        File fileName = new File(errorFileName);
        FileOutputStream outputStream = null;
        try {
            if (ext.equals("xls")) {
                workbook = new HSSFWorkbook();
            }
            if (ext.equals("xlsx")) {
                workbook = new SXSSFWorkbook();
            }
            Sheet sheet = workbook.createSheet();
            // 设置表头
            Row row1 = sheet.createRow(0);
            Cell cell = row1.createCell(0);
            cell.setCellValue("商品sku编码（必填）");
            setErrorCell(workbook,"日照香炉生字眼",cell);

            Cell cell1 = row1.createCell(1);
            cell1.setCellValue("商品名（可不填）");
            setErrorCell(workbook,"sdadfwefsdf",cell1);

            row1.createCell(2).setCellValue("成本价（可不填）");
            row1.createCell(3).setCellValue("售价（可不填）");
            row1.createCell(4).setCellValue("划线价（可不填）");
            row1.createCell(5).setCellValue("自动调整价格（必填）");
            row1.createCell(6).setCellValue("sdfas（必填）");
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);

        } catch (FileNotFoundException e) {

        } catch (IOException e){

        }finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {

            }try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {

            }

        }
    }

    private static void setErrorCell(Workbook workbook, String comment, Cell skuCell) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        skuCell.setCellStyle(style);

        Comment t_comment = skuCell.getCellComment();

        //当前单元格索引起始值
        int beginRowIndex = skuCell.getRowIndex()+1;
        int beginCelIndex = skuCell.getColumnIndex()+1 ;

        //范围性单元格终止值
        int endRowIndex = beginRowIndex + 2;
        int endCelIndex = beginCelIndex + 2;

        if (t_comment == null) {
            Drawing patriarch = workbook.getSheetAt(0).createDrawingPatriarch();
            ClientAnchor anchor = patriarch.createAnchor(0, 0, 0, 0,
                    beginCelIndex, beginRowIndex, endCelIndex, endRowIndex);
            t_comment = patriarch.createCellComment(anchor);
        }

        RichTextString richTextString = t_comment.getString();
        String oldComment = (richTextString == null || StringUtils.isEmpty(richTextString.getString())) ?
                comment.concat("\n") :
                richTextString.getString().concat("\n").concat(comment);
        //2003的批注
        if (richTextString instanceof HSSFRichTextString) {
            t_comment.setString(new HSSFRichTextString(comment));
        } else {
            t_comment.setString(new XSSFRichTextString(comment));
        }
        skuCell.setCellComment(t_comment);
    }

}
