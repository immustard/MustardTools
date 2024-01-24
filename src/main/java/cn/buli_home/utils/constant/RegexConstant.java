package cn.buli_home.utils.constant;

public final class RegexConstant {

    /**
     * 字母
     */
    public static final String LETTER = "[a-zA-Z]*";

    /**
     * 大写字母
     */
    public static final String UPPER_LETTER = "[A-Z]*";

    /**
     * 小写字母
     */
    public static final String LOWER_LETTER = "[a-z]*";

    /**
     * 数字
     */
    public static final String NUMBER = "[0-9]*";

    /**
     * 汉字
     */
    public static final String HANZI = "[\\u4E00-\\u9FA5]*";

    /**
     * 字母和数字
     */
    public static final String LETTER_NUMBER = "[a-zA-Z0-9]*";

    /**
     * 字母、数字和汉字
     */
    public static final String LETTER_NUMBER_HANZI = "[a-zA-Z0-9\\u4E00-\\u9FA5]*";

    /**
     * 特殊字符
     */
    public static final String SPECIAL_CHAR = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";

    /**
     * 生日
     */
    public static final String BIRTHDAY = "^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$";

    private RegexConstant() {
    }
}
