package cn.vpclub.order.consumer.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AmountNoDifference {
    /**
     * 系统订单号
     */
    private String systemOrderNo;
    /**
     * 平台回款
     */
    private BigDecimal platformReceivable;
    /**
     * 成功时间
     */
    private Date succeedTime;
}
