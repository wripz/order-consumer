package cn.vpclub.order.consumer.mapper;

import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 平台账单 Mapper 接口
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
public interface PlatformBillMapper extends BaseMapper<PlatformBill> {
    //批量插入
    Integer insertList(List<PlatformBill> request);

    Integer deletedAll();

}