package cn.vpclub.order.consumer.mapper;

import cn.vpclub.order.consumer.entityProvider.PlatformAlipayProvider;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 平台支付宝表 Mapper 接口
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface PlatformAlipayMapper extends BaseMapper<PlatformAlipayProvider> {
    //批量插入
    Integer insertList(List<PlatformAlipayProvider> request);
}