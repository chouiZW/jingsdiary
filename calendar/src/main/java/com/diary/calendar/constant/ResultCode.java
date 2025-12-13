package com.diary.calendar.constant;

public class ResultCode {

    // ========== HTTP状态码分类 ==========

    /**
     * 2xx 成功状态码
     */
    public static final int SUCCESS = 200;           // 请求成功
    public static final int CREATED = 201;          // 创建成功
    public static final int ACCEPTED = 202;         // 请求已接受
    public static final int NO_CONTENT = 204;       // 无内容

    /**
     * 4xx 客户端错误状态码
     */
    public static final int BAD_REQUEST = 400;      // 请求参数错误
    public static final int UNAUTHORIZED = 401;     // 未授权
    public static final int FORBIDDEN = 403;        // 禁止访问
    public static final int NOT_FOUND = 404;        // 资源不存在
    public static final int METHOD_NOT_ALLOWED = 405; // 方法不允许
    public static final int REQUEST_TIMEOUT = 408;  // 请求超时
    public static final int CONFLICT = 409;         // 冲突
    public static final int PAYLOAD_TOO_LARGE = 413; // 请求实体过大
    public static final int UNSUPPORTED_MEDIA_TYPE = 415; // 不支持的媒体类型
    public static final int TOO_MANY_REQUESTS = 429; // 请求过于频繁

    /**
     * 5xx 服务器错误状态码
     */
    public static final int INTERNAL_ERROR = 500;   // 内部服务器错误
    public static final int NOT_IMPLEMENTED = 501;  // 未实现
    public static final int BAD_GATEWAY = 502;      // 网关错误
    public static final int SERVICE_UNAVAILABLE = 503; // 服务不可用
    public static final int GATEWAY_TIMEOUT = 504;  // 网关超时

    // ========== 业务状态码分类 ==========

    /**
     * 通用业务状态码
     */
    public static final int BUSINESS_SUCCESS = 200;    // 业务操作成功
    public static final int PARAM_ERROR = 400;         // 参数错误
    public static final int VALIDATION_ERROR = 400;    // 数据校验失败
    public static final int AUTH_FAILED = 401;         // 认证失败
    public static final int PERMISSION_DENIED = 403;   // 权限不足
    public static final int RESOURCE_NOT_FOUND = 404;  // 资源不存在
    public static final int BUSINESS_ERROR = 400;      // 业务逻辑错误
    public static final int SYSTEM_ERROR = 500;        // 系统错误

    /**
     * 用户相关业务状态码
     */
    public static final int USER_NOT_EXIST = 1001;     // 用户不存在
    public static final int USER_ALREADY_EXISTS = 1002; // 用户已存在
    public static final int USER_PASSWORD_ERROR = 1003; // 密码错误
    public static final int USER_ACCOUNT_LOCKED = 1004; // 账户锁定
    public static final int USER_ACCOUNT_EXPIRED = 1005; // 账户过期
    public static final int USER_CREDENTIALS_EXPIRED = 1006; // 凭证过期

    /**
     * 订单相关业务状态码
     */
    public static final int ORDER_NOT_FOUND = 2001;    // 订单不存在
    public static final int ORDER_STATUS_ERROR = 2002; // 订单状态错误
    public static final int ORDER_PAID = 2003;         // 订单已支付
    public static final int ORDER_CANCELLED = 2004;    // 订单已取消

    /**
     * 商品相关业务状态码
     */
    public static final int PRODUCT_NOT_FOUND = 3001;  // 商品不存在
    public static final int PRODUCT_OUT_OF_STOCK = 3002; // 商品缺货
    public static final int PRODUCT_OFF_SALE = 3003;   // 商品下架

    /**
     * 数据库相关状态码
     */
    public static final int DATABASE_ERROR = 6000;     // 数据库错误
    public static final int DUPLICATE_KEY = 6001;      // 主键冲突
    public static final int FOREIGN_KEY_VIOLATION = 6002; // 外键约束违反

    /**
     * 文件相关状态码
     */
    public static final int FILE_NOT_FOUND = 7001;     // 文件不存在
    public static final int FILE_SIZE_EXCEEDED = 7002; // 文件大小超限
    public static final int FILE_TYPE_UNSUPPORTED = 7003; // 文件类型不支持
    public static final int FILE_UPLOAD_FAILED = 7004; // 文件上传失败

    /**
     * 缓存相关状态码
     */
    public static final int CACHE_ERROR = 8001;        // 缓存错误
    public static final int CACHE_MISS = 8002;         // 缓存未命中

    /**
     * 第三方服务相关状态码
     */
    public static final int EXTERNAL_SERVICE_ERROR = 9001; // 外部服务错误
    public static final int EXTERNAL_API_RATE_LIMIT = 9002; // 外部API限流
    public static final int EXTERNAL_AUTH_FAILED = 9003; // 外部认证失败
}
