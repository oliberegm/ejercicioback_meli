package com.cloud.meli.trace.unit;

import com.cloud.meli.trace.utils.Util;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {
    @Test
    public void distanceCoordTest() {
        Assertions.assertEquals(9190.202182677493, Util.distanceCoord(10, 10));
    }

    @Test
    public void validateIpTest() {
        Assertions.assertTrue(Util.validateIp("102.168.0.1"));
        Assertions.assertFalse(Util.validateIp("102.-168.0.1"));
        Assertions.assertFalse(Util.validateIp("255.255.255.256"));
    }
}