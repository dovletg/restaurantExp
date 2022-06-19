package com.lkdrestaurantserver.utill;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Utils {
    public Utils() {
    }

    public static byte[] uuidToByte(UUID uuid) {
        byte[] buffer = new byte[16];
        return ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits()).array();
    }
}