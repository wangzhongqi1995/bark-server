package cn.wangqiqi.barkserver.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wangqiqi.barkserver.domain.core.PageQuery;
import cn.wangqiqi.barkserver.domain.core.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.BarkInfoQueryParamDTO;
import cn.wangqiqi.barkserver.entity.BarkInfo;
import cn.wangqiqi.barkserver.mapper.BarkInfoMapper;
import cn.wangqiqi.barkserver.service.IBarkInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BarkInfoServiceImpl extends ServiceImpl<BarkInfoMapper, BarkInfo> implements IBarkInfoService {

    @Override
    public TableDataInfo<BarkInfo> page(PageQuery pageQuery, BarkInfoQueryParamDTO barkInfoQueryParamDTO) {
        return TableDataInfo.build(page(pageQuery.build(), Wrappers.<BarkInfo>lambdaQuery()
                .like(StrUtil.isNotBlank(barkInfoQueryParamDTO.getUrl()), BarkInfo::getUrl, barkInfoQueryParamDTO.getUrl())
                .like(StrUtil.isNotBlank(barkInfoQueryParamDTO.getRemark()), BarkInfo::getRemark, barkInfoQueryParamDTO.getRemark())));
    }
}
