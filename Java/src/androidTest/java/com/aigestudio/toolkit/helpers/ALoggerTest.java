package com.aigestudio.toolkit.helpers;

import android.Manifest;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import com.aigestudio.toolkit.helpers.logger.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class ALoggerTest {
    @Rule
    public final GrantPermissionRule rule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    private static final String TAG = "__TEST__";

    private final ALogger logger = ALogger.getInstance();

    @Test
    public final void testVOnlyPrint() {
        logger.configure(new Logger.Configuration.Builder()
                .logout(true)
                .store(false)
                .build());
        logger.v("Test print v message with default tag.");
        logger.v(TAG, "Test print v message with tag " + TAG + ".");
    }

    @Test
    public final void testVStore() throws IOException {
        final File file = new File(Environment.getExternalStorageDirectory(), ".aigestudio");
        final String name = InstrumentationRegistry.getContext().getPackageName();
        logger.configure(new Logger.Configuration.Builder()
                .logout(false)
                .store(true)
                .directory(file)
                .filename(name)
                .build());
        logger.v(TAG, "Test store v message with tag " + TAG + ".");
        final BufferedReader br = new BufferedReader(new FileReader(new File(file, name)));
        final String line = br.readLine();
        br.close();
        //noinspection ResultOfMethodCallIgnored
        new File(file, name).delete();
        Assert.assertNotNull(line);
        Assert.assertTrue(line.endsWith("Test store v message with tag " + TAG + "."));
    }
}