package cn.buli_home.utils.constant;

public final class RegexConstant {

    /**
     * 字母
     */
    public static String LETTER = "[a-zA-Z]*";

    /**
     * 大写字母
     */
    public static String UPPER_LETTER = "[A-Z]*";

    /**
     * 小写字母
     */
    public static String LOWER_LETTER = "[a-z]*";

    /**
     * 数字
     */
    public static String NUMBER = "[0-9]*";

    /**
     * 汉字
     */
    public static String HANZI = "[\\u4E00-\\u9FA5]*";

    /**
     * 字母和数字
     */
    public static String LETTER_NUMBER = "[a-zA-Z0-9]*";

    /**
     * 字母、数字和汉字
     */
    public static String LETTER_NUMBER_HANZI = "[a-zA-Z0-9\\u4E00-\\u9FA5]*";

    /**
     * 特殊字符
     */
    public static String SPECIAL_CHAR = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";


    private RegexConstant() {}
}
