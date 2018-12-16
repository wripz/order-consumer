package cn.vpclub.order.consumer.entityProvider;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商户支付宝表
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@TableName("merchant_alipay")
public class MerchantAlipayProvider extends Model<MerchantAlipayProvider> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	private Long id;
    /**
     * 账务流水号
     */
	@TableField("account_number")
	private String accountNumber;
    /**
     * 业务流水号
     */
	@TableField("service_serial_number")
	private String serviceSerialNumber;
    /**
     * 商户订单号
     */
	@TableField("merchant_order_no")
	private String merchantOrderNo;
    /**
     * 商品名称
     */
	@TableField("commodity_name")
	private String commodityName;
    /**
     * 发生时间
     */
	@TableField("occurrence_time")
	private Date occurrenceTime;
    /**
     * 对方账号
     */
	@TableField("reciprocal_account_number")
	private String reciprocalAccountNumber;
    /**
     * 收入金额（+元）
     */
	private BigDecimal income;
    /**
     * 支出金额（-元）
     */
	@TableField("amount_paid")
	private BigDecimal amountPaid;
    /**
     * 账户余额（元）
     */
	private BigDecimal balance;
    /**
     * 交易渠道
     */
	@TableField("transaction_channel")
	private String transactionChannel;
    /**
     * 业务类型
     */
	@TableField("business_type")
	private String businessType;
    /**
     * 备注
     */
	private String remark;
    /**
     * 导入表名称
     */
	@TableField("import_file_name")
	private String importFileName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getServiceSerialNumber() {
		return serviceSerialNumber;
	}

	public void setServiceSerialNumber(String serviceSerialNumber) {
		this.serviceSerialNumber = serviceSerialNumber;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public String getReciprocalAccountNumber() {
		return reciprocalAccountNumber;
	}

	public void setReciprocalAccountNumber(String reciprocalAccountNumber) {
		this.reciprocalAccountNumber = reciprocalAccountNumber;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTransactionChannel() {
		return transactionChannel;
	}

	public void setTransactionChannel(String transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImportFileName() {
		return importFileName;
	}

	public void setImportFileName(String importFileName) {
		this.importFileName = importFileName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
