package cn.vpclub.order.consumer.serviceProvider.impl;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.ResponseConvert;
import cn.vpclub.order.consumer.entityProvider.AlipayOrderBill;
import cn.vpclub.order.consumer.mapper.AlipayOrderBillMapper;
import cn.vpclub.order.consumer.model.request.AlipayOrderBillPageParam;
import cn.vpclub.order.consumer.serviceProvider.IAlipayOrderBillService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

;

/**
 * <p>
 * 支付宝订单账单 服务实现类
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
@Service
public class AlipayOrderBillServiceImpl extends ServiceImpl<AlipayOrderBillMapper, AlipayOrderBill> implements IAlipayOrderBillService {
    public AlipayOrderBillServiceImpl() {
        super();
    }

    public AlipayOrderBillServiceImpl(AlipayOrderBillMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 添加方法
     *
     * @return
     */
    @Override
    public BaseResponse add(AlipayOrderBill model) {
        boolean back = this.insert(model);
        BaseResponse baseResponse = ResponseConvert.convert(back);
        return baseResponse;
    }

    /**
     * 删除
     *
     * @return
     */
    @Override
    public BaseResponse delete(AlipayOrderBill model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            boolean back = this.deleteById(model.getId());
            baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse deleteAll(AlipayOrderBill model) {
        BaseResponse response = new BaseResponse();
        Integer back = baseMapper.deletedAll();
        response = ResponseConvert.convert(back > 0);

        return response;
    }

    /**
     * 修改方法
     *
     * @return
     */
    @Override
    public BaseResponse update(AlipayOrderBill model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            boolean back = this.updateById(model);
            baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
    }

    /**
     * 单个查询
     *
     * @return
     */
    @Override
    public BaseResponse query(AlipayOrderBill model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            AlipayOrderBill data = this.selectById(model.getId());
            if (null != data) {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
                baseResponse.setDataInfo(data);
            } else {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
            }
        }
        return baseResponse;
    }

    /**
     * 条件查询分页列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public Page page(AlipayOrderBillPageParam pageParam) {
        Page<AlipayOrderBill> page = new Page<AlipayOrderBill>();

        Page<AlipayOrderBill> pageResponse = null;
        //封装条件
        EntityWrapper<AlipayOrderBill> ew = new EntityWrapper<AlipayOrderBill>();

        //针对分页判断
        if (null != pageParam && null != pageParam.getPageNumber() && null != pageParam.getPageSize()) {
            page.setCurrent(pageParam.getPageNumber());
            page.setSize(pageParam.getPageSize());
            pageResponse = this.selectPage(page, ew);
        } else {
            List<AlipayOrderBill> selectList = this.selectList(ew);
            pageResponse = new Page<>();
            pageResponse.setRecords(selectList);
        }
        //记录数
        pageResponse.setTotal(this.selectCount(ew));
        return pageResponse;
    }

    @Override
    public BaseResponse insertList(List<AlipayOrderBill> pageParam) {
        BaseResponse response = new BaseResponse();
        if (CollectionUtils.isEmpty(pageParam)) {
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {

            Integer back = baseMapper.insertList(pageParam);
            response = ResponseConvert.convert(back > 0);
        }
        return response;
    }
}
