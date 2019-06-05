package com.aigestudio.toolkit.helpers;

import android.os.Process;
import android.util.Log;
import com.aigestudio.toolkit.helpers.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志打印输出工具
 * 使用前需要先调用{@link #configure(Configuration)}方法进行配置，否则默认不打印也不输出日志。日志打印的格式与Android自带的
 * {@link Log}一致；唯一不同的是日志输出的格式，与打印不同日志输出格式省去了包名，即：date time PID-TID priority/tag: message。在需
 * 要进行日志输出前，一个好的方式是按包名为日志输出指定不同的目录，具体可参考
 * {@link com.aigestudio.toolkit.helpers.logger.Logger.Configuration}
 * Helper for print and store log
 * Print and store log is disable by default, make sure set
 * {@link com.aigestudio.toolkit.helpers.logger.Logger.Configuration} via {@link #configure(Configuration)} before any
 * method call in this class. The format of log print is same as {@link Log}, but the store will ignore package name
 * like: date time PID-TID priority/tag: message. The best way for store is set a package name related folder name via
 * {@link #configure(Configuration)}
 */
public final class ALogger extends Logger {
    private static volatile ALogger sInstance;

    private ALogger() {
    }

    public static ALogger getInstance() {
        if (null == sInstance) synchronized (ALogger.class) {
            if (null == sInstance) sInstance = new ALogger();
        }
        return sInstance;
    }

    public final void v(final Object msg) {
        v(PREFIX, msg);
    }

    public final void v(final String tag, final Object msg) {
        if (configuration.logout) Log.v(tag, msg.toString());
        if (null != configuration.file) store("V", tag, configuration.file, msg.toString());
    }

    public final void d(final Object msg) {
        d(PREFIX, msg);
    }

    public final void d(final String tag, final Object msg) {
        if (configuration.logout) Log.v(tag, msg.toString());
        if (null != configuration.file) store("D", tag, configuration.file, msg.toString());
    }

    public final void i(final Object msg) {
        d(PREFIX, msg);
    }

    public final void i(final String tag, final Object msg) {
        if (configuration.logout) Log.v(tag, msg.toString());
        if (null != configuration.file) store("I", tag, configuration.file, msg.toString());
    }

    public final void w(final Object msg) {
        d(PREFIX, msg);
    }

    public final void w(final String tag, final Object msg) {
        if (configuration.logout) Log.v(tag, msg.toString());
        if (null != configuration.file) store("W", tag, configuration.file, msg.toString());
    }

    public final void e(final Object msg) {
        d(PREFIX, msg);
    }

    public final void e(final String tag, final Object msg) {
        if (configuration.logout) Log.v(tag, msg.toString());
        if (null != configuration.file) store("E", tag, configuration.file, msg.toString());
    }

    private void store(final String level, final String prefix, final File file, final String msg) {
        store(file, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).format(new Date()) + " "
                + Process.myPid() + "-" + Thread.currentThread().getId() + " " + level + "/" + prefix + ":" + msg);
    }
}