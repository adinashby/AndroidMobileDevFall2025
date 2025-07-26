package com.example.testingexample;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testReverse_normal() {
        assertEquals("cba", StringUtil.reverse("abc"));
    }

    @Test
    public void testReverse_empty() {
        assertEquals("", StringUtil.reverse(""));
    }

    @Test
    public void testReverse_null() {
        assertNull(StringUtil.reverse(null));
    }
}
