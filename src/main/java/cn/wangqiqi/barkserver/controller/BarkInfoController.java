package cn.wangqiqi.barkserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.wangqiqi.barkserver.domain.PageQuery;
import cn.wangqiqi.barkserver.domain.R;
import cn.wangqiqi.barkserver.domain.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.BarkInfoCreateUpdateDTO;
import cn.wangqiqi.barkserver.domain.dto.BarkInfoQueryParamDTO;
import cn.wangqiqi.barkserver.entity.BarkInfo;
import cn.wangqiqi.barkserver.service.IBarkInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RequestMapping("barkInfo")
@RestController
public class BarkInfoController {
    @Resource
    private IBarkInfoService barkInfoService;

    @GetMapping("page")
    public TableDataInfo<BarkInfo> page(PageQuery pageQuery, BarkInfoQueryParamDTO barkInfoQueryParamDTO) {
        return barkInfoService.page(pageQuery, barkInfoQueryParamDTO);
    }

    @DeleteMapping("{id}")
    public R<Void> delete(@PathVariable Integer id) {
        barkInfoService.removeById(id);
        return R.ok();
    }

    @PostMapping("saveOrUpdate")
    public R<Void> saveOrUpdate(@RequestBody BarkInfoCreateUpdateDTO barkInfoCreateUpdateDTO) {
        BarkInfo barkInfo = new BarkInfo();
        BeanUtil.copyProperties(barkInfoCreateUpdateDTO, barkInfo);
        barkInfoService.saveOrUpdate(barkInfo);
        return R.ok();
    }
}
