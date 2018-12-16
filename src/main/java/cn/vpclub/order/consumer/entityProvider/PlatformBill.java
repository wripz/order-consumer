package cn.vpclub.order.consumer.entityProvider;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 平台账单
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
@TableName("platform_bill")
public class PlatformBill extends Model<PlatformBill> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 系统订单号
     */
	@TableField("system_order_no")
	private String systemOrderNo;
    /**
     * 平台回款
     */
	@TableField("platform_receivable")
	private BigDecimal platformReceivable;
    /**
     * 成功时间
     */
	@TableField("succeed_time")
	private Date succeedTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemOrderNo() {
		return systemOrderNo;
	}

	public void setSystemOrderNo(String systemOrderNo) {
		this.systemOrderNo = systemOrderNo;
	}

	public BigDecimal getPlatformReceivable() {
		return platformReceivable;
	}

	public void setPlatformReceivable(BigDecimal platformReceivable) {
		this.platformReceivable = platformReceivable;
	}

	public Date getSucceedTime() {
		return succeedTime;
	}

	public void setSucceedTime(Date succeedTime) {
		this.succeedTime = succeedTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
