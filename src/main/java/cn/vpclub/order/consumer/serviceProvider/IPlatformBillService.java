package cn.vpclub.order.consumer.serviceProvider;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import cn.vpclub.order.consumer.model.request.PlatformBillPageParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 平台账单 服务类
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
public interface IPlatformBillService extends IService<PlatformBill> {
    public BaseResponse add(PlatformBill model);

    public BaseResponse delete(PlatformBill model);

    public BaseResponse deleteAll(PlatformBill model);

    public BaseResponse update(PlatformBill model);

    public BaseResponse query(PlatformBill model);

    public Page page(PlatformBillPageParam pageParam);

    //批量插入
    public BaseResponse insertList(List<PlatformBill> pageParam);
}
