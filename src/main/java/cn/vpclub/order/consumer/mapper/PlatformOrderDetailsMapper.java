package cn.vpclub.order.consumer.mapper;

import cn.vpclub.order.consumer.entityProvider.PlatformOrderDetailsProvider;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 平台订单明细表 Mapper 接口
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
public interface PlatformOrderDetailsMapper extends BaseMapper<PlatformOrderDetailsProvider> {
    //批量插入
    Integer insertList(List<PlatformOrderDetailsProvider> request);

}