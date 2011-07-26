package com.tweakcart.util;

/**
 * Created by IntelliJ IDEA.
 * User: Robert FH Groeneveld(rgroeneveld@rsdd.nl)
 * Date: 28-6-11
 * Time: 13:44
 * @deprecated of toch niet?
 */
@Deprecated
public final class Bitwise {
    public static short MaxBits = 8;

    public static short packBits(short a, short b, short bitsWidth) {
        bitsWidth = (short) (Bitwise.MaxBits - bitsWidth);
        return (short) ((a << bitsWidth) | b);
    }

    public static short getHighBits(short a, short bitsWidth) {
        bitsWidth = (short) (Bitwise.MaxBits - bitsWidth);
        return (short) (a >> bitsWidth);
    }

    public static short getLowBits(short a, short Highbit) {
        //2 pow 6 min 1;
        Highbit = (short) (Math.pow(2, (Bitwise.MaxBits - Highbit)) - 1);
        return (short) (a & Highbit);
    }
}
