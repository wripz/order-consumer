package cn.vpclub.order.consumer.serviceProvider.impl;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.ResponseConvert;
import cn.vpclub.order.consumer.entityProvider.PlatformAlipayProvider;
import cn.vpclub.order.consumer.mapper.PlatformAlipayMapper;
import cn.vpclub.order.consumer.model.request.PlatformAlipayPageParam;
import cn.vpclub.order.consumer.serviceProvider.IPlatformAlipayService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * <p>
 * 平台支付宝表 服务实现类
 * </p>
 *
 * @author wsj
 * @since 2018-12-03
 */
@Service
public class PlatformAlipayServiceImpl extends ServiceImpl<PlatformAlipayMapper, PlatformAlipayProvider> implements IPlatformAlipayService {
    public PlatformAlipayServiceImpl() {
        super();
     }

    public PlatformAlipayServiceImpl(PlatformAlipayMapper baseMapper) {
        this.baseMapper = baseMapper;
     }
    /**
    * 添加方法
    * @return
    */
    @Override
    public BaseResponse add(PlatformAlipayProvider model) {
        boolean back = this.insert(model);
        BaseResponse baseResponse = ResponseConvert.convert(back);
        return baseResponse;
        }
    /**
    * 删除
    * @return
    */
    @Override
    public BaseResponse delete(PlatformAlipayProvider model) {
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
    public BaseResponse update(PlatformAlipayProvider model) {
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
    public BaseResponse query(PlatformAlipayProvider model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            PlatformAlipayProvider data = this.selectById(model.getId());
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
    public Page page(PlatformAlipayPageParam pageParam) {
        Page<PlatformAlipayProvider> page = new Page<PlatformAlipayProvider>();

        Page<PlatformAlipayProvider> pageResponse = null;
        //封装条件
        EntityWrapper<PlatformAlipayProvider> ew = new EntityWrapper<PlatformAlipayProvider>();

        //针对分页判断
        if (null != pageParam && null != pageParam.getPageNumber() && null != pageParam.getPageSize()) {
        page.setCurrent(pageParam.getPageNumber());
        page.setSize(pageParam.getPageSize());
        pageResponse = this.selectPage(page, ew);
        } else {
        List<PlatformAlipayProvider> selectList = this.selectList(ew);
        pageResponse = new Page<>();
        pageResponse.setRecords(selectList);
        }
        //记录数
        pageResponse.setTotal(this.selectCount(ew));
        return pageResponse;
        }

    @Override
    public BaseResponse insertList(List<PlatformAlipayProvider> pageParam) {
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
