package cn.vpclub.order.consumer.serviceProvider;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.model.request.AlipayOrderBillPageParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 支付宝订单账单 服务类
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
public interface IAlipayOrderBillService extends IService<AlipayOrderBill> {
    public BaseResponse add(AlipayOrderBill model);

    public BaseResponse delete(AlipayOrderBill model);

    public BaseResponse deleteAll(AlipayOrderBill model);

    public BaseResponse update(AlipayOrderBill model);

    public BaseResponse query(AlipayOrderBill model);

    public Page page(AlipayOrderBillPageParam pageParam);

    //批量插入
    public BaseResponse insertList(List<AlipayOrderBill> pageParam);
}
