package cn.wangqiqi.barkserver.constants;

public interface HttpStatus {
    /**
     * 操作成功
     */
    int SUCCESS = 0;

    /**
     * token过期
     */
    int TOKEN_EXPIRED = 401;

    int ERROR = -1;
}
