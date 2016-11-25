
package com.stt.ThreadDemo.ThreadPattern.part11.Sample2;
public class Log {
    private static final ThreadLocal tsLogCollection = new ThreadLocal();

    // ����һ��log
    public static void println(String s) {
        getTSLog().println(s);
    }

    // �ر�log
    public static void close() {
        getTSLog().close();
    }

    // ��ȡ���߳����е�log
    private static TSLog getTSLog() {
        TSLog tsLog = (TSLog)tsLogCollection.get();

        //����߳��ǵ�һ�κ��У��ͽ����µ�������½log
        if (tsLog == null) {
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollection.set(tsLog);
        }

        return tsLog;
    }
}
