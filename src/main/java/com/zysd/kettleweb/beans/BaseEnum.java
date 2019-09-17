package com.zysd.kettleweb.beans;

/**
 * Created by Garmin on 2018/8/30.
 */
public class BaseEnum {
    /**
     * 空字符串
     */
    public static final String NULL_STRING = "";
    //用户key
    public static final String USER_STRING = "user";

    public static final Integer PAGE_SIZE = 10;

    public static final Integer INIT_PAGE = 0;

    public enum YesOrNo {
        YES(1, "是"),
        NO(0, "否");

        private Integer value;
        private String text;

        YesOrNo (Integer value, String text) {
            this.value = value;
            this.text = text;
        }

        public Integer val() {
            return this.value;
        }
        public String text() {
            return this.text;
        }

        public static String getText(Integer value) {
            for (YesOrNo c : YesOrNo.values()) {
                if (c.val().equals(value)) {
                    return c.text;
                }
            }
            return null;
        }
    }
}
