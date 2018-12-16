package cn.vpclub.order.consumer.serviceProvider;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.order.consumer.entityProvider.PlatformOrderDetailsProvider;
import cn.vpclub.order.consumer.model.request.PlatformOrderDetailsPageParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 平台订单明细表 服务类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface IPlatformOrderDetailsService extends IService<PlatformOrderDetailsProvider> {
    public BaseResponse add(PlatformOrderDetailsProvider model);

    public BaseResponse delete(PlatformOrderDetailsProvider model);

    public BaseResponse update(PlatformOrderDetailsProvider model);

    public BaseResponse query(PlatformOrderDetailsProvider model);

    public Page page(PlatformOrderDetailsPageParam pageParam);


    //批量插入
    public BaseResponse insertList(List<PlatformOrderDetailsProvider> pageParam);
}
