package cn.vpclub.order.consumer.web;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.web.controller.AbstractController;
import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.model.request.AlipayOrderBillPageParam;
import cn.vpclub.order.consumer.service.AlipayOrderBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 支付宝订单账单 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-07
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/alipayOrderBill")
@Api(value = "支付宝订单账单", description = "支付宝订单账单RESTful接口清单")
public class AlipayOrderBillController extends AbstractController {


    private AlipayOrderBillService alipayOrderBillService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单添加方法-add method")
    public BaseResponse add(@RequestBody AlipayOrderBill vo) {
        return alipayOrderBillService.add(vo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单修改方法-update method")
    public BaseResponse update(@RequestBody AlipayOrderBill vo) {
        return alipayOrderBillService.update(vo);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单查询方法-query method")
    public BaseResponse query(@RequestBody AlipayOrderBill vo) {
        return alipayOrderBillService.query(vo);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单删除方法-delete method")
    public BaseResponse delete(@RequestBody AlipayOrderBill vo) {
        return alipayOrderBillService.delete(vo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单条件查询-page method")
    public PageResponse page(@RequestBody AlipayOrderBillPageParam vo) {
        return alipayOrderBillService.page(vo);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付宝订单账单删除所有方法-deleteAll method")
    public BaseResponse deleteAll(@RequestBody AlipayOrderBill vo) {
        return alipayOrderBillService.deleteAll(vo);
    }


    /**
     * 批量导入
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse importExcel(@RequestParam("fileData") MultipartFile file) throws IOException {
        return alipayOrderBillService.importExcel(file);
    }

}
