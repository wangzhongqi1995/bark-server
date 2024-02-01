package cn.wangqiqi.barkserver.httpClients;

import cn.wangqiqi.barkserver.constants.CommonConstant;
import com.dtflys.forest.annotation.Get;
import org.springframework.stereotype.Component;

@Component
public interface BarkClient {
    @Get(CommonConstant.BARK_ICON_TEMPLATE)
    String sendBarkIconRequest(String title, String content, String iconUrl);
}
