package cn.buli_home.utils.file;

/**
 *
 *
 * @author mustard
 * @version 1.0
 * Create by 2023-03-30
 */
public enum FileWriteType {

    /** 只写一次, 若文件存在则继续 */
    ONCE,

    /** 覆写 */
    OVERWRITE,

    /** 追加 */
    APPEND,

    /** 追加且换行 */
    APPEND_NEWLINE

}
