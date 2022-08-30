package org.buli.apps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buli.utils.common.StringUtils;
import org.buli.utils.date.DateUtils;

public class TestApp {
    private static final Logger log = LogManager.getLogger(TestApp.class);

    public static void main(String[] args) throws Exception {

        String idx = StringUtils.loadRecordString("idx");

        int i = StringUtils.parseInt(idx);

        log.info(i);

        i += 6;

        StringUtils.recordString("idx", i+"");


    }

}
