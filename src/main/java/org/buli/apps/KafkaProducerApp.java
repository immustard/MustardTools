package org.buli.apps;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buli.utils.common.StringUtils;
import org.buli.utils.constant.NumberConstant;
import org.buli.utils.date.DateUtils;
import org.buli.utils.date.StopWatchUtils;
import org.buli.utils.file.FileUtils;
import org.buli.utils.kafka.KafkaUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class KafkaProducerApp {

    private static final Logger log = LogManager.getLogger(KafkaUtils.class);

    public static void main(String[] args) throws Exception {
        String servers = "10.133.2.145:9092,10.133.2.146:9092,10.133.2.147:9092";
        // ods-tbox-seatunnel
        String topic = "seatunnel-source-ibmcos-concat-someip";

        checkTopic(servers, topic);
        sendMessage(servers, topic);
    }

    public static void sendMessage(String servers, String topic) throws Exception {
        StopWatchUtils.start();

        KafkaProducer<String, String> producer = KafkaUtils.getStringProducer(servers);
        String value = "{\"vid\":\"10000004785\",\"vin\":\"LZYTBGCW6K1024515\",\"daq_time\":\"2022-08-08 11:51:10\",\"status\":\"1\",\"c_stat\":\"3\",\"mode\":\"1\",\"speed\":3.0,\"mileage\":196344.7,\"t_volt\":513.1,\"t_current\":-2.9,\"soc\":62,\"dcdc_stat\":\"1\",\"isulate_r\":28636.0,\"ap_stroke\":null,\"bp_stroke\":null,\"gear\":\"30\",\"brake_stat\":\"1\",\"power_stat\":\"0\",\"lng\":125.385418,\"lat\":43.904903,\"province\":\"吉林省\",\"city\":\"长春市\",\"district\":\"二道区\",\"max_volt_bat_id\":\"1\",\"max_volt_cell_id\":\"200\",\"max_cell_volt\":65535.0,\"min_volt_bat_id\":\"1\",\"min_cell_volt_id\":\"67\",\"min_cell_volt\":3286.0,\"max_temp_sys_id\":\"1\",\"max_temp_probe_id\":\"22\",\"max_temp\":40,\"min_temp_sys_id\":\"1\",\"min_temp_probe_id\":\"41\",\"min_temp\":31,\"max_alarm_lvl\":0,\"genral_alarm\":\"0\",\"bat_fault_nu\":\"0\",\"bat_fault_list\":null,\"motor_fault_nu\":\"0\",\"motor_fault_list\":null,\"engine_fault_nu\":\"0\",\"engine_fault_list\":null,\"other_fault_nu\":\"0\",\"other_fault_list\":null,\"cell_nu\":0,\"cell_pack_nu\":0,\"cell_volt_list\":null,\"cell_probe_nu\":0,\"cell_probe_pack_nu\":0,\"cell_temp_list\":null,\"cell_volt_stat\":0,\"cell_temp_stat\":0,\"motor_nu\":1,\"motor_list\":\"{\\\"data\\\":[{\\\"motor_rpm\\\":85,\\\"motor_sid\\\":1,\\\"motor_controller_in_volt\\\":515.0,\\\"motor_controller_bus_current\\\":-2.0,\\\"motor_temp\\\":69,\\\"motor_controller_temp\\\":57,\\\"motor_state\\\":2,\\\"motor_torque\\\":2960}]}\",\"motor_stat\":0}";
//        String value = "{\"vin\":\"text\",\"collectTime\":\"text\",\"tbox_GPS_1_GPSTime_Hour\":\"text\",\"tbox_GPS_1_GPSTime_Minute\":\"text\",\"tbox_GPS_1_GPSTime_Second\":\"text\",\"tbox_GPS_1_GPSTime_Year\":\"text\",\"tbox_GPS_1_GPSTime_Month\":\"text\",\"tbox_GPS_1_GPSTime_Day\":\"text\",\"tbox_GPS_2_GPS_Latitude\":\"text\",\"tbox_GPS_2_GPS_LatitudeStatus\":\"text\",\"tbox_GPS_2_GPS_Longitude\":\"text\"}";

        String date = DateUtils.formatDate(new Date());

        String KAFKA_IDX_KEY = "kafka_idx";
        int kafkaIdx = StringUtils.parseInt(UserDefaultUtils.getInstance().loadRecordString(KAFKA_IDX_KEY));

        int idx = 1;
        String[] type = new String[] {"tbox","someip","track","udp","can"};
//        String[] type = new String[] {"tbox", "someip", "track", "can"};

        String tbox = FileUtils.readFile("/Users/mustard/Downloads/seatunnel_concat/ods_ibmcos_message_tbox.json");
        String someip = FileUtils.readFile("/Users/mustard/Downloads/seatunnel/ods_ibmcos_message_someip.json");
        String track = FileUtils.readFile("/Users/mustard/Downloads/seatunnel/ods_ibmcos_message_had.json");
        String udp = FileUtils.readFile("/Users/mustard/Downloads/seatunnel/ods_ibmcos_message_udp.json");
        String can = FileUtils.readFile("/Users/mustard/Downloads/seatunnel/ods_ibmcos_message_can.json");

        String jsonStr = null;

        while (idx <= NumberConstant.ONE) {
            kafkaIdx++;

//            String msgType = type[idx%type.length];
            String msgType = "tbox";
            switch (msgType) {
                case "tbox":
                    jsonStr = tbox;
                    break;
                case "someip":
                    jsonStr = someip;
                    break;
                case "track":
                    jsonStr = track;
                    break;
                case "udp":
                    jsonStr = udp;
                    break;
                case "can":
                    jsonStr = can;
                    break;
                default:
                    log.error("error type");
                    return;
            }

            JSONObject jsonObject = JSON.parseObject(jsonStr);
//            jsonObject.put("vid", kafkaIdx+"");
//            JSONObject jsonObject = new JSONObject();
            jsonObject.put("MessageType", type[idx%type.length]);
//            jsonObject.put("MessageType", "can");
            jsonObject.put("vin",kafkaIdx+"");
            jsonObject.put("collectTime", DateUtils.formatDateTime(new Date()));
            jsonObject.put("pdate", date);

            String s = JSON.toJSONString(jsonObject);
            KafkaUtils.sendAsyncMessage(producer, topic, s, true);

            idx++;
        }

        UserDefaultUtils.getInstance().recordString(KAFKA_IDX_KEY, kafkaIdx+"");

        producer.close();

        Long millSec = StopWatchUtils.stop(TimeUnit.MILLISECONDS);
        log.info("完成: " + DateUtils.convertMillisecond2TimeStr(millSec));
    }

    public static void checkTopic(String servers, String topic) {
        try {
            Set<String> topics = KafkaUtils.getTopics(servers);
            boolean exist = topics.contains(topic);

            if (!exist) {
                KafkaUtils.createTopic(servers, topic, 6, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
