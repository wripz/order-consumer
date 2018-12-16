package cn.vpclub.order.consumer.entityProvider;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付宝订单账单
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
@TableName("alipay_order_bill")
public class AlipayOrderBill extends Model<AlipayOrderBill> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 商户订单号
     */
	@TableField("merchant_order_no")
	private String merchantOrderNo;
    /**
     * 发生时间
     */
	@TableField("occurrence_time")
	private Date occurrenceTime;
    /**
     * 收入金额
     */
	private BigDecimal income;
    /**
     * 业务类型
     */
	@TableField("business_type")
	private String businessType;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
