package com.sqlist.web.result;

/**
 * @author SqList
 * @date 2019/4/9 9:44
 * @descriptio
 **/
public class CodeMsg {

    private int code;
    private String msg;

    /**
     * 通用的错误码
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

    /**
     * 登录模块 5002XX
     */
    public static CodeMsg SESSION_ERROR = new CodeMsg(500201, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500202, "登录密码不能为空");
    public static CodeMsg LOGIN_ERROR = new CodeMsg(500203, "用户名或密码错误");

    /**
     * Task 5003XX
     */
    public static CodeMsg TASK_NAME_REPEAT = new CodeMsg(500301, "任务名称不能重复");
    public static CodeMsg TASK_DETAIL_NULL = new CodeMsg(500302, "任务单元详细信息未填写完全");
    public static CodeMsg TASK_CONNECT_LACK = new CodeMsg(500303, "存在任务单元还未连接");
    public static CodeMsg TASK_INPUT_ONE = new CodeMsg(500304, "输入单元需要存在且只有一个");
    public static CodeMsg TASK_OUTPUT_ZERO = new CodeMsg(500305, "输出单元最少需要一个");
    public static CodeMsg TASK_IS_RUNNING = new CodeMsg(500306, "任务 %s 正在运行无法删除");

    /**
     * File 5004XX
     */
    public static CodeMsg UPLOAD_FILE_EMPET = new CodeMsg(500401, "上传文件空，上传失败");
    public static CodeMsg UPLOAD_FILE_IO_EXCEPTION = new CodeMsg(500402, "上传文件失败");
    public static CodeMsg FILE_NAME_REPEAT = new CodeMsg(500403, "文件名称不能重复");
    public static CodeMsg FILE_NOT_EXIST = new CodeMsg(500404, "下载文件失败，文件不存在");
    public static CodeMsg FILE_IS_USED = new CodeMsg(500405, "文件 %s 被任务使用无法删除");

    /**
     * 产品 5005XX
     */
    public static CodeMsg PRODUCT_IS_USED = new CodeMsg(500501, "任务 %s 被任务使用无法删除");

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
