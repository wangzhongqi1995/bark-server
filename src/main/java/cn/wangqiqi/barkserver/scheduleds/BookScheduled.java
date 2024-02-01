package cn.wangqiqi.barkserver.scheduleds;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wangqiqi.barkserver.constants.CommonConstant;
import cn.wangqiqi.barkserver.domain.BookRes;
import cn.wangqiqi.barkserver.domain.BookResult;
import cn.wangqiqi.barkserver.httpClients.BarkClient;
import cn.wangqiqi.barkserver.httpClients.BookClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookScheduled {
    @Resource
    private BookClient bookClient;
    @Resource
    private BarkClient barkClient;

    @Scheduled(cron = "0 0 9-23/2 * * *")
    public void test() {
        // 从纵横查询接口
        BookRes bookRes = bookClient.getLastSimpleChapterInfo(CommonConstant.JIANLAI_BOOK_ID);
        if (ObjectUtil.notEqual(bookRes.getCode(), CommonConstant.ZONGHENG_RES_SUCCESS_CODE)) {
            log.error("纵横接口返回异常，错误信息为:{}", bookRes.getMessage());
            return;
        }

        if (CollUtil.isEmpty(bookRes.getResult())) {
            log.error("纵横接口最新章节为空，错误信息为:{}", bookRes.getMessage());
            return;
        }

        BookResult bookResult = bookRes.getResult().get(0);

        barkClient.sendBarkIconRequest(CommonConstant.JIANLAI_TITLE, StrUtil.format("最新章节为：{}\n更新时间：{}\n字数：{}", bookResult.getChapterName(), bookResult.getCreateTime(), bookResult.getTotalWords()), CommonConstant.JIANLAI_ICON_URL);
    }
}
