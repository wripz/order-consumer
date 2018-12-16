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
 * 平台订单明细表
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */

@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("平台订单明细表-数据载体")
public class PlatformOrderDetails extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @NotNull(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
	private Long id;
    /**
     * 下游订单号
     */
    @ApiModelProperty(value = "下游订单号")
	private String downstreamOrderNo;
    /**
     * 系统订单号
     */
    @ApiModelProperty(value = "系统订单号")
	private String systemOrderNo;
    /**
     * 商户编号
     */
    @ApiModelProperty(value = "商户编号")
	private String merchantCode;
    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
	private BigDecimal transactionAmount;
    /**
     * 供码成本
     */
    @ApiModelProperty(value = "供码成本")
	private BigDecimal cost;
    /**
     * 平台入账
     */
    @ApiModelProperty(value = "平台入账")
	private BigDecimal platformIncome;
    /**
     * 商户回款
     */
    @ApiModelProperty(value = "商户回款")
	private BigDecimal merchantReturnedMoney;
    /**
     * 一级分润
     */
    @ApiModelProperty(value = "一级分润")
	private BigDecimal firstShareProfit;
    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
	private Date submitTime;
    /**
     * 成功时间
     */
    @ApiModelProperty(value = "成功时间")
	private Date succeedTime;
    /**
     * 通道商户号
     */
    @ApiModelProperty(value = "通道商户号")
	private String channelMerchantNumber;
    /**
     * 通道id
     */
    @ApiModelProperty(value = "通道id")
	private String channelId;
    /**
     * 通道名称
     */
    @ApiModelProperty(value = "通道名称")
	private String channelName;
    /**
     * 导入表名称
     */
    @ApiModelProperty(value = "导入表名称")
	private String importFileName;


}
