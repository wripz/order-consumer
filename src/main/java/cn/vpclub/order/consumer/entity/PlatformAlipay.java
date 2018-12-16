package cn.vpclub.order.consumer.entity;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.annotations.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 平台支付宝表
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */

@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("平台支付宝表-数据载体")
public class PlatformAlipay extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @NotNull(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
	private Long id;
    /**
     * 账务流水号
     */
    @ApiModelProperty(value = "账务流水号")
	private String accountNumber;
    /**
     * 业务流水号
     */
    @ApiModelProperty(value = "业务流水号")
	private String serviceSerialNumber;
    /**
     * 商户订单号
     */
    @ApiModelProperty(value = "商户订单号")
	private String merchantOrderNo;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
	private String commodityName;
    /**
     * 发生时间
     */
    @ApiModelProperty(value = "发生时间")
	private Date occurrenceTime;
    /**
     * 对方账号
     */
    @ApiModelProperty(value = "对方账号")
	private String reciprocalAccountNumber;
    /**
     * 收入金额（+元）
     */
    @ApiModelProperty(value = "收入金额（+元）")
	private BigDecimal income;
    /**
     * 支出金额（-元）
     */
    @ApiModelProperty(value = "支出金额（-元）")
	private BigDecimal amountPaid;
    /**
     * 账户余额（元）
     */
    @ApiModelProperty(value = "账户余额（元）")
	private BigDecimal balance;
    /**
     * 交易渠道
     */
    @ApiModelProperty(value = "交易渠道")
	private String transactionChannel;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
	private String businessType;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
	private String remark;
    /**
     * 导入表名称
     */
    @ApiModelProperty(value = "导入表名称")
	private String importFileName;


}
