package cn.wangqiqi.barkserver.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.wangqiqi.barkserver.constants.CommonConstant;
import cn.wangqiqi.barkserver.domain.R;
import cn.wangqiqi.barkserver.domain.dto.LoginDTO;
import cn.wangqiqi.barkserver.domain.vo.LoginResVO;
import cn.wangqiqi.barkserver.domain.vo.UserInfoVO;
import cn.wangqiqi.barkserver.entity.Student;
import cn.wangqiqi.barkserver.mapper.StudentMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/users")
public class LoginController {
    @Resource
    private StudentMapper studentMapper;

    @PostMapping("/login")
    public R<LoginResVO> login(@RequestBody LoginDTO loginDTO) {
        if (!StrUtil.equals(loginDTO.getPassword(), CommonConstant.DEFAULT_PASSWORD) || !StrUtil.equals(loginDTO.getUsername(), CommonConstant.DEFAULT_USERNAME)) {
            return R.fail("用户名密码异常！");
        }
        return R.ok(LoginResVO.builder().token("token-admin").build());
    }

    @GetMapping("/info")
    public R<UserInfoVO> info() {
        return R.ok(UserInfoVO.builder().username(CommonConstant.DEFAULT_USERNAME).roles(CollUtil.newArrayList(CommonConstant.DEFAULT_USERNAME)).build());
    }

    @GetMapping("test")
    public R<List<Student>> test() {
        return R.ok(studentMapper.selectList(Wrappers.lambdaQuery()));
    }
}
