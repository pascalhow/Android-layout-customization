package com.mydrivesolutions.juniortest;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends InstrumentationTestCase {
    @Test
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 4;
        assertEquals(expected, reality);
    }
}