package cn.vpclub.order.consumer.web;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.order.consumer.service.AccountCheckingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 支付宝订单账单和平台账单对账 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-12-07
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/accountChecking")
public class AccountCheckingController {

    @Autowired
    private AccountCheckingService accountCheckingService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        accountCheckingService.export(httpServletRequest,httpServletResponse);
    }
    @RequestMapping(value = "/exportMore", method = RequestMethod.GET)
    @ResponseBody
    public void exportMore(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        accountCheckingService.exportMore(httpServletRequest,httpServletResponse);
    }
    @RequestMapping(value = "/exportLess", method = RequestMethod.GET)
    @ResponseBody
    public void exportLess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        accountCheckingService.exportLess(httpServletRequest,httpServletResponse);
    }

    @RequestMapping(value = "/exportalipayOrderBillHave", method = RequestMethod.GET)
    @ResponseBody
    public void exportalipayOrderBillHave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        accountCheckingService.exportalipayOrderBillHave(httpServletRequest,httpServletResponse);
    }
    @RequestMapping(value = "/exportplatformBillHave", method = RequestMethod.GET)
    @ResponseBody
    public void exportplatformBillHave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        accountCheckingService.exportplatformBillHave(httpServletRequest,httpServletResponse);
    }

    @RequestMapping(value = "/exportSheets", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse exportSheets() {
        return accountCheckingService.exportSheets();
    }

}
