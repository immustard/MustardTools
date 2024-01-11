package cn.buli_home.utils.constant;

public final class RegexConstant {

    public static String LETTER = "[a-zA-Z]*";

    public static String NUMBER = "[0-9]*";

    public static String HANZI = "[\\u4E00-\\u9FA5]*";

    public static String LETTER_NUMBER = "[a-zA-Z0-9]*";

    public static String LETTER_NUMBER_HANZI = "[a-zA-Z0-9\\u4E00-\\u9FA5]*";

    public static String SPECIAL_CHAR = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";


    private RegexConstant() {}
}
