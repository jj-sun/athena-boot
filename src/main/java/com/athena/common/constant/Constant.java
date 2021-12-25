package com.athena.common.constant;

/**
 * 常量
 *
 * @author sunjie
 */
public class Constant {
	/** 超级管理员ID */
	public static final String SUPER_ADMIN = "admin";

    public static final String TREE_ROOT = "0";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    public enum DelFlag {
        /**
         * 正常
         */
        DEL_FLAG_0(0),
        /**
         * 禁用
         */
        DEL_FLAG_1(1);

        private final int value;
        DelFlag(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }

    }

    public enum PermissionType {
        /**
         * 权限类型
         */
        LIST(0, "目录"),
        MENU(1, "菜单"),
        BUTTON(2, "按钮");
        private int value;
        private String name;
        PermissionType(int value, String name) {
            this.value = value;
            this.name = name;
        }
        public int getValue() {
            return value;
        }

    }
}
