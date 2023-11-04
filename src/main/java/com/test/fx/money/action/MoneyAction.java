package com.test.fx.money.action;

import com.test.fx.money.model.Enitry;
import com.test.fx.money.model.MoneyModel;
import com.test.fx.util.AxiosVo;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test/fx/money")
public class MoneyAction {
    @RequestMapping("importExcel")
    @ResponseBody
    public AxiosVo importExcel(@RequestParam("file") MultipartFile files,@RequestParam("name") String name){
        AxiosVo axiosVo = new AxiosVo();
        axiosVo.setCode(20000);
        Workbook readwb = null;
        try {
            InputStream instream = files.getInputStream();//构建Workbook对象, 只读Workbook对象
            readwb = this.createWorkbook(instream);
            Sheet sheet = readwb.getSheetAt(0);
            List<MoneyModel> moneyModels = this.listModelByExcel(sheet);
            axiosVo.setMsg(name);
            axiosVo.setSuccess(true);
            axiosVo.setObj(moneyModels);
        } catch (Exception e) {
            System.out.println(e);
            axiosVo.setSuccess(false);
            axiosVo.setMsg("系统未能处理您的请求,请联系管理员!");
        }
        return axiosVo ;
    }

    @RequestMapping("handleEdit")
    @ResponseBody
    public AxiosVo handleEdit(@RequestBody Enitry enitry){
        AxiosVo axiosVo = this.hEdit(enitry);
        return axiosVo ;
    }

    @RequestMapping("outExcel")
    public void outExcel(@RequestBody Enitry enitry, HttpServletResponse response){
        // 1-声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("aaa");

        // 创建单元格样式
        CellStyle style= workbook.createCellStyle();
        // 设置为文本格式，防止身份证号变成科学计数法
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));
        //对单独某一列进行样式赋值，第一个参数为列数，第二个参数为样式
        for (int i = 0; i<9;i++){
            if(i == 5){
                style.setDataFormat(format.getFormat("0.00"));
                sheet.setColumnWidth(i,100*50);
            } else if (i == 6 || i == 7 || i == 8) {
                sheet.setColumnWidth(i,100*80);
            } else {
                style.setDataFormat(format.getFormat("@"));
                sheet.setColumnWidth(i,100*50);
            }
        }
        sheet.setDefaultColumnStyle(5, style);


        // 2-数据准备
        String title = "注意：\n" + "1、【金额是否含税】以导入时页面左上角设置为准    \n" + "2、【是否清单票】以导入时页面左上角设置为准";
        List<String> headerList1 = new ArrayList<>();
        List<String> headerList2 = new ArrayList<>();
        headerList1.add("必填\n" + "不超出92字符\n");
        headerList1.add("不超出40字符");
        headerList1.add("不超出20字符");
        headerList1.add("数量、单价、金额：任选其中两项，或者仅填金额或者三项均填写；\n" + "导入红字信息表清单时数量和金额自动处理为负数\n" + "单价/数量：不得超过10位小数\n" +  "金额：不得超过2位小数");
        headerList1.add("未填写，优先从已维护的商品信息中匹配\n" + "如果未维护该商品，通过大数据智能匹配获取");
        headerList1.add("未填写，优先从已维护的商品信息中匹配\n" + "如果未维护该商品，通过大数据智能匹配获取");
        headerList1.add("非必填，享受优惠政策时填写\n" + "\n" + "比如：免税、不征税");
        headerList2.add("商品名称*");
        headerList2.add("规格型号");
        headerList2.add("单位");
        headerList2.add("数量");
        headerList2.add("单价");
        headerList2.add("金额");
        headerList2.add("税率");
        headerList2.add("税收分类编码");
        headerList2.add("优惠政策名称");





        // 3-标题准备
        int row = 0;
        // 1-获取行对象
        HSSFRow hssfRow = sheet.getRow(row);
        if (hssfRow == null) {
            hssfRow = sheet.createRow(row);
        }
        hssfRow.setHeight((short) (14*100));

        // 2-创建单元格对象
        HSSFCell cell = hssfRow.createCell(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setFontName("宋体");
        CellStyle styleTite= workbook.createCellStyle();
        styleTite.setFont(font);//增加字体样式
        styleTite.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);//增加垂直居中样式
        styleTite.setWrapText(true);


        // 3-单元格文本数据填充
        cell.setCellValue(title);
        cell.setCellStyle(styleTite);
        CellRangeAddress region = new CellRangeAddress(0 , 0, 0, 9 - 1);// 合并标题单元格
        sheet.addMergedRegion(region);




        // 4-表头1准备
        row ++;
        HSSFRow hssfRow1 = sheet.getRow(row);
        if (hssfRow1 == null) {
            hssfRow1 = sheet.createRow(row);
        }
        hssfRow1.setHeight((short) (21*100));
        for (int i = 0; i < 7; i++) {
            if(i == 3){
                // 2-创建单元格对象
                HSSFCell cell1 = hssfRow1.createCell(i);
                // 3-单元格文本数据填充
                cell1.setCellValue(headerList1.get(i));
                cell1.setCellStyle(styleTite);
                CellRangeAddress region2 = new CellRangeAddress(1 , 1, 3 ,5);// 合并标题单元格
                sheet.addMergedRegion(region2);
            } else if (i == 4 || i==5 || i == 6) {
                // 2-创建单元格对象
                HSSFCell cell1 = hssfRow1.createCell(i+2);
                cell1.setCellStyle(styleTite);
                // 3-单元格文本数据填充
                cell1.setCellValue(headerList1.get(i));
            } else {
                // 2-创建单元格对象
                HSSFCell cell1 = hssfRow1.createCell(i);
                cell1.setCellStyle(styleTite);
                // 3-单元格文本数据填充
                cell1.setCellValue(headerList1.get(i));
            }

        }
        // 5-表头2准备
        row ++;
        HSSFRow hssfRow2 = sheet.getRow(row);
        if (hssfRow2 == null) {
            hssfRow2 = sheet.createRow(row);
        }
        hssfRow2.setHeight((short) (7*100));
        for (int i = 0; i < 9; i++) {
            if(i ==1 || i==4 || i ==5 || i == 6){
                // 1-获取行对象

                // 2-创建单元格对象
                HSSFCell cell2 = hssfRow2.createCell(i);
                // 3-单元格文本数据填充
                cell2.setCellValue(headerList2.get(i));
                cell2.setCellStyle(styleTite);
            }else {
                // 2-创建单元格对象
                HSSFCell cell2 = hssfRow2.createCell(i);
                // 3-单元格文本数据填充
                cell2.setCellValue(headerList2.get(i));
                cell2.setCellStyle(styleTite);
            }
        }


        // 6-表数据写入
        List<MoneyModel> modelList = new ArrayList<>();
        modelList = enitry.getMoneyList();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = 0; i < modelList.size(); i++) {
            MoneyModel moneyModel = modelList.get(i);
            if(moneyModel.getRownum() != null){
                if(moneyModel.getData16() != null){
                    for (int j = 0; j < 9; j++) {
                        // 1-获取行对象
                        HSSFRow row1 = sheet.getRow(i+3);
                        if (row1 == null) {
                            row1 = sheet.createRow(i+3);
                        }
                        // 2-创建单元格对象
                        HSSFCell cell1 = row1.createCell(j);
                        cell1.setCellStyle(cellStyle);
                        // 3-单元格文本数据填充
                        if(j == 0){
                            cell1.setCellValue(moneyModel.getData5());
                        }else if (j == 1){
                            cell1.setCellValue(moneyModel.getData6());
                        }else if (j == 2){
                            cell1.setCellValue(moneyModel.getData7());
                        }else if (j == 3){
                            if(moneyModel.getData9() != null){
                                cell1.setCellValue(moneyModel.getData9().toString());
                            }
                        }else if (j == 4){
//                        if(moneyModel.getData10() != null){
//                            cell1.setCellValue(moneyModel.getData10().toString());
//                        }
                        }else if (j == 5){
                            if(moneyModel.getData11() != null){
                                cell1.setCellValue(moneyModel.getData11().toString());
                            }
                        }else if (j == 6){
                            if(moneyModel.getData14() != null){
                                cell1.setCellValue(moneyModel.getData14().toString());
                            }
                        }else if (j == 7){
                            if(moneyModel.getData17() != null){
                                cell1.setCellValue(moneyModel.getData17().toString());
                            }
                        }else {
                            cell1.setCellValue("");
                        }

                    }
                }else {
                    for (int j = 0; j < 9; j++) {
                        // 1-获取行对象
                        HSSFRow row1 = sheet.getRow(i+3);
                        if (row1 == null) {
                            row1 = sheet.createRow(i+3);
                        }
                        // 2-创建单元格对象
                        HSSFCell cell1 = row1.createCell(j);
                        cell1.setCellStyle(cellStyle);
                        // 3-单元格文本数据填充
                        if(j == 0){
                            cell1.setCellValue(moneyModel.getData5());
                        }else if (j == 1){
                            cell1.setCellValue(moneyModel.getData6());
                        }else if (j == 2){
                            cell1.setCellValue(moneyModel.getData7());
                        }else if (j == 3){
                            if(moneyModel.getData9() != null){
                                cell1.setCellValue(moneyModel.getData9().toString());
                            }
                        }else if (j == 4){
                            if(moneyModel.getData10() != null){
                                cell1.setCellValue(moneyModel.getData10().toString());
                            }
                        }else if (j == 5){
//                            if(moneyModel.getData11() != null){
//                                cell1.setCellValue(moneyModel.getData11().toString());
//                            }
                        }else if (j == 6){
                            if(moneyModel.getData14() != null){
                                cell1.setCellValue(moneyModel.getData14().toString());
                            }
                        }else if (j == 7){
                            if(moneyModel.getData17() != null){
                                cell1.setCellValue(moneyModel.getData17().toString());
                            }
                        }else {
                            cell1.setCellValue("");
                        }

                    }
                }
            }
        }
        try (ServletOutputStream stream = response.getOutputStream()) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + "filename");
            workbook.write(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    private AxiosVo hEdit(Enitry enitry){
        AxiosVo axiosVo = new AxiosVo();
        List<MoneyModel> tableData = enitry.getMoneyList();
        List<MoneyModel> editTableData = new ArrayList<>();
        Map<Integer,MoneyModel> editTableMap = new HashMap<>();

        BigDecimal editTax = enitry.getTax();
        BigDecimal editTaxOfMoney = enitry.getTaxOfMoney();
        BigDecimal tax = tableData.get(tableData.size()-1).getData15();
        BigDecimal taxOfMoney = tableData.get(tableData.size()-1).getData11();
        tableData.remove(tableData.size() - 1);


        BigDecimal v = editTax.subtract(tax);
        BigDecimal n = v.abs();
        BigDecimal b_013 = new BigDecimal("0.13");
        int i1 = v.compareTo(BigDecimal.ZERO);
        //值对应约数
        Map<Double,Double> rateCountMap1 = this.getRateCountMap1(0.13, 1);
        //约数对应值范围
        Map<Double,Double[]> rateCountMap2 = this.getRateCountMap2(0.13, 1);

        //调整税额，得出调整后的金额
        for (MoneyModel model :tableData) {
            if( i1 == -1){
                if(n.subtract(b_013).compareTo(BigDecimal.ZERO) < 1) {
                    //取整数
                    BigDecimal integerPart = model.getData11().setScale(0, RoundingMode.DOWN);
                    //取小数部分
                    BigDecimal fractionalPart = model.getData11().subtract(integerPart);
                    //取约数 //当前数最多调整的约数
                    Double iscount = rateCountMap1.get(fractionalPart.doubleValue());
                    if(BigDecimal.valueOf(iscount).compareTo(n) == 1){
                        BigDecimal sub = BigDecimal.valueOf(iscount).subtract(n);
                        Double[] doubles1 = rateCountMap2.get(sub.doubleValue());
                        BigDecimal subtract = fractionalPart.subtract(BigDecimal.valueOf(doubles1[1]));
                        model.setData11(model.getData11().subtract(subtract));
                        model.setData15(model.getData15().subtract(n));
                        model.setData16("1");
                        editTableMap.put(model.getRownum(),model);
                        taxOfMoney = taxOfMoney.subtract(subtract);
                        break;
                    }else{
                        Double[] doubles = rateCountMap2.get(0.00);
                        BigDecimal subtract = fractionalPart.subtract(BigDecimal.valueOf(doubles[1]));
                        model.setData11(model.getData11().subtract(subtract));
                        model.setData15(model.getData15().subtract(BigDecimal.valueOf(iscount)));
                        model.setData16("1");
                        taxOfMoney = taxOfMoney.subtract(subtract);
                        editTableMap.put(model.getRownum(),model);
                        n = n.subtract(BigDecimal.valueOf(iscount));
                        continue;
                    }
                }else {
                    BigDecimal add_013 = model.getData11().subtract(new BigDecimal(1));
                    model.setData11(add_013);
                    model.setData15(model.getData15().subtract(b_013));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);

                    n = n.subtract(b_013);
                }
            }else if(i1 == 1){
                if(n.subtract(b_013).compareTo(BigDecimal.ZERO) < 1) {
                    //取整数
                    BigDecimal integerPart = model.getData11().setScale(0, RoundingMode.DOWN);
                    //取小数部分
                    BigDecimal fractionalPart = model.getData11().subtract(integerPart);
                    //取约数
                    Double iscount = rateCountMap1.get(fractionalPart.doubleValue());
                    //当前数最多调整的约数
                    BigDecimal subtract = b_013.subtract(BigDecimal.valueOf(iscount));
                    //
                    if(subtract.compareTo(n) == 1){
                        BigDecimal add = BigDecimal.valueOf(iscount).add(n);
                        Double[] doubles1 = rateCountMap2.get(add.doubleValue());
                        BigDecimal subtract1 = BigDecimal.valueOf(doubles1[1]).subtract(fractionalPart);
                        model.setData11(model.getData11().add(subtract1));
                        model.setData16("1");
                        model.setData15(model.getData15().add(n));
                        editTableMap.put(model.getRownum(),model);
                        taxOfMoney = taxOfMoney.add(subtract1);
                        break;
                    }else{
                        Double[] doubles1 = rateCountMap2.get(BigDecimal.valueOf(iscount).add(subtract));
                        BigDecimal subtract1 = BigDecimal.valueOf(doubles1[1]).subtract(fractionalPart);
                        model.setData11(model.getData11().add(subtract1));
                        model.setData15(model.getData15().add(subtract));
                        model.setData16("1");
                        editTableMap.put(model.getRownum(),model);
                        taxOfMoney = taxOfMoney.add(subtract1);
                        n = n.subtract(subtract);
                        continue;
                    }
                }else {
                    BigDecimal add_013 = model.getData11().add(new BigDecimal(1));
                    model.setData11(add_013);
                    model.setData15(model.getData15().add(b_013));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);
                    n = n.subtract(b_013);
                }
            }else{
                break;
            }
        }

        BigDecimal v1 = editTaxOfMoney.subtract(taxOfMoney);
        //要调整的数
        BigDecimal n1 = v1.abs();
        int i2 = v1.compareTo(BigDecimal.ZERO);
        //调整金额
        for(MoneyModel model :tableData){
            //取整数
            BigDecimal integerPart = model.getData11().setScale(0, RoundingMode.DOWN);
            //取小数部分
            BigDecimal fractionalPart = model.getData11().subtract(integerPart);
            Double aDouble = rateCountMap1.get(fractionalPart.doubleValue());
            Double[] doubles = rateCountMap2.get(aDouble);
            if(i2 == -1){
                //当前数最多可减
                BigDecimal subtract = BigDecimal.valueOf(doubles[1]).subtract(fractionalPart);
                if(subtract.compareTo(n1) > -1){
                    model.setData11(model.getData11().subtract(n1));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);
                    taxOfMoney = taxOfMoney.subtract(n1);
                    break;
                }else if(subtract.compareTo(BigDecimal.ZERO) == 0){
                    continue;
                }else {
                    model.setData11(model.getData11().subtract(subtract));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);
                    n1 = n1.subtract(subtract);
                    taxOfMoney = taxOfMoney.subtract(subtract);
                    continue;
                }
            }else if(i2 == 1){
                //当前数最多可加
                BigDecimal addtract = BigDecimal.valueOf(doubles[1]).subtract(fractionalPart);
                if(addtract.compareTo(n1) == 1){
                    model.setData11(model.getData11().add(n1));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);
                    taxOfMoney = taxOfMoney.add(n1);
                    break;
                }else {
                    model.setData11(model.getData11().add(addtract));
                    model.setData16("1");
                    editTableMap.put(model.getRownum(),model);
                    n1 = n1.subtract(addtract);
                    taxOfMoney = taxOfMoney.add(addtract);
                    continue;
                }
            }else {
                break;
            }
        }

//        //转换map
//        for(Integer key : editTableMap.keySet()) {
//            editTableMap.get(key).setData16("1");
//            editTableData.add(editTableMap.get(key));
//        }
        BigDecimal tax1 = new BigDecimal(0);
        BigDecimal tax2 = new BigDecimal(0);
        for(MoneyModel mdoel : tableData){
            tax1 = tax1.add(mdoel.getData15());
            tax2 = tax2.add(mdoel.getData11());
        }
        MoneyModel model1 = new MoneyModel();
        model1.setData11(tax2);
        model1.setData15(tax1);
//        enitry.setTax(tax1);
//        enitry.setTaxOfMoney(tax2);
        tableData.add(model1);
        enitry.setMoneyList(tableData);
        axiosVo.setObj(enitry);
        return axiosVo;
    }

    //值对应约数
    private Map<Double,Double> getRateCountMap1(Double rate,int count ) {
        Map<Double,Double> map = new HashMap<>();
        if (rate == 0.13 && count == 1){
            map.put(0.01,0.00);
            map.put(0.02,0.00);
            map.put(0.03,0.00);
            map.put(0.04,0.01);
            map.put(0.05,0.01);
            map.put(0.06,0.01);
            map.put(0.07,0.01);
            map.put(0.08,0.01);
            map.put(0.09,0.01);
            map.put(0.1,0.01);
            map.put(0.11,0.01);
            map.put(0.12,0.02);
            map.put(0.13,0.02);
            map.put(0.14,0.02);
            map.put(0.15,0.02);
            map.put(0.16,0.02);
            map.put(0.17,0.02);
            map.put(0.18,0.02);
            map.put(0.19,0.02);
            map.put(0.2,0.03);
            map.put(0.21,0.03);
            map.put(0.22,0.03);
            map.put(0.23,0.03);
            map.put(0.24,0.03);
            map.put(0.25,0.03);
            map.put(0.26,0.03);
            map.put(0.27,0.04);
            map.put(0.28,0.04);
            map.put(0.29,0.04);
            map.put(0.3,0.04);
            map.put(0.31,0.04);
            map.put(0.32,0.04);
            map.put(0.33,0.04);
            map.put(0.34,0.04);
            map.put(0.35,0.05);
            map.put(0.36,0.05);
            map.put(0.37,0.05);
            map.put(0.38,0.05);
            map.put(0.39,0.05);
            map.put(0.4,0.05);
            map.put(0.41,0.05);
            map.put(0.42,0.05);
            map.put(0.43,0.06);
            map.put(0.44,0.06);
            map.put(0.45,0.06);
            map.put(0.46,0.06);
            map.put(0.47,0.06);
            map.put(0.48,0.06);
            map.put(0.49,0.06);
            map.put(0.5,0.07);
            map.put(0.51,0.07);
            map.put(0.52,0.07);
            map.put(0.53,0.07);
            map.put(0.54,0.07);
            map.put(0.55,0.07);
            map.put(0.56,0.07);
            map.put(0.57,0.07);
            map.put(0.58,0.08);
            map.put(0.59,0.08);
            map.put(0.6,0.08);
            map.put(0.61,0.08);
            map.put(0.62,0.08);
            map.put(0.63,0.08);
            map.put(0.64,0.08);
            map.put(0.65,0.08);
            map.put(0.66,0.09);
            map.put(0.67,0.09);
            map.put(0.68,0.09);
            map.put(0.69,0.09);
            map.put(0.7,0.09);
            map.put(0.71,0.09);
            map.put(0.72,0.09);
            map.put(0.73,0.09);
            map.put(0.74,0.1);
            map.put(0.75,0.1);
            map.put(0.76,0.1);
            map.put(0.77,0.1);
            map.put(0.78,0.1);
            map.put(0.79,0.1);
            map.put(0.8,0.1);
            map.put(0.81,0.11);
            map.put(0.82,0.11);
            map.put(0.83,0.11);
            map.put(0.84,0.11);
            map.put(0.85,0.11);
            map.put(0.86,0.11);
            map.put(0.87,0.11);
            map.put(0.88,0.11);
            map.put(0.89,0.12);
            map.put(0.9,0.12);
            map.put(0.91,0.12);
            map.put(0.92,0.12);
            map.put(0.93,0.12);
            map.put(0.94,0.12);
            map.put(0.95,0.12);
            map.put(0.96,0.12);
            map.put(0.97,0.13);
            map.put(0.98,0.13);
            map.put(0.99,0.13);
        }
        return map;
    }

    //约数对应值范围
    private Map<Double,Double[]> getRateCountMap2(Double rate,int count ) {
        Map<Double,Double[]> map = new HashMap<>();
        if (rate == 0.13 && count == 1){
            map.put(0.00,new Double[]{0.01,0.03});
            map.put(0.01,new Double[]{0.04,0.11});
            map.put(0.02,new Double[]{0.12,0.19});
            map.put(0.03,new Double[]{0.20,0.26});
            map.put(0.04,new Double[]{0.27,0.34});
            map.put(0.05,new Double[]{0.35,0.42});
            map.put(0.06,new Double[]{0.43,0.49});
            map.put(0.07,new Double[]{0.50,0.57});
            map.put(0.08,new Double[]{0.58,0.65});
            map.put(0.09,new Double[]{0.66,0.73});
            map.put(0.10,new Double[]{0.74,0.80});
            map.put(0.11,new Double[]{0.81,0.88});
            map.put(0.12,new Double[]{0.89,0.96});
            map.put(0.13,new Double[]{0.97,0.99});
        }
        return map;
    }



    private Workbook createWorkbook(InputStream inp) throws IOException, InvalidFormatException {
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }
    private List<MoneyModel> listModelByExcel(Sheet sheet) {
        List<MoneyModel> list= new ArrayList<>();
        int rowNum = sheet.getPhysicalNumberOfRows();   //获取总行数
        int columnNum = sheet.getRow(1).getPhysicalNumberOfCells();//获得总列数
        BigDecimal taxOfMoney = new BigDecimal(0);
        BigDecimal tax = new BigDecimal(0);
        for (int i = 3; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            MoneyModel model = new MoneyModel();
            model.setRownum(i+1);
            model.setData14(new BigDecimal(0.13));
            for (int j = 0; j < columnNum; j++) {
                String cellVal = this.getCellValue(row, j);
                this.setModel(model,cellVal,j);
            }
            if("".equals(model.getData10()) || null == model.getData10()){
                continue;
            }
            list.add(model);
            taxOfMoney = taxOfMoney.add(model.getData11());
            tax = tax.add(model.getData15());
        }
        MoneyModel model = new MoneyModel();
        model.setData11(taxOfMoney);
        model.setData15(tax);
        list.add(model);
        return list;
    }

    private String getCellValue(Row row, int i) {
        if (null != row && null != row.getCell(i)) {
            return getCellValue(row.getCell(i));
        } else {
            return "";
        }

    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 数字
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    } else {
                        DataFormatter dataFormatter = new DataFormatter();
                        cellValue = dataFormatter.formatCellValue(cell);
                    }
                    break;
                case Cell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case Cell.CELL_TYPE_FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                case Cell.CELL_TYPE_BLANK: // 空值
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR: // 故障
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
        }
        if (cellValue != null) cellValue = cellValue.trim();
        return cellValue;
    }
    private MoneyModel setModel(MoneyModel model,String cellValue,int j){
        switch(j){
            case  0:
                model.setData5(cellValue);
                break;
            case  1:
                model.setData6(cellValue);
                break;
            case  2:
                model.setData7(cellValue);
                break;
            case  3:
                try {
                    model.setData9(new BigDecimal(cellValue));
                    break;
                } catch (Exception e) {
                    model.setData9(null);
                    break;
                }
            case  4:
                try {
                    model.setData10(new BigDecimal(cellValue));
                    break;
                } catch (Exception e) {
                    model.setData10(null);
                    break;
                }
            case  5:
                try {
                    BigDecimal mul = this.mul(model.getData9(), model.getData10());
                    model.setData11(mul);
                    BigDecimal mul1 = this.mul(model.getData11(), new BigDecimal(0.13));
                    model.setData15(mul1);
                    break;
                } catch (Exception e) {
                    model.setData11(null);
                    break;
                }
            case  7:
                model.setData17(cellValue);
                break;
            default :

        }
        return model;
    }
    private BigDecimal mul(BigDecimal b1,BigDecimal b2){
        BigDecimal b3 = b1.multiply(b2);
        return b3.setScale(2, RoundingMode.HALF_UP);
    }

}
