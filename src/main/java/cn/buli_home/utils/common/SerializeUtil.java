package cn.buli_home.utils.common;

import java.io.*;

/**
 * @author Mustard
 * @date 2022年03月20日 16:36
 */
public class SerializeUtil {

    private SerializeUtil() {}

    /**
     * 序列化对象
     */
    public static byte[] serializeObject(Object obj) {
        ObjectOutputStream objOS = null;
        ByteArrayOutputStream byteArrOS = null;

        try {

            byteArrOS = new ByteArrayOutputStream();
            objOS = new ObjectOutputStream(byteArrOS);

            objOS.writeObject(obj);

            return byteArrOS.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反序列化对象
     */
    public static Object deserializeObject(byte[] buffer) {
        Object obj = null;
        ByteArrayInputStream byteArrIS = new ByteArrayInputStream(buffer);

        try {
            ObjectInputStream objIS = new ObjectInputStream(byteArrIS);
            obj = objIS.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

}
