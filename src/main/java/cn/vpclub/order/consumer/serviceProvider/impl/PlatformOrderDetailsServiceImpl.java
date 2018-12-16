package cn.vpclub.order.consumer.serviceProvider.impl;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.ResponseConvert;
import cn.vpclub.order.consumer.entity.PlatformOrderDetails;
import cn.vpclub.order.consumer.entityProvider.PlatformOrderDetailsProvider;
import cn.vpclub.order.consumer.mapper.PlatformOrderDetailsMapper;
import cn.vpclub.order.consumer.model.request.PlatformOrderDetailsPageParam;
import cn.vpclub.order.consumer.serviceProvider.IPlatformOrderDetailsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

;import static org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data;

/**
 * <p>
 * 平台订单明细表 服务实现类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@Service
public class PlatformOrderDetailsServiceImpl extends ServiceImpl<PlatformOrderDetailsMapper, PlatformOrderDetailsProvider> implements IPlatformOrderDetailsService {
    public PlatformOrderDetailsServiceImpl() {
        super();
     }

    public PlatformOrderDetailsServiceImpl(PlatformOrderDetailsMapper baseMapper) {
        this.baseMapper = baseMapper;
     }
    /**
    * 添加方法
    * @return
    */
    @Override
    public BaseResponse add(PlatformOrderDetailsProvider model) {
        boolean back = this.insert(model);
        BaseResponse baseResponse = ResponseConvert.convert(back);
        return baseResponse;
        }
    /**
    * 删除
    * @return
    */
    @Override
    public BaseResponse delete(PlatformOrderDetailsProvider model) {
        BaseResponse baseResponse ;
        if (null == model || null == model.getId()) {
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
        boolean back = this.deleteById(model.getId());
        baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
        }
    /**
    * 修改方法
    * @return
    */
    @Override
    public BaseResponse update(PlatformOrderDetailsProvider model) {
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
    public BaseResponse query(PlatformOrderDetailsProvider model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            PlatformOrderDetailsProvider platformOrderDetailsProvider = this.selectById(model.getId());
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
    public Page page(PlatformOrderDetailsPageParam pageParam) {
        Page<PlatformOrderDetailsProvider> page = new Page<PlatformOrderDetailsProvider>();

        Page<PlatformOrderDetailsProvider> pageResponse = null;
        //封装条件
        EntityWrapper<PlatformOrderDetailsProvider> ew = new EntityWrapper<PlatformOrderDetailsProvider>();

        //针对分页判断
        if (null != pageParam && null != pageParam.getPageNumber() && null != pageParam.getPageSize()) {
        page.setCurrent(pageParam.getPageNumber());
        page.setSize(pageParam.getPageSize());
        pageResponse = this.selectPage(page, ew);
        } else {
        List<PlatformOrderDetailsProvider> selectList = this.selectList(ew);
        pageResponse = new Page<>();
        pageResponse.setRecords(selectList);
        }
        //记录数
        pageResponse.setTotal(this.selectCount(ew));
        return pageResponse;
        }

    @Override
    public BaseResponse insertList(List<PlatformOrderDetailsProvider> pageParam) {
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
