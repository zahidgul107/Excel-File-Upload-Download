package com.csv.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.csv.entity.CsvEntry;
import com.csv.entity.File;

public class ExcelHelper {

	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}

	}
	

	public static List<CsvEntry> convertExcelToListOfProduct(InputStream is, File f) {
		List<CsvEntry> list = new ArrayList<>();

		try {

			XSSFWorkbook workbook = new XSSFWorkbook(is);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				System.out.println("Sheet name: " + sheetName);

				int rowNumber = 0;
				Iterator<Row> iterator = sheet.iterator();

				while (iterator.hasNext()) {
					Row row = iterator.next();

					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cells = row.iterator();

					int cid = 0;

					CsvEntry csvEntry = new CsvEntry();

					while (cells.hasNext()) {
						Cell cell = cells.next();

						switch (cid) {
						case 0:
							csvEntry.setFinalColumn(cell.getStringCellValue());
							break;
						default:
							break;
						}
						cid++;
					}
					csvEntry.setFile(f);
					list.add(csvEntry);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public static String[] HEADERS = { "finalColumn" };

	public static String SHEET_NAME = "csv_upload";

	public static ByteArrayInputStream dataToExcel(List<CsvEntry> list) {
		try {

			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Sheet sheet = workbook.createSheet(SHEET_NAME);

			Row row = sheet.createRow(0);

			for (int i = 0; i < HEADERS.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(HEADERS[i]);
			}

			int rowIndex = 1;

			for (CsvEntry c : list) {
				Row dataRow = sheet.createRow(rowIndex);
				rowIndex++;

				dataRow.createCell(0).setCellValue(c.getFinalColumn());
			}

			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
