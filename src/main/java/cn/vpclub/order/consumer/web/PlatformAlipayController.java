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

import cn.vpclub.order.consumer.entity.PlatformAlipay;
import cn.vpclub.order.consumer.model.request.PlatformAlipayPageParam;
import cn.vpclub.order.consumer.service.PlatformAlipayService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 平台支付宝表 前端控制器
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/platformAlipay")
@Api(value = "平台支付宝表",description = "平台支付宝表RESTful接口清单")
public class PlatformAlipayController extends AbstractController {


    private PlatformAlipayService platformAlipayService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台支付宝表添加方法-add method")
    public BaseResponse add(@RequestBody PlatformAlipay vo) {
        return platformAlipayService.add(vo);
        }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台支付宝表修改方法-update method")
    public BaseResponse update(@RequestBody PlatformAlipay vo) {
        return platformAlipayService.update(vo);
        }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台支付宝表查询方法-query method")
    public BaseResponse query(@RequestBody PlatformAlipay vo) {
        return platformAlipayService.query(vo);
        }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台支付宝表删除方法-delete method")
    public BaseResponse delete(@RequestBody PlatformAlipay vo) {
        return platformAlipayService.delete(vo);
        }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台支付宝表条件查询-page method")
    public PageResponse page(@RequestBody PlatformAlipayPageParam vo) {
        return platformAlipayService.page(vo);
    }


    /**
     * 批量导入更新
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse importExcel(@RequestParam("fileData") MultipartFile file) {
        return platformAlipayService.importExcel(file);
    }


}
