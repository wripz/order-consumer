package cn.vpclub.order.consumer.serviceProvider;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.order.consumer.entityProvider.PlatformAlipayProvider;
import cn.vpclub.order.consumer.model.request.PlatformAlipayPageParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 平台支付宝表 服务类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface IPlatformAlipayService extends IService<PlatformAlipayProvider> {
    public BaseResponse add(PlatformAlipayProvider model);

    public BaseResponse delete(PlatformAlipayProvider model);

    public BaseResponse update(PlatformAlipayProvider model);

    public BaseResponse query(PlatformAlipayProvider model);

    public Page page(PlatformAlipayPageParam pageParam);


    //批量插入
    public BaseResponse insertList(List<PlatformAlipayProvider> pageParam);
}
