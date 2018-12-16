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

import cn.vpclub.order.consumer.entity.PlatformOrderDetails;
import cn.vpclub.order.consumer.model.request.PlatformOrderDetailsPageParam;
import cn.vpclub.order.consumer.service.PlatformOrderDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 平台订单明细表 前端控制器
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/platformOrderDetails")
@Api(value = "平台订单明细表",description = "平台订单明细表RESTful接口清单")
public class PlatformOrderDetailsController extends AbstractController {


    private PlatformOrderDetailsService platformOrderDetailsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台订单明细表添加方法-add method")
    public BaseResponse add(@RequestBody PlatformOrderDetails vo) {
        return platformOrderDetailsService.add(vo);
        }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台订单明细表修改方法-update method")
    public BaseResponse update(@RequestBody PlatformOrderDetails vo) {
        return platformOrderDetailsService.update(vo);
        }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台订单明细表查询方法-query method")
    public BaseResponse query(@RequestBody PlatformOrderDetails vo) {
        return platformOrderDetailsService.query(vo);
        }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台订单明细表删除方法-delete method")
    public BaseResponse delete(@RequestBody PlatformOrderDetails vo) {
        return platformOrderDetailsService.delete(vo);
        }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("平台订单明细表条件查询-page method")
    public PageResponse page(@RequestBody PlatformOrderDetailsPageParam vo) {
        return platformOrderDetailsService.page(vo);
    }

    /**
     * 批量导入更新
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse importExcel(@RequestParam("fileData") MultipartFile file) {
        return platformOrderDetailsService.importExcel(file);
    }

    }
