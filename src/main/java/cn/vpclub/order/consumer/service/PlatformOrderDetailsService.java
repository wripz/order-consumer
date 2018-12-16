package cn.vpclub.order.consumer.service;

import cn.vpclub.moses.utils.common.AccessExcelUtil;
import cn.vpclub.moses.utils.common.IdWorker;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.order.consumer.entityProvider.PlatformOrderDetailsProvider;
import cn.vpclub.order.consumer.model.request.PlatformOrderDetailsPageParam;;
import cn.vpclub.order.consumer.entity.PlatformOrderDetails;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.order.consumer.serviceProvider.IPlatformOrderDetailsService;
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
 * 平台订单明细表 service业务层
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformOrderDetailsService {


    @Autowired
    private IPlatformOrderDetailsService iPlatformOrderDetailsService;

    /**
    * 添加方法
    * @return
    */
    public BaseResponse add(PlatformOrderDetails request) {
        BaseResponse response;
        //业务操作
        log.info("add PlatformOrderDetailsProvider: {}", request);
        try {
            request.validate(ValidatorConditionType.CREATE);
            PlatformOrderDetailsProvider platformOrderDetailsProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformOrderDetailsProvider.class);
            response = iPlatformOrderDetailsService.add(platformOrderDetailsProvider);
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
    public BaseResponse update(PlatformOrderDetails request) {
        BaseResponse response;
        //业务操作
        log.info("update PlatformOrderDetailsProvider : {}", request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
            PlatformOrderDetailsProvider platformOrderDetailsProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformOrderDetailsProvider.class);
            response = iPlatformOrderDetailsService.update(platformOrderDetailsProvider);
            log.debug("update PlatformOrderDetailsProvider back : {}", response);
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
    public BaseResponse query(PlatformOrderDetails request) {
        BaseResponse response;
        //业务操作
        log.info("query PlatformOrderDetailsProvider : {}", request);
        try {
            request.validate(ValidatorConditionType.READ);
            PlatformOrderDetailsProvider platformOrderDetailsProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformOrderDetailsProvider.class);
            response = iPlatformOrderDetailsService.query(platformOrderDetailsProvider);
            log.info("query PlatformOrderDetailsProvider back: {}", response);
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
    public BaseResponse delete(PlatformOrderDetails request) {
        BaseResponse response;
        //业务操作
        log.info("delete PlatformOrderDetailsProvider " + request);
        try {
            request.validate(ValidatorConditionType.DELETE);
            PlatformOrderDetailsProvider platformOrderDetailsProvider = JSON.parseObject(JsonUtil.objectToJson(request), PlatformOrderDetailsProvider.class);
            response = iPlatformOrderDetailsService.delete(platformOrderDetailsProvider);
            log.debug("delete PlatformOrderDetailsProvider back : {}", response);
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
    public PageResponse page(PlatformOrderDetailsPageParam request) {
        PageResponse response = new PageResponse();
        //业务操作
        log.info("PlatformOrderDetailsProvider page method request : {}", request);
        if (null == request) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("PlatformOrderDetailsProvider page method request : {}",request);
            Page responsePage = iPlatformOrderDetailsService.page(request);

            response = JSON.parseObject(JsonUtil.objectToJson(responsePage), PageResponse.class);

            //处理结果
            if (CollectionUtils.isNotEmpty(responsePage.getRecords())) {
                response.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1000.getValue());
            } else {
                response.setReturnCode(ReturnCodeEnum.CODE_1002.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1002.getValue());
            }

            List<PlatformOrderDetailsProvider> records = responsePage.getRecords();
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
                //下游订单号
                attributeList.add("downstreamOrderNo");
                //系统订单号
                attributeList.add("systemOrderNo");
                //商户编号
                attributeList.add("merchantCode");
                //交易金额
                attributeList.add("transactionAmount");
                //供码成本
                attributeList.add("cost");
                //平台入账
                attributeList.add("platformIncome");
                //商户回款
                attributeList.add("merchantReturnedMoney");
                //一级分润
                attributeList.add("firstShareProfit");
                //提交时间
                attributeList.add("submitTime");
                //成功时间
                attributeList.add("succeedTime");
                //通道商户号
                attributeList.add("channelMerchantNumber");
                //通道id
                attributeList.add("channelId");
                //通道名称
                attributeList.add("channelName");

                //1.解析excel。将其转化为对象集合
                List<PlatformOrderDetails> platformAlipayImport = AccessExcelUtil.parseExcel(inputStream, PlatformOrderDetails.class,
                        attributeList, 1, 0);
                int count = platformAlipayImport.size();
                log.info("信息excel转成MerchantAlipay对象个数: {}", count);
                //如果对象集合大小不为0
                if (count > 0) {
                    //文件名
                    String originalFilename = file.getOriginalFilename();

                    List<PlatformOrderDetailsProvider> merchantAlipayProviderList = new ArrayList<>();
                    for (PlatformOrderDetails platformOrderDetails : platformAlipayImport) {
                        platformOrderDetails.setId(IdWorker.getId());
                        platformOrderDetails.setImportFileName(originalFilename);
                        PlatformOrderDetailsProvider merchantAlipayProvider = JSON.parseObject(JsonUtil.objectToJson(platformOrderDetails), PlatformOrderDetailsProvider.class);
                        merchantAlipayProviderList.add(merchantAlipayProvider);
                    }

                    //批量插入
                    //避免数据量太大分成多个List进行数据操作
                    List<List> resDate = AverageAssignUtil.averageAssign(merchantAlipayProviderList);
                    int i = 1;
                    for (List<PlatformOrderDetailsProvider> importDate : resDate) {

                        log.info("批量第"+ i +"次导入请求size：{}", importDate.size());
                        baseResponse = iPlatformOrderDetailsService.insertList(importDate);
                        log.info("批量第"+ i +"次导入返回：{}", JsonUtil.objectToJson(baseResponse));
                        i++;
                    }

//                    log.info("批量导入请求size：{}", merchantAlipayProviderList.size());
//                    baseResponse = iPlatformOrderDetailsService.insertList(merchantAlipayProviderList);
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
