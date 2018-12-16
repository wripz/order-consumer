package cn.vpclub.order.consumer.service;

import cn.vpclub.moses.utils.common.AccessExcelUtil;
import cn.vpclub.moses.utils.common.IdWorker;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.order.consumer.entityProvider.PlatformAlipayProvider;
import cn.vpclub.order.consumer.model.request.PlatformAlipayPageParam;;
import cn.vpclub.order.consumer.entity.PlatformAlipay;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.order.consumer.serviceProvider.IPlatformAlipayService;
import cn.vpclub.order.consumer.utils.AverageAssignUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 平台支付宝表 service业务层
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformAlipayService {


    @Autowired
    private IPlatformAlipayService iPlatformAlipayService;

    /**
    * 添加方法
    * @return
    */
    public BaseResponse add(PlatformAlipay request) {
        BaseResponse response;
        //业务操作
        log.info("add PlatformAlipayProvider: {}", request);
        try {
            request.validate(ValidatorConditionType.CREATE);
            PlatformAlipayProvider platformAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformAlipayProvider.class);
            response = iPlatformAlipayService.add(platformAlipayProvider);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
    * 修改方法
    * @return
    */
    public BaseResponse update(PlatformAlipay request) {
        BaseResponse response;
        //业务操作
        log.info("update PlatformAlipayProvider : {}", request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
            PlatformAlipayProvider platformAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformAlipayProvider.class);
            response = iPlatformAlipayService.update(platformAlipayProvider);
            log.debug("update PlatformAlipayProvider back : {}", response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
    * 单个查询
    * @return
    */
    public BaseResponse query(PlatformAlipay request) {
        BaseResponse response;
        //业务操作
        log.info("query PlatformAlipayProvider : {}", request);
        try {
            request.validate(ValidatorConditionType.READ);
            PlatformAlipayProvider platformAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformAlipayProvider.class);
            response = iPlatformAlipayService.query(platformAlipayProvider);
            log.info("query PlatformAlipayProvider back: {}", response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
    * 删除
    * @return
    */
    public BaseResponse delete(PlatformAlipay request) {
        BaseResponse response;
        //业务操作
        log.info("delete PlatformAlipayProvider " + request);
        try {
            request.validate(ValidatorConditionType.DELETE);
            PlatformAlipayProvider platformAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformAlipayProvider.class);
            response = iPlatformAlipayService.delete(platformAlipayProvider);
            log.debug("delete PlatformAlipayProvider back : {}", response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
    * 条件查询分页列表
    * @param request
    * @return
    */
    public PageResponse page(PlatformAlipayPageParam request) {
        PageResponse response = new PageResponse();
        //业务操作
        log.info("PlatformAlipayProvider page method request : {}", request);
        if (null == request) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("PlatformAlipayProvider page method request : {}",request);
            Page responsePage = iPlatformAlipayService.page(request);

            response = JSON.parseObject(JsonUtil.objectToJson(responsePage), PageResponse.class);

            //处理结果
            if (CollectionUtils.isNotEmpty(responsePage.getRecords())) {
                response.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1000.getValue());
            } else {
                response.setReturnCode(ReturnCodeEnum.CODE_1002.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1002.getValue());
            }

            List<PlatformAlipayProvider> records = responsePage.getRecords();
            response.setRecords(records);
        }
        return response;
    }

    /**
     * 批量导入
     */
    public BaseResponse importExcel(MultipartFile file) {
        BaseResponse baseResponse = new BaseResponse();
        if (file.isEmpty()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("批量导入信息导入请求参数: {}", "文件大小:" + file.getSize());
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                List<String> attributeList = new ArrayList<>();
                //账务流水号
                attributeList.add("accountNumber");
                //业务流水号
                attributeList.add("serviceSerialNumber");
                //商户订单号
                attributeList.add("merchantOrderNo");
                //商品名称
                attributeList.add("commodityName");
                //发生时间
                attributeList.add("occurrenceTime");
                //对方账号
                attributeList.add("reciprocalAccountNumber");
                //收入金额（+元）
                attributeList.add("income");
                //支出金额（-元）
                attributeList.add("amountPaid");
                //账户余额（元）
                attributeList.add("balance");
                //交易渠道
                attributeList.add("transactionChannel");
                //业务类型
                attributeList.add("businessType");
                //备注
                attributeList.add("remark");

                //1.解析excel。将其转化为对象集合
                List<PlatformAlipay> platformAlipayImport = AccessExcelUtil.parseExcel(inputStream, PlatformAlipay.class,
                        attributeList, 5, 0);
                int count = platformAlipayImport.size();
                log.info("信息excel转成MerchantAlipay对象个数: {}", count);
                //如果对象集合大小不为0
                if (count > 0) {
                    //文件名
                    String originalFilename = file.getOriginalFilename();

                    List<PlatformAlipayProvider> platformAlipayProviderArrayList = new ArrayList<>();
                    for (PlatformAlipay platformAlipay : platformAlipayImport) {
                        platformAlipay.setId(IdWorker.getId());
                        platformAlipay.setImportFileName(originalFilename);
                        PlatformAlipayProvider merchantAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(platformAlipay), PlatformAlipayProvider.class);
                        platformAlipayProviderArrayList.add(merchantAlipayProvider);
                    }

                    //批量插入
                    //避免数据量太大分成多个List进行数据操作
                    List<List> resDate = AverageAssignUtil.averageAssign(platformAlipayProviderArrayList);
                    int i = 1;
                    for (List<PlatformAlipayProvider> importDate : resDate) {

                        log.info("批量第"+ i +"次导入请求size：{}", importDate.size());
                        baseResponse = iPlatformAlipayService.insertList(importDate);
                        log.info("批量第"+ i +"次导入返回：{}", JsonUtil.objectToJson(baseResponse));
                        i++;
                    }

//                    log.info("批量导入请求size：{}", platformAlipayProviderArrayList.size());
//                    baseResponse = iPlatformAlipayService.insertList(platformAlipayProviderArrayList);
//                    log.info("批量导入生成兑换码返回：{}", JsonUtil.objectToJson(baseResponse));
                } else {
                    baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
                    baseResponse.setMessage("导入表格中不存在信息!");
                    //return baseResponse;
                }
            } catch (Exception e) {
                log.error("批量导入生成兑换码导入失败: {}", e);
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
                baseResponse.setMessage("批量导入生成兑换码信息导入失败");
//                baseResponse.setDataInfo(e);
                //throw new RuntimeException(e);
            }
        }
        return baseResponse;
    }

}
