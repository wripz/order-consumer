package cn.vpclub.order.consumer.serviceProvider.impl;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.ResponseConvert;
import cn.vpclub.order.consumer.entityProvider.PlatformBill;
import cn.vpclub.order.consumer.mapper.PlatformBillMapper;
import cn.vpclub.order.consumer.model.request.PlatformBillPageParam;
import cn.vpclub.order.consumer.serviceProvider.IPlatformBillService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * <p>
 * 平台账单 服务实现类
 * </p>
 *
 * @author null
 * @since 2018-12-07
 */
@Service
public class PlatformBillServiceImpl extends ServiceImpl<PlatformBillMapper, PlatformBill> implements IPlatformBillService {
    public PlatformBillServiceImpl() {
        super();
     }

    public PlatformBillServiceImpl(PlatformBillMapper baseMapper) {
        this.baseMapper = baseMapper;
     }
    /**
    * 添加方法
    * @return
    */
    @Override
    public BaseResponse add(PlatformBill model) {
        boolean back = this.insert(model);
        BaseResponse baseResponse = ResponseConvert.convert(back);
        return baseResponse;
        }
    /**
    * 删除
    * @return
    */
    @Override
    public BaseResponse delete(PlatformBill model) {
        BaseResponse baseResponse ;
        if (null == model || null == model.getId()) {
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
        boolean back = this.deleteById(model.getId());
        baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
        }

    @Override
    public BaseResponse deleteAll(PlatformBill model) {
        BaseResponse response = new BaseResponse();
        Integer back = baseMapper.deletedAll();
        response = ResponseConvert.convert(back > 0);

        return response;
    }

    /**
    * 修改方法
    * @return
    */
    @Override
    public BaseResponse update(PlatformBill model) {
        BaseResponse baseResponse ;
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
    * @return
    */
    @Override
    public BaseResponse query(PlatformBill model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
        PlatformBill data = this.selectById(model.getId());
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
    * @param pageParam
    * @return
    */
    @Override
    public Page page(PlatformBillPageParam pageParam) {
        Page<PlatformBill> page = new Page<PlatformBill>();

        Page<PlatformBill> pageResponse = null;
        //封装条件
        EntityWrapper<PlatformBill> ew = new EntityWrapper<PlatformBill>();

        //针对分页判断
        if (null != pageParam && null != pageParam.getPageNumber() && null != pageParam.getPageSize()) {
        page.setCurrent(pageParam.getPageNumber());
        page.setSize(pageParam.getPageSize());
        pageResponse = this.selectPage(page, ew);
        } else {
        List<PlatformBill> selectList = this.selectList(ew);
        pageResponse = new Page<>();
        pageResponse.setRecords(selectList);
        }
        //记录数
        pageResponse.setTotal(this.selectCount(ew));
        return pageResponse;
        }

    @Override
    public BaseResponse insertList(List<PlatformBill> pageParam) {
        BaseResponse response = new BaseResponse();
        if (CollectionUtils.isEmpty(pageParam)){
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        }else {
            Integer back = baseMapper.insertList(pageParam);
            response = ResponseConvert.convert(back > 0);
        }
        return response;
    }
}
