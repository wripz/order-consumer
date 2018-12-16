package cn.vpclub.order.consumer.mapper;

import cn.vpclub.order.consumer.entityProvider.MerchantAlipayProvider;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 商户支付宝表 Mapper 接口
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface MerchantAlipayMapper extends BaseMapper<MerchantAlipayProvider> {
    //批量插入
    Integer insertList(List<MerchantAlipayProvider> request);
}