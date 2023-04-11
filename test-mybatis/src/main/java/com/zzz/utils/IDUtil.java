package com.zzz;

import java.text.DecimalFormat;

public class IDUtil {
    
    /*
    根据 twitter snowflake 算法

    每毫秒生成8100个左右的唯一id
     */

    private static final long anchor = 1525363200000L; // 2018-05-04 00:00:00
    private static long lastTimeStamp = System.currentTimeMillis();

    //       0           [42]         [3]            [5]               [13]
    //   防止为负数     时间戳偏移    机房id长度    机器或进程id长度    一毫秒内的序列号
    private static final long idLength = 64L;  // id 长度

    private static final long timeStampBits = 42L;  // 时间戳长度  可以使用 139 年
    private static final long dataCenterIdBits = 3L;  // 机房id长度 [0-7]
    private static final long workerIdBits = 5L;  // 机器id长度 [0-31]
    private static final long sequenceBits = 13L;  //序列号长度  [0-8195]

    private long dataCenterId = 0L;
    private long workerId = 0L;

    private static long sequence = 0L;

    private static final long maxSequence = (1L << sequenceBits) - 1L;
    private static final long maxDataCenterId = (1L << dataCenterIdBits) - 1L;
    private static final long maxWorkerId = (1L << workerIdBits) - 1L;

    private static final long timeStampShift = idLength - 1 - timeStampBits;
    private static final long dataCenterIdShift = idLength - 1 - timeStampBits - dataCenterIdBits;
    private static final long workerIdShift = idLength - 1 - timeStampBits - dataCenterIdBits - workerIdBits;

    private static IDUtil IDUtil = null;

    public IDUtil() {
    }


    public IDUtil(long dataCenterId, long workerId) {
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    ;

    public static IDUtil getIDUtil() {
        if (IDUtil == null) {
            synchronized (IDUtil.class) {
                if (IDUtil == null) {
                    IDUtil = new IDUtil();
                }
            }
        }
        return IDUtil;
    }

    public static IDUtil getIDUtil(long dataCenterId, long workerId) {
        if (dataCenterId > IDUtil.maxDataCenterId || workerId > IDUtil.maxWorkerId) {
            throw new IllegalArgumentException("datacenterid: [0-7], workerid: [0-31]");
        }
        if (IDUtil == null) {
            synchronized (IDUtil.class) {
                if (IDUtil == null) {
                    IDUtil = new IDUtil(dataCenterId, workerId);
                }
            }
        }
        return IDUtil;
    }

    public synchronized long generateId() {

        long currTimeStamp = System.currentTimeMillis();
        if (currTimeStamp < anchor) {
            throw new IllegalStateException("anchor is error");
        } else if (currTimeStamp < lastTimeStamp) {
            throw new IllegalStateException("curr - last = " + (currTimeStamp - lastTimeStamp));
        }

        if (currTimeStamp == lastTimeStamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                while (currTimeStamp <= lastTimeStamp) {
                    currTimeStamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimeStamp = currTimeStamp;

        return ((currTimeStamp - anchor) << timeStampShift) | (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;

    }


    /**
     * 生成唯一ID
     *
     * @return
     */
    public static synchronized long getUUID() {
        IDUtil idUtil = getIDUtil();
        return idUtil.generateId();
    }


    /**
     * @desc 获取随机UUID
     * @author ZZZ
     * @date 2019/2/21 0021
     * @version 1.0
     */
    public static String getUUIDStr() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


    private static final int DEFAULT_FORMAT_LENGTH = 10;


    /**
     * 格式化数字 eg: 11 -> 000011
     *
     * @param originNo
     * @return
     */
    public static String formatNumber(int originNo) {
        return formatNumber(originNo, DEFAULT_FORMAT_LENGTH);
    }

    /**
     * 格式化数字 eg: 11 -> 000011
     *
     * @param originNo
     * @param legth
     * @return
     */
    public static String formatNumber(int originNo, int legth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < legth; i++) {
            sb.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(sb.toString());
        return decimalFormat.format(originNo);
    }


    public static void main(String[] args) {
        IDUtil idUtil = IDUtil.getIDUtil();
        for (int i = 0; i <= 10; i++) {
            System.out.println(idUtil.generateId());
        }

    }

}
