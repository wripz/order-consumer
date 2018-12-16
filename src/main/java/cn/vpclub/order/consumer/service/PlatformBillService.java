package cn.vpclub.order.consumer.service;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.common.AccessExcelUtil;
import cn.vpclub.moses.utils.common.IdWorker;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import cn.vpclub.order.consumer.model.request.PlatformBillPageParam;
import cn.vpclub.order.consumer.serviceProvider.IPlatformBillService;
import cn.vpclub.order.consumer.utils.AverageAssignUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

;import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 平台账单 service业务层
 * </p>
 *
 * @author ${author}
 * @since 2018-12-07
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformBillService {

    @Autowired
    private IPlatformBillService iPlatformBillService;

    /**
    * 添加方法
    * @return
    */
    public BaseResponse add(PlatformBill request) {
        BaseResponse response;
        //业务操作
        log.info("add PlatformBill: {}", request);
        try {
//            request.validate(ValidatorConditionType.CREATE);
            response = iPlatformBillService.add(request);
        } catch (Exception e) {
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
    public BaseResponse update(PlatformBill request) {
        BaseResponse response;
        //业务操作
        log.info("update PlatformBill : {}", request);
        try {
//            request.validate(ValidatorConditionType.UPDATE);
            response = iPlatformBillService.update(request);
            log.debug("update PlatformBill back : {}", response);
        } catch (Exception e) {
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
    public BaseResponse query(PlatformBill request) {
        BaseResponse response;
        //业务操作
        log.info("query PlatformBill : {}", request);
        try {
//            request.validate(ValidatorConditionType.READ);
            response = iPlatformBillService.query(request);
            log.info("query PlatformBill back: {}", response);
        } catch (Exception e) {
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
    public BaseResponse delete(PlatformBill request) {
        BaseResponse response;
        //业务操作
        log.info("delete PlatformBill " + request);
        try {
//            request.validate(ValidatorConditionType.DELETE);
            response = iPlatformBillService.delete(request);
            log.debug("delete PlatformBill back : {}", response);
        } catch (Exception e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 删除所有
     * @return
     */
    public BaseResponse deleteAll(PlatformBill request) {
        BaseResponse response;
        //业务操作
        log.info("deleteAll AlipayOrderBill " + request);
        try {
//            request.validate(ValidatorConditionType.DELETE);
            response = iPlatformBillService.deleteAll(request);
            log.debug("deleteAll AlipayOrderBill back : {}", response);
        } catch (Exception e) {
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
    public PageResponse page(PlatformBillPageParam request) {
        PageResponse response = new PageResponse();
        //业务操作
        log.info("PlatformBill page method request : {}", request);
        if (null == request) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("PlatformBill page method request : {}",request);
            Page responsePage = iPlatformBillService.page(request);
            response = JSON.parseObject(JsonUtil.objectToJson(responsePage), PageResponse.class);
            //处理结果
            if (CollectionUtils.isNotEmpty(responsePage.getRecords())) {
                response.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1000.getValue());
            } else {
                response.setReturnCode(ReturnCodeEnum.CODE_1002.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1002.getValue());
            }
            List<PlatformBill> records = responsePage.getRecords();
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
                //系统订单号
                attributeList.add("systemOrderNo");
                //平台回款
                attributeList.add("platformReceivable");
                //成功时间
                attributeList.add("succeedTime");

                //1.解析excel。将其转化为对象集合
                List<PlatformBill> platformBillImport = AccessExcelUtil.parseExcel(inputStream, PlatformBill.class,
                        attributeList, 1, 0);
                int count = platformBillImport.size();
                log.info("信息excel转成AlipayOrderBill对象个数: {}", count);
                //如果对象集合大小不为0
                if (count > 0) {
                    //文件名
                    String originalFilename = file.getOriginalFilename();

                    List<PlatformBill> platformBillList = new ArrayList<>();
                    for (PlatformBill platformBill : platformBillImport) {
                        platformBill.setId(IdWorker.getId());
                        String systemOrderNo = platformBill.getSystemOrderNo();
                        systemOrderNo = systemOrderNo.replaceAll("\\s*", "");
                        platformBill.setSystemOrderNo(systemOrderNo);
                    }


                    //批量插入
                    //避免数据量太大分成多个List进行数据操作
                    List<List> resDate = AverageAssignUtil.averageAssign(platformBillImport);
                    int i = 1;
                    for (List<PlatformBill> importDate : resDate) {

                        log.info("批量第"+ i +"次导入请求size：{}", importDate.size());
                        baseResponse = iPlatformBillService.insertList(importDate);
                        log.info("批量第"+ i +"次导入返回：{}", JsonUtil.objectToJson(baseResponse));
                        i++;
                    }

                } else {
                    baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
                    baseResponse.setMessage("导入表格中不存在信息!");
                    //return baseResponse;
                }
            } catch (Exception e) {
                log.error("批量导入失败: {}", e);
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
                baseResponse.setMessage("批量导入失败");
//                baseResponse.setDataInfo(e);
                //throw new RuntimeException(e);
            }
        }
        log.info("平台订单处理完毕");
        return baseResponse;
    }


}
