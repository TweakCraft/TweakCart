package com.tweakcart.model;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;

/**
 * Created by IntelliJ IDEA.
 * User: Robert FH Groeneveld(rgroeneveld@rsdd.nl)
 * Date: 27-6-11
 * Time: 20:45
 */
public class SignParser
{
    public enum ACTION
    {
        NULL,
        COLLECT,
        DEPOSIT;
    }

    public byte[] ByteMap = null;
    private static SignParser _Instance;

    public void SignParser()
    {
        //
    }

    public static SignParser getInstance()
    {
        if (_Instance == null)
        {
            _Instance = new SignParser();
        }

        return _Instance;
    }

    public static ACTION ParseSign(String[] sign, Minecart cart)
    {
        if (sign[0].equalsIgnoreCase("collect items"))
        {
            byte[] ByteMap = new byte[512];


            return ACTION.COLLECT;
        }
        else if (sign[0].equalsIgnoreCase("deposit items"))
        {
            byte[] ByteMap = new byte[512];


            return ACTION.DEPOSIT;
        }

        return ACTION.NULL;
    }

    public static boolean CheckStorageCart(Minecart cart)
    {
        return cart instanceof StorageMinecart;
    }

    public static boolean CheckCart(Minecart cart)
    {
        return !(cart instanceof StorageMinecart);
    }
}
