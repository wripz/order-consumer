package cn.vpclub.order.consumer.entityProvider;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 平台订单明细表
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@TableName("platform_order_details")
public class PlatformOrderDetailsProvider extends Model<PlatformOrderDetailsProvider> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	private Long id;
    /**
     * 下游订单号
     */
	@TableField("downstream_order_no")
	private String downstreamOrderNo;
    /**
     * 系统订单号
     */
	@TableField("system_order_no")
	private String systemOrderNo;
    /**
     * 商户编号
     */
	@TableField("merchant_code")
	private String merchantCode;
    /**
     * 交易金额
     */
	@TableField("transaction_amount")
	private BigDecimal transactionAmount;
    /**
     * 供码成本
     */
	private BigDecimal cost;
    /**
     * 平台入账
     */
	@TableField("platform_income")
	private BigDecimal platformIncome;
    /**
     * 商户回款
     */
	@TableField("merchant_returned_money")
	private BigDecimal merchantReturnedMoney;
    /**
     * 一级分润
     */
	@TableField("first_share_profit")
	private BigDecimal firstShareProfit;
    /**
     * 提交时间
     */
	@TableField("submit_time")
	private Date submitTime;
    /**
     * 成功时间
     */
	@TableField("succeed_time")
	private Date succeedTime;
    /**
     * 通道商户号
     */
	@TableField("channel_merchant_number")
	private String channelMerchantNumber;
    /**
     * 通道id
     */
	@TableField("channel_id")
	private String channelId;
    /**
     * 通道名称
     */
	@TableField("channel_name")
	private String channelName;
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

	public String getDownstreamOrderNo() {
		return downstreamOrderNo;
	}

	public void setDownstreamOrderNo(String downstreamOrderNo) {
		this.downstreamOrderNo = downstreamOrderNo;
	}

	public String getSystemOrderNo() {
		return systemOrderNo;
	}

	public void setSystemOrderNo(String systemOrderNo) {
		this.systemOrderNo = systemOrderNo;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPlatformIncome() {
		return platformIncome;
	}

	public void setPlatformIncome(BigDecimal platformIncome) {
		this.platformIncome = platformIncome;
	}

	public BigDecimal getMerchantReturnedMoney() {
		return merchantReturnedMoney;
	}

	public void setMerchantReturnedMoney(BigDecimal merchantReturnedMoney) {
		this.merchantReturnedMoney = merchantReturnedMoney;
	}

	public BigDecimal getFirstShareProfit() {
		return firstShareProfit;
	}

	public void setFirstShareProfit(BigDecimal firstShareProfit) {
		this.firstShareProfit = firstShareProfit;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getSucceedTime() {
		return succeedTime;
	}

	public void setSucceedTime(Date succeedTime) {
		this.succeedTime = succeedTime;
	}

	public String getChannelMerchantNumber() {
		return channelMerchantNumber;
	}

	public void setChannelMerchantNumber(String channelMerchantNumber) {
		this.channelMerchantNumber = channelMerchantNumber;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
