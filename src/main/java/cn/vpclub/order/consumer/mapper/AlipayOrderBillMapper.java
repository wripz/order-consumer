package cn.vpclub.order.consumer.mapper;

import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.entityProvider.MerchantAlipayProvider;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 支付宝订单账单 Mapper 接口
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
public interface AlipayOrderBillMapper extends BaseMapper<AlipayOrderBill> {
    //批量插入
    Integer insertList(List<AlipayOrderBill> request);

    Integer deletedAll();
}