package com.game.bmanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.game.smvc.core.util.FileUtil;
import com.game.smvc.util.ByteOutputStream;

public class OpUtil {
	
	/**
     * 获取文件MD5值
     *
     * @param byte[]
     * @return String
     * 
     * @author Crap
     */
	public static String encryptMD5File(byte[] bytes){
		try {
			return Coder.encryptHex(Coder.encryptMD5(bytes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
     * 获取文件MD5值
     *
     * @param in InputStream
     * @return String
     * 
     * @author Crap
     */
	public static String encryptMD5File(InputStream in){
		try {
			return Coder.encryptHex(Coder.encryptMD5(FileUtil.getBytes(in)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 生成Excel流
	 * @param String[] 格式{"列名=字段名"}
	 * @param List<Map<String, Object>>
	 * @return ByteOutputStream
	 * @throws IOException 
	 */
	public static ByteOutputStream exportExcel(String[] columns, List<Map<String, Object>> data) throws IOException {
		
		Workbook wb = new HSSFWorkbook();
		
        Sheet sheet = wb.createSheet("用户统计");
        Row row = sheet.createRow(0);
        row.setHeightInPoints(30);
        for (int i = 0; i < columns.length; i++) {
             createCell(wb, row, i, columns[i].split("=")[0]);
		}
        for (int i = 0; i < data.size(); i++) {
        	row = sheet.createRow(i+1);
        	Map<String, Object> map = data.get(i);
        	for (int j = 0; j < columns.length; j++) {
        		createCell(wb, row, j, String.valueOf(map.get(columns[j].split("=")[1])));
			}
		}
        // Write the output to a ByteOutputStream
        ByteOutputStream byteOut = new ByteOutputStream();
        
        wb.write(byteOut);
        byteOut.close();
		return byteOut;
	}
	 /**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     */
    private static void createCell(Workbook wb, Row row, int column, String title) {
        Cell cell = row.createCell(column);
        cell.setCellValue(title);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        cell.setCellStyle(cellStyle);
    }
    
    
}
