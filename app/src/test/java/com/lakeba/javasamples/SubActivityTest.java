package com.lakeba.javasamples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Lakeba.
 */
public class SubActivityTest {

    private SubActivity subActivity;

    @Before
    public void setUp() throws Exception {
        subActivity = new SubActivity();
    }

    @Test
    public void doHandlerSample() throws Exception {
        subActivity.doHandlerSample();
    }

}