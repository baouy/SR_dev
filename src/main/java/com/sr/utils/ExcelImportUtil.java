package com.sr.utils;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by KingZS on 2018/5/28
 */
public class ExcelImportUtil {

	private final static String excel2003L =".xls";    //2003- 版本的excel
	private final static String excel2007U =".xlsx";   //2007+ 版本的excel


	public  List<List<Object>> getBankListByExcel(InputStream in,String fileName,int column) throws Exception{
		List<List<Object>> list = null;

		//创建Excel工作薄
		Workbook work = this.getWorkbook(in,fileName);
		if(null == work){
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		list = new ArrayList<List<Object>>();
		//遍历Excel中所有的sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if(sheet==null){continue;}

			//遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
				row = sheet.getRow(j);
				if(row==null||j==0){continue;}

				//遍历所有的列(暂时13列)
				List<Object> li = new ArrayList<Object>();
				for (int y = 0; y < column; y++) {
					cell = row.getCell(y);
					li.add(getCellValue(cell));
				}
				list.add(li);
			}
		}

		if (in != null){
			in.close();
		}

		return list;
	}

	/**
	 * 描述：根据文件后缀，自适应上传文件的版本
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if(excel2003L.equals(fileType)){
			wb = new HSSFWorkbook(inStr);  //2003-
		}else if(excel2007U.equals(fileType)){
			wb = new XSSFWorkbook(inStr);  //2007+
		}else{
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 描述：对表格中数值进行格式化
	 * @param cell
	 * @return
	 */
	public static Object getCellValue(Cell cell){
		Object value = null;
		DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

		if (cell == null){
			return "";
		}

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if("General".equals(cell.getCellStyle().getDataFormatString())){
					value = df.format(cell.getNumericCellValue());
				}else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
					value = sdf.format(cell.getDateCellValue());
				}else{
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			default:
				break;
		}
		return value;
	}

	/**
	 * @description 获取HSSFSheet 中的图片内容
	 * @author JiaoBai
	 * @param hssfSheet
	 * @return
	 */
	public static Map<String, List<HSSFPictureData>> getHSSFSheetAllPictureData(HSSFSheet hssfSheet){

		Map<String, List<HSSFPictureData>> dataMap = null;

		//处理sheet中的图形
		HSSFPatriarch hssfPatriarch = hssfSheet.getDrawingPatriarch();
		//获取所有的形状图
		List<HSSFShape> shapes = hssfPatriarch.getChildren();

		if(shapes.size()>0){

			dataMap = new HashMap<>();

			List<HSSFPictureData> pictureDataList = null;

			for(HSSFShape sp : shapes){
				if(sp instanceof HSSFPicture){
					//转换
					HSSFPicture picture = (HSSFPicture)sp;
					//获取图片数据
					HSSFPictureData pictureData = picture.getPictureData();
					//图形定位
					if(picture.getAnchor() instanceof HSSFClientAnchor){

						HSSFClientAnchor anchor = (HSSFClientAnchor)picture.getAnchor();
						//获取图片所在行作为key值,插入图片时，默认图片只占一行的单个格子，不能超出格子边界
						String rowNum = String.valueOf(anchor.getRow1());
						if(dataMap.get(rowNum)!=null){
							pictureDataList = dataMap.get(rowNum);
						}else{
							pictureDataList = new ArrayList<>();
						}
						pictureDataList.add(pictureData);
						dataMap.put(rowNum, pictureDataList);
						// 测试部分
						/*int row2 = anchor.getRow2();
						short col1 = anchor.getCol1();
						short col2 = anchor.getCol2();
						int dx1 = anchor.getDx1();
						int dx2 = anchor.getDx2();
						int dy1 = anchor.getDy1();
						int dy2 = anchor.getDy2();

						System.out.println("row1: "+rowNum+" , row2: "+row2+" , col1: "+col1+" , col2: "+col2);
						System.out.println("dx1: "+dx1+" , dx2: "+dx2+" , dy1: "+dy1+" , dy2: "+dy2);*/
					}
				}
			}
		}
		/* 以下为 测试查看
		System.out.println("********图片数量明细 START********");
		int t=0;
		if(dataMap!=null){
			t=dataMap.keySet().size();
		}
		if(t>0){
			for(String key : dataMap.keySet()){
				System.out.println("第 "+key+" 行， 有图片： "+ dataMap.get(key).size() +" 张");
			}
		}else{
			System.out.println("Excel表中没有图片!");
		}
		System.out.println("********图片数量明细 END ********");*/

		return dataMap;
	}


	/**
	 * 获取XSSFSheet(Excel2007)图片内容
	 * @author JiaoBai
	 * @param sheetNum 当前sheet编号
	 * @param xssfSheet
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
	public static Map<String, PictureData> getXSSFSheetAllPictureData(int sheetNum,XSSFSheet xssfSheet) {
		Map<String, PictureData> sheetIndexPicMap = new HashMap<>();
		List<POIXMLDocumentPart> rls = xssfSheet.getRelations();
		for (POIXMLDocumentPart dr : rls) {
			if (dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture pic = (XSSFPicture) shape;
					XSSFClientAnchor anchor = pic.getPreferredSize();
					CTMarker ctMarker = anchor.getFrom();
					String picIndex = sheetNum + "_"+ ctMarker.getRow() + "_" + ctMarker.getCol();
					sheetIndexPicMap.put(picIndex, pic.getPictureData());
					// System.out.println("XSSFSheet*********图片位置信息***************"+picIndex);
				}
			}
		}
		return sheetIndexPicMap;
	}
	/**
	 * 获取HSSFWorkbook(Excel2003) 所有图片信息
	 * @author JiaoBai
	 * @param sheet 当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（all_1_1）String，value:图片流PictureData
	 */
	public static Map<String, PictureData> getHSSFWorkbookAllPictrues(HSSFSheet sheet, HSSFWorkbook workbook) {
		Map<String, PictureData> sheetIndexPicMap = new HashMap<>();
		List<HSSFPictureData> pictures = workbook.getAllPictures();
		if (pictures.size() != 0) {
			for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int pictureIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pictures.get(pictureIndex);
					String picIndex = "all_".concat(String.valueOf(anchor.getRow1()).concat("_").concat(String.valueOf(anchor.getCol1())));
					sheetIndexPicMap.put(picIndex, picData);
				}
			}
			return sheetIndexPicMap;
		} else {
			return null;
		}
	}
}
