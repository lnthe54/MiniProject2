package com.example.lnthe54.miniproject2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented activity_detail_albums, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under activity_detail_albums.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.lnthe54.miniproject2", appContext.getPackageName());
    }
}
