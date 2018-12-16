package cn.vpclub.order.consumer.model.response;

import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExportExcelSheets {
    //支付宝有平台无
    private List<AlipayOrderBill> alipayOrderBillHave;
    //平台有支付宝无
    private List<PlatformBill> platformBillHave;
    //金额无差别
    private  List<AmountNoDifference> amountNoDifferenceList;
    //多收
    private List<Overcharge> overchargeList;
    //少收
    private List<LessMoney> lessMoneyList;
}
