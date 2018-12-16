package cn.vpclub.order.consumer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.web.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import cn.vpclub.order.consumer.entity.MerchantAlipay;
import cn.vpclub.order.consumer.model.request.MerchantAlipayPageParam;
import cn.vpclub.order.consumer.service.MerchantAlipayService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 商户支付宝表 前端控制器
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/merchantAlipay")
@Api(value = "商户支付宝表", description = "商户支付宝表RESTful接口清单")
public class MerchantAlipayController extends AbstractController {


    private MerchantAlipayService merchantAlipayService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("商户支付宝表添加方法-add method")
    public BaseResponse add(@RequestBody MerchantAlipay vo) {
        return merchantAlipayService.add(vo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("商户支付宝表修改方法-update method")
    public BaseResponse update(@RequestBody MerchantAlipay vo) {
        return merchantAlipayService.update(vo);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("商户支付宝表查询方法-query method")
    public BaseResponse query(@RequestBody MerchantAlipay vo) {
        return merchantAlipayService.query(vo);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("商户支付宝表删除方法-delete method")
    public BaseResponse delete(@RequestBody MerchantAlipay vo) {
        return merchantAlipayService.delete(vo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("商户支付宝表条件查询-page method")
    public PageResponse page(@RequestBody MerchantAlipayPageParam vo) {
        return merchantAlipayService.page(vo);
    }


    /**
     * 批量导入更新
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse importExcel(@RequestParam("fileData") MultipartFile file) throws IOException {
        return merchantAlipayService.importExcel(file);
    }

}
