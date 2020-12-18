package com.ywkj.ktyunxiao.common.utils;

import com.ywkj.ktyunxiao.common.annotation.Excel;
import com.ywkj.ktyunxiao.common.enums.FileType;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.pojo.ExcelPojo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

/**
 * excel工具类
 * @author LiWenHao
 * @date 2018/4/18 13:40
 */
public class ExcelUtil {

    /**
     * 创建工作簿
     * @param stream    输入流
     * @param fileName  文件名
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream stream, String fileName) throws IOException {
        //文件后缀名
        String suffix= fileName.substring(fileName.lastIndexOf(".")+1);
        Workbook workbook = null;
        if(FileType.XLS.getName().equals(suffix)){
            workbook = new HSSFWorkbook(stream);
        }else if (FileType.XLSX.getName().equals(suffix)){
            workbook = new XSSFWorkbook(stream);
        }else{
            throw new CheckException("解析的文件格式有有误！");
        }
        return workbook;
    }

    /**
     * cell转换成对象
     * @param cell
     * @return
     */
	public static Object getCellValue(Cell cell){
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");
        if(cell == null){
            value = "";
        }else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    value = df.format(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value = "";
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value;
    }

    /**
     * 获取Excel数据
     * @param in        输入流
     * @param fileName  文件名
     * @return
     * @throws Exception
     */
    public static List<List<String>> getExcelData(InputStream in, String fileName) throws IOException {
        List<List<String>> lists = null;
        Workbook workbook = getWorkbook(in,fileName);
        if(workbook == null){
            throw new CheckException("创建Excel工作薄为空！");
        }
        lists = new ArrayList<>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        //遍历sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if(sheet == null){ continue; }
            //遍历所有行
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                if(row == null){continue;}
                //遍历所有列
                List<String> objectList = new ArrayList<>();
                for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                    cell = row.getCell(k);
                    if (StringUtil.isNotEmpty(cell.toString())) {
                        objectList.add(StringUtil.replaceSpace(getCellValue(cell).toString()));
                    }
                }
                lists.add(objectList);
            }
        }
        workbook.close();
        return lists;
    }

    /**
     * 导出excel
     * @param data
     * @param pojoClass
     * @return
     */
    public static Workbook exportExcel(List<?> data, Class<?> pojoClass){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = setCellStyle(workbook);
        XSSFSheet sheet = workbook.createSheet();
        List<ExcelPojo> excelPojoList = annotationData(pojoClass);
        setTitle(workbook,sheet, excelPojoList);
        for (int i = 0; i < data.size(); i++) {
            XSSFRow nextRow = sheet.createRow(i + 1);
            //获取成员变量
            Field[] fields = data.get(i).getClass().getDeclaredFields();
            for (int j = 0; j < excelPojoList.size(); j++) {
                //创建单元格
                XSSFCell contentCell = nextRow.createCell(j);
                for (Field field : fields) {
                    if (excelPojoList.get(j).getKey().equals(field.getName())) {
                        try {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),data.get(i).getClass());
                            Method method = pd.getReadMethod();
                            String invoke = (String)method.invoke(data.get(i));
                            contentCell.setCellValue(invoke);
                            contentCell.setCellStyle(cellStyle);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return workbook;
    }

    /**
     * 设置标题
     * @param sheet
     * @param excelPojoList
     */
    public static void setTitle(XSSFWorkbook workbook,XSSFSheet sheet,List<ExcelPojo> excelPojoList){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        XSSFRow row = sheet.createRow(0);
        XSSFCell tileCell;
        for (int i = 0; i < excelPojoList.size(); i++) {
            tileCell = row.createCell(i);
            tileCell.setCellValue(excelPojoList.get(i).getName());
            tileCell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i,excelPojoList.get(i).getWidth());
        }
    }

    /**
     * 设置单元格样式
     * @param workbook
     * @return
     */
    private static XSSFCellStyle setCellStyle(XSSFWorkbook workbook){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        return cellStyle;
    }

    /**
     * 获取注解数据
     * @param pojoClass
     * @return
     */
    private static List<ExcelPojo> annotationData (Class pojoClass){
        List<ExcelPojo> excelPojoList = new ArrayList<>();
        Field[] declaredFields = pojoClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(Excel.class)){
                Excel annotation = declaredField.getAnnotation(Excel.class);
                excelPojoList.add(new ExcelPojo(annotation.index(), annotation.name(), declaredField.getName(), annotation.width()));
            }
        }
        //排序
        excelPojoList.sort((o1, o2) ->  o1.getIndex() - o2.getIndex());
        return excelPojoList;
    }
}
