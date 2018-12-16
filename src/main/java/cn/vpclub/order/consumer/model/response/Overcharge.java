package cn.vpclub.order.consumer.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Overcharge {
    /**
     * 系统订单号
     */
    private String systemOrderNo;
    /**
     * 平台回款
     */
    private BigDecimal platformReceivable;
    /**
     * 收入金额
     */
    private BigDecimal income;
    /**
     * 差额
     */
    private String difference;
    /**
     * 业务类型
     */
    private String businessType;
}
