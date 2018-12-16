package cn.vpclub.order.consumer.serviceProvider;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.order.consumer.entityProvider.MerchantAlipayProvider;
import cn.vpclub.order.consumer.model.request.MerchantAlipayPageParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商户支付宝表 服务类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface IMerchantAlipayService extends IService<MerchantAlipayProvider> {
    public BaseResponse add(MerchantAlipayProvider model);

    public BaseResponse delete(MerchantAlipayProvider model);

    public BaseResponse update(MerchantAlipayProvider model);

    public BaseResponse query(MerchantAlipayProvider model);

    public Page page(MerchantAlipayPageParam pageParam);

    //批量插入
    public BaseResponse insertList(List<MerchantAlipayProvider> pageParam);
}
