package toolmanage;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewExcel {
    
    private String excelPath = "D:\\data.xlsx";

    public static void main(String[] args) throws Exception{
        NewExcel excel = new NewExcel();
        if(excel.createExcelFile()) {
            System.out.println("data.xlsx is created successfully.");
        }
    }
    @SuppressWarnings("resource")
    public boolean createExcelFile() {
        boolean isCreateSuccess = false;
        Workbook workbook = null;
        try {
            // XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
            workbook = new XSSFWorkbook();//HSSFWorkbook();//WorkbookFactory.create(inputStream);
        }catch(Exception e) {
            System.out.println("It cause Error on CREATING excel workbook: ");
            e.printStackTrace();
        }
        if(workbook != null) {
            Sheet sheet = workbook.createSheet("testdata");
            Row row0 = sheet.createRow(0);
            for(int i = 0; i < 11; i++) {
                Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
                //CellStyle style = getStyle(workbook);
                //cell_1.setCellStyle(style);
                cell_1.setCellValue("HELLO" + i + "Column");
                sheet.autoSizeColumn(i);
            }
            for (int rowNum = 1; rowNum < 200; rowNum++) {
                Row row = sheet.createRow(rowNum);
                for(int i = 0; i < 11; i++) {
                    Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                    cell.setCellValue("cell" + String.valueOf(rowNum+1) + String.valueOf(i+1));
                }
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(excelPath);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
                isCreateSuccess = true;
            } catch (Exception e) {
                System.out.println("It cause Error on WRITTING excel workbook: ");
                e.printStackTrace();
            }
        }
        File sss = new File(excelPath);
        System.out.println(sss.getAbsolutePath());
        return isCreateSuccess;
    }
    @SuppressWarnings("unused")
    private CellStyle getStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short)14);
        headerFont.setColor(HSSFColor.RED.index);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);

        // 设置单元格边框及颜色
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setWrapText(true);
        return style;
    }
    public String getExcelPath() {
        return this.excelPath;
    }
    
    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }
}