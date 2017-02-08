package com.lakeba.javasamples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Lakeba.
 */
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = new MainActivity();
    }

    @Test
    public void testDoThreading() throws Exception {
        mainActivity.doThreading();
    }

}