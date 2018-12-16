package cn.vpclub.order.consumer.model.request;

import cn.vpclub.moses.core.model.request.PageBaseSearchParam;
import cn.vpclub.order.consumer.entityProvider.MerchantAlipayProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 商户支付宝表 分页查询请求包装类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@Data
@ApiModel("商户支付宝表-分页查询请求参数")
public class MerchantAlipayPageParam extends PageBaseSearchParam{
    private List<MerchantAlipayProvider> merchantAlipayProviderList;
}
