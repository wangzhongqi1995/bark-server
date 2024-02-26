package cn.wangqiqi.barkserver.service;

import cn.wangqiqi.barkserver.domain.core.PageQuery;
import cn.wangqiqi.barkserver.domain.core.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.BarkInfoQueryParamDTO;
import cn.wangqiqi.barkserver.entity.BarkInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBarkInfoService extends IService<BarkInfo> {
    TableDataInfo<BarkInfo> page(PageQuery pageQuery, BarkInfoQueryParamDTO barkInfoQueryParamDTO);
}
