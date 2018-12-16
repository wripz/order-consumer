package cn.vpclub.order.consumer.service;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.common.AccessExcelUtil;
import cn.vpclub.moses.utils.common.DateUtil;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import cn.vpclub.order.consumer.model.request.AlipayOrderBillPageParam;
import cn.vpclub.order.consumer.model.request.PlatformBillPageParam;
import cn.vpclub.order.consumer.model.response.AmountNoDifference;
import cn.vpclub.order.consumer.model.response.ExportExcelSheets;
import cn.vpclub.order.consumer.model.response.LessMoney;
import cn.vpclub.order.consumer.model.response.Overcharge;
import cn.vpclub.order.consumer.serviceProvider.IAlipayOrderBillService;
import cn.vpclub.order.consumer.serviceProvider.IPlatformBillService;
import cn.vpclub.order.consumer.utils.AverageAssignUtil;
import cn.vpclub.order.consumer.utils.ExportExcelUtils;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AccountCheckingService {


    @Autowired
    private IAlipayOrderBillService iAlipayOrderBillService;

    @Autowired
    private IPlatformBillService iPlatformBillService;
    
    public ExportExcelSheets accountChecking(){
        ExportExcelSheets exportExcelSheets = new ExportExcelSheets();

        DecimalFormat df = new DecimalFormat("######0.00000");

        //查询支付宝订单账单
        AlipayOrderBillPageParam alipayOrderBillPageParam = new AlipayOrderBillPageParam();
        alipayOrderBillPageParam.setPageNumber(1);
        alipayOrderBillPageParam.setPageSize(Integer.MAX_VALUE);
        Page alipayOrderBillPage = iAlipayOrderBillService.page(alipayOrderBillPageParam);
        
        //查询平台账单
        PlatformBillPageParam platformBillPageParam = new PlatformBillPageParam();
        platformBillPageParam.setPageNumber(1);
        platformBillPageParam.setPageSize(Integer.MAX_VALUE);
        Page platformBillPage = iPlatformBillService.page(platformBillPageParam);

        if (null != alipayOrderBillPage && CollectionUtils.isNotEmpty(alipayOrderBillPage.getRecords())
                && null != platformBillPage && CollectionUtils.isNotEmpty(platformBillPage.getRecords())){
            List<AlipayOrderBill> alipayOrderBillRecords = alipayOrderBillPage.getRecords();
            List<PlatformBill> platformBillRecords = platformBillPage.getRecords();

            //两个分别以订单号为key放入map
            Map<String,AlipayOrderBill> alipayOrderBillMap = new HashMap<>();
            Map<String,PlatformBill> platformBillMap = new HashMap<>();
            for (AlipayOrderBill alipayOrderBill : alipayOrderBillRecords) {
                alipayOrderBillMap.put(alipayOrderBill.getMerchantOrderNo(), alipayOrderBill);
            }
            for (PlatformBill platformBill : platformBillRecords) {
                platformBillMap.put(platformBill.getSystemOrderNo(), platformBill);
            }

            //比对两个的数据
            //支付宝有平台无
            List<AlipayOrderBill> alipayOrderBillHave = new ArrayList<>();
            //平台有支付宝无
            List<PlatformBill> platformBillHave = new ArrayList<>();
            //金额无差别
            List<AmountNoDifference> amountNoDifferenceList = new ArrayList<>();
            //多收
            List<Overcharge> overchargeList = new ArrayList<>();
            //少收
            List<LessMoney> lessMoneyList = new ArrayList<>();
            //遍历平台数据
            for (PlatformBill platformBill : platformBillRecords) {
                String systemOrderNo = platformBill.getSystemOrderNo();
                AlipayOrderBill alipayOrderBill = alipayOrderBillMap.get(systemOrderNo);
                if (StringUtil.isEmpty(alipayOrderBill)){
                    platformBillHave.add(platformBill);
                }else {
                    BigDecimal platformReceivableBigDecimal = platformBill.getPlatformReceivable();
                    BigDecimal incomeBigDecimal = alipayOrderBill.getIncome();
                    double platformReceivable = Double.valueOf(df.format(platformReceivableBigDecimal.doubleValue()));
                    double income = Double.valueOf(df.format(incomeBigDecimal.doubleValue()));
                    if (platformReceivable == income){
                        AmountNoDifference amountNoDifference = new AmountNoDifference();
                        amountNoDifference.setSystemOrderNo(platformBill.getSystemOrderNo());
                        amountNoDifference.setPlatformReceivable(platformBill.getPlatformReceivable());
                        amountNoDifference.setSucceedTime(platformBill.getSucceedTime());
                        amountNoDifferenceList.add(amountNoDifference);
                    }else if (platformReceivable > income){
                        //少收
                        LessMoney lessMoney = new LessMoney();
                        lessMoney.setBusinessType(alipayOrderBill.getBusinessType());
                        lessMoney.setSystemOrderNo(platformBill.getSystemOrderNo());

                        java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
                        NF.setGroupingUsed(false);//去掉科学计数法显示
                        String format = df.format((income - platformReceivable));
                        lessMoney.setDifference(format);
//                        lessMoney.setDifference(Double.valueOf(df.format((income-platformReceivable))));

                        lessMoney.setIncome(alipayOrderBill.getIncome());
                        lessMoney.setPlatformReceivable(platformBill.getPlatformReceivable());
                        lessMoneyList.add(lessMoney);
                    }else if (platformReceivable < income){
                        //多收
                        Overcharge overcharge = new Overcharge();
                        overcharge.setBusinessType(alipayOrderBill.getBusinessType());
                        overcharge.setSystemOrderNo(platformBill.getSystemOrderNo());

                        java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
                        NF.setGroupingUsed(false);//去掉科学计数法显示
                        String format = df.format((income - platformReceivable));
                        overcharge.setDifference(format);
//                        overcharge.setDifference(Double.valueOf(df.format((income-platformReceivable))));

                        overcharge.setIncome(alipayOrderBill.getIncome());
                        overcharge.setPlatformReceivable(platformBill.getPlatformReceivable());
                        overchargeList.add(overcharge);
                    }
                }

            }
            //遍历支付宝数据
            for (AlipayOrderBill alipayOrderBill : alipayOrderBillRecords) {
                String merchantOrderNo = alipayOrderBill.getMerchantOrderNo();
                PlatformBill platformBill = platformBillMap.get(merchantOrderNo);
                if (StringUtil.isEmpty(platformBill)){
                    alipayOrderBillHave.add(alipayOrderBill);
                }
            }


            exportExcelSheets.setPlatformBillHave(platformBillHave);
            exportExcelSheets.setAlipayOrderBillHave(alipayOrderBillHave);
            exportExcelSheets.setAmountNoDifferenceList(amountNoDifferenceList);
            exportExcelSheets.setLessMoneyList(lessMoneyList);
            exportExcelSheets.setOverchargeList(overchargeList);


        }

        return exportExcelSheets;

    }

    /**
     * 按条件导出Excel表
     */
    public void export(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        List<String> heads = new ArrayList<>();//存放EXCEL文件列表头信息数组
        heads.add("系统订单号");
        heads.add("平台回款");
        heads.add("成功时间");
        //ArrayList对象中存放Excel表格各列对应数据库表的中字段的名称field
        List<String> filedNameList = new ArrayList<>();
        //系统订单号
        filedNameList.add("systemOrderNo");
        //平台回款
        filedNameList.add("platformReceivable");
        //成功时间
        filedNameList.add("succeedTime");

        String fileName = "无差异" + ".xlsx";//文件名
        final String userAgent = httpServletRequest.getHeader("USER-AGENT");//获取请求消息头
        try {
            String finalFileName = null;
            if (userAgent.contains("MSIE") || userAgent.contains("like Gecko")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");//其他浏览器
            }

            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);


            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> amountNoDifferenceList = exportExcelSheets.getAmountNoDifferenceList();

            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();


            OutputStream fileOpStream = httpServletResponse.getOutputStream();
            AccessExcelUtil.createExcel(fileOpStream, fileName, heads, filedNameList, amountNoDifferenceList);
            long endTime = System.currentTimeMillis();
            log.info("导出:" + (endTime - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error("导出:", e);
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
                log.error("物流信息输出流关闭失败: {}", e);
            }
        }
    }

    /**
     * 按条件导出Excel表
     */
    public void exportMore(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        List<String> heads = new ArrayList<>();//存放EXCEL文件列表头信息数组
        heads.add("系统订单号");
        heads.add("平台回款");
        heads.add("收入金额");
        heads.add("差额");
        heads.add("业务类型");
        //ArrayList对象中存放Excel表格各列对应数据库表的中字段的名称field
        List<String> filedNameList = new ArrayList<>();
        //系统订单号
        filedNameList.add("systemOrderNo");
        //平台回款
        filedNameList.add("platformReceivable");
        //收入金额
        filedNameList.add("income");
        //差额
        filedNameList.add("difference");
        //业务类型
        filedNameList.add("businessType");

        String fileName = "多收" + ".xlsx";//文件名
        final String userAgent = httpServletRequest.getHeader("USER-AGENT");//获取请求消息头
        try {
            String finalFileName = null;
            if (userAgent.contains("MSIE") || userAgent.contains("like Gecko")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");//其他浏览器
            }

            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);


            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> exports = exportExcelSheets.getAmountNoDifferenceList();

            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();


            OutputStream fileOpStream = httpServletResponse.getOutputStream();
            AccessExcelUtil.createExcel(fileOpStream, fileName, heads, filedNameList, overchargeList);
            long endTime = System.currentTimeMillis();
            log.info("导出:" + (endTime - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error("导出:", e);
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
                log.error("物流信息输出流关闭失败: {}", e);
            }
        }
    }

    /**
     * 按条件导出Excel表
     */
    public void exportLess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        List<String> heads = new ArrayList<>();//存放EXCEL文件列表头信息数组
        heads.add("系统订单号");
        heads.add("平台回款");
        heads.add("收入金额");
        heads.add("差额");
        heads.add("业务类型");
        //ArrayList对象中存放Excel表格各列对应数据库表的中字段的名称field
        List<String> filedNameList = new ArrayList<>();
        //系统订单号
        filedNameList.add("systemOrderNo");
        //平台回款
        filedNameList.add("platformReceivable");
        //收入金额
        filedNameList.add("income");
        //差额
        filedNameList.add("difference");
        //业务类型
        filedNameList.add("businessType");

        String fileName = "少收" + ".xlsx";//文件名
        final String userAgent = httpServletRequest.getHeader("USER-AGENT");//获取请求消息头
        try {
            String finalFileName = null;
            if (userAgent.contains("MSIE") || userAgent.contains("like Gecko")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");//其他浏览器
            }

            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);


            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> exports = exportExcelSheets.getAmountNoDifferenceList();

            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();


            OutputStream fileOpStream = httpServletResponse.getOutputStream();
            AccessExcelUtil.createExcel(fileOpStream, fileName, heads, filedNameList, lessMoneyList);
            long endTime = System.currentTimeMillis();
            log.info("导出:" + (endTime - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error("导出:", e);
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
                log.error("物流信息输出流关闭失败: {}", e);
            }
        }
    }

    /**
     * 按条件导出Excel表
     */
    public void exportalipayOrderBillHave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        List<String> heads = new ArrayList<>();//存放EXCEL文件列表头信息数组
        heads.add("系统订单号");
        heads.add("平台回款");
        heads.add("收入金额");
        heads.add("差额");
        heads.add("业务类型");
        //ArrayList对象中存放Excel表格各列对应数据库表的中字段的名称field
        List<String> filedNameList = new ArrayList<>();
        //系统订单号
        filedNameList.add("systemOrderNo");
        //平台回款
        filedNameList.add("platformReceivable");
        //收入金额
        filedNameList.add("income");
        //差额
        filedNameList.add("difference");
        //业务类型
        filedNameList.add("businessType");

        String fileName = "支付宝有平台无" + ".xlsx";//文件名
        final String userAgent = httpServletRequest.getHeader("USER-AGENT");//获取请求消息头
        try {
            String finalFileName = null;
            if (userAgent.contains("MSIE") || userAgent.contains("like Gecko")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");//其他浏览器
            }

            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);


            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> exports = exportExcelSheets.getAmountNoDifferenceList();

            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();


            OutputStream fileOpStream = httpServletResponse.getOutputStream();
            AccessExcelUtil.createExcel(fileOpStream, fileName, heads, filedNameList, alipayOrderBillHave);
            long endTime = System.currentTimeMillis();
            log.info("导出:" + (endTime - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error("导出:", e);
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
                log.error("输出流关闭失败: {}", e);
            }
        }
    }

    /**
     * 按条件导出Excel表
     */
    public void exportplatformBillHave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        List<String> heads = new ArrayList<>();//存放EXCEL文件列表头信息数组
        heads.add("系统订单号");
        heads.add("平台回款");
        heads.add("收入金额");
        heads.add("差额");
        heads.add("业务类型");
        //ArrayList对象中存放Excel表格各列对应数据库表的中字段的名称field
        List<String> filedNameList = new ArrayList<>();
        //系统订单号
        filedNameList.add("systemOrderNo");
        //平台回款
        filedNameList.add("platformReceivable");
        //收入金额
        filedNameList.add("income");
        //差额
        filedNameList.add("difference");
        //业务类型
        filedNameList.add("businessType");

        String fileName = "平台有支付宝无" + ".xlsx";//文件名
        final String userAgent = httpServletRequest.getHeader("USER-AGENT");//获取请求消息头
        try {
            String finalFileName = null;
            if (userAgent.contains("MSIE") || userAgent.contains("like Gecko")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");//其他浏览器
            }

            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);


            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> exports = exportExcelSheets.getAmountNoDifferenceList();

            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();


            OutputStream fileOpStream = httpServletResponse.getOutputStream();
            AccessExcelUtil.createExcel(fileOpStream, fileName, heads, filedNameList, platformBillHave);
            long endTime = System.currentTimeMillis();
            log.info("导出:" + (endTime - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error("导出:", e);
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
                log.error("输出流关闭失败: {}", e);
            }
        }
    }

    public BaseResponse exportSheets() {
        BaseResponse baseResponse = new BaseResponse();
        try {

            String fileName = "对比结果"+ DateUtil.formatDate(new Date(), DateUtil.YEARMONTHDAYHHMMSS) + ".xlsx";//文件名;
            OutputStream out = new FileOutputStream("D:\\对比结果\\"+fileName);
            ExportExcelUtils eeu = new ExportExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();

            ExportExcelSheets exportExcelSheets = accountChecking();
            List<AmountNoDifference> amountNoDifferenceList = exportExcelSheets.getAmountNoDifferenceList();
            List<AlipayOrderBill> alipayOrderBillHave = exportExcelSheets.getAlipayOrderBillHave();
            List<LessMoney> lessMoneyList = exportExcelSheets.getLessMoneyList();
            List<Overcharge> overchargeList = exportExcelSheets.getOverchargeList();
            List<PlatformBill> platformBillHave = exportExcelSheets.getPlatformBillHave();

            int sheetNum = 0;

            List<List> resDate1 = AverageAssignUtil.averageAssign60000(alipayOrderBillHave);
            int temp1 = 1;
            for(List<AlipayOrderBill> alipayOrderBills : resDate1) {
                List<List<Object>> dataalipayOrderBillHave = new ArrayList<List<Object>>();
                for (AlipayOrderBill alipayOrderBill : alipayOrderBills) {
                    List rowData = new ArrayList();
                    rowData.add(String.valueOf(alipayOrderBill.getMerchantOrderNo()));

                    String dateStr = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(alipayOrderBill.getOccurrenceTime());
                    rowData.add(dateStr);

                    rowData.add(alipayOrderBill.getIncome());
                    rowData.add(String.valueOf(alipayOrderBill.getBusinessType()));

                    dataalipayOrderBillHave.add(rowData);
                }
                String[] headersalipayOrderBillHave = {"商户订单号", "发生时间", "收入金额（+元）", "业务类型"};
                eeu.exportExcel1(workbook, sheetNum, "支付宝有平台无" + temp1, headersalipayOrderBillHave, dataalipayOrderBillHave, out);
                sheetNum++;
                temp1++;
            }

            List<List> resDate2 = AverageAssignUtil.averageAssign60000(platformBillHave);
            int temp2 = 1;
            for(List<PlatformBill> platformBills : resDate2) {
                List<List<Object>> dataplatformBillHave = new ArrayList<List<Object>>();
                for (PlatformBill platformBill : platformBills) {
                    List rowData = new ArrayList();
                    rowData.add(String.valueOf(platformBill.getSystemOrderNo()));
                    rowData.add(platformBill.getPlatformReceivable());

                    String dateStr = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(platformBill.getSucceedTime());
                    rowData.add(dateStr);

                    dataplatformBillHave.add(rowData);
                }
                String[] headersplatformBillHave = {"系统订单号", "平台回款", "成功时间"};
                eeu.exportExcel2(workbook, sheetNum, "平台有支付宝无"+temp2, headersplatformBillHave, dataplatformBillHave, out);
                sheetNum++;
                temp2++;
            }

            List<List> resDate3 = AverageAssignUtil.averageAssign60000(amountNoDifferenceList);
            int temp3 = 1;
            for(List<AmountNoDifference> amountNoDifferences : resDate3) {
                List<List<Object>> dataamountNoDifferenceList = new ArrayList<List<Object>>();
                for (AmountNoDifference amountNoDifference : amountNoDifferences) {
                    List rowData = new ArrayList();
                    rowData.add(String.valueOf(amountNoDifference.getSystemOrderNo()));
                    rowData.add(amountNoDifference.getPlatformReceivable());


                    String dateStr = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(amountNoDifference.getSucceedTime());
                    rowData.add(dateStr);
//                rowData.add(String.valueOf(amountNoDifference.getSucceedTime()));

                    dataamountNoDifferenceList.add(rowData);
                }
                String[] headersamountNoDifference = {"系统订单号", "平台回款", "成功时间"};
                eeu.exportExcel2(workbook, sheetNum, "无差异"+temp3, headersamountNoDifference, dataamountNoDifferenceList, out);
                sheetNum++;
                temp3++;
            }

            List<List> resDate4 = AverageAssignUtil.averageAssign60000(overchargeList);
            int temp4 = 1;
            for(List<Overcharge> overcharges : resDate4) {
                List<List<Object>> dataoverchargeList = new ArrayList<List<Object>>();
                for (Overcharge overcharge : overcharges) {
                    List rowData = new ArrayList();
                    rowData.add(String.valueOf(overcharge.getSystemOrderNo()));
                    rowData.add(overcharge.getIncome());
                    rowData.add(overcharge.getPlatformReceivable());
                    rowData.add(overcharge.getDifference());
                    rowData.add(String.valueOf(overcharge.getBusinessType()));

                    dataoverchargeList.add(rowData);
                }
                String[] headersovercharge = {"系统订单号", "收入金额", "平台回款", "差额", "业务类型"};
                eeu.exportExcel3(workbook, sheetNum, "都有，多收"+temp4, headersovercharge, dataoverchargeList, out);
                sheetNum++;
                temp4++;
            }

            List<List> resDate5 = AverageAssignUtil.averageAssign60000(lessMoneyList);
            int temp5 = 1;
            for(List<LessMoney> lessMoneys : resDate5) {
                List<List<Object>> datalessMoneyList = new ArrayList<List<Object>>();
                for(LessMoney lessMoney : lessMoneys){
                    List rowData = new ArrayList();
                    rowData.add(String.valueOf(lessMoney.getSystemOrderNo()));
                    rowData.add(lessMoney.getIncome());
                    rowData.add(lessMoney.getPlatformReceivable());
                    rowData.add(lessMoney.getDifference());
                    rowData.add(lessMoney.getBusinessType());

                    datalessMoneyList.add(rowData);
                }
                String[] headerslessMoney = { "系统订单号", "收入金额", "平台回款", "差额", "业务类型"};
                eeu.exportExcel3(workbook, sheetNum, "都有，少收"+temp5, headerslessMoney, datalessMoneyList, out);
                sheetNum++;
                temp5++;
            }

            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.close();
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1004.getCode());
            baseResponse.setDataInfo(e);
        }

        return baseResponse;
    }

}
