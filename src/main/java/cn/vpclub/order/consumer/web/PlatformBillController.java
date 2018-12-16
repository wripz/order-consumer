package cn.vpclub.order.consumer.web;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.web.controller.AbstractController;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import cn.vpclub.order.consumer.model.request.PlatformBillPageParam;
import cn.vpclub.order.consumer.service.PlatformBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 平台账单 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-07
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/platformBill")
@Api(value = "平台账单",description = "平台账单RESTful接口清单")
public class PlatformBillController extends AbstractController {


    private PlatformBillService platformBillService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单添加方法-add method")
    public BaseResponse add(@RequestBody PlatformBill vo) {
        return platformBillService.add(vo);
        }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单修改方法-update method")
    public BaseResponse update(@RequestBody PlatformBill vo) {
        return platformBillService.update(vo);
        }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单查询方法-query method")
    public BaseResponse query(@RequestBody PlatformBill vo) {
        return platformBillService.query(vo);
        }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单删除方法-delete method")
    public BaseResponse delete(@RequestBody PlatformBill vo) {
        return platformBillService.delete(vo);
        }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单删除所有方法-delete method")
    public BaseResponse deleteAll(@RequestBody PlatformBill vo) {
        return platformBillService.deleteAll(vo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台账单条件查询-page method")
    public PageResponse page(@RequestBody PlatformBillPageParam vo) {
        return platformBillService.page(vo);
    }

    /**
     * 批量导入
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse importExcel(@RequestParam("fileData") MultipartFile file) {
        return platformBillService.importExcel(file);
    }

}
