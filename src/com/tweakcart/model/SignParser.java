package com.tweakcart.model;

import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
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
        NULL(0),
        COLLECT(1),
        DEPOSIT(2),
        FUEL(3),
        SMELT(4),
        ITEM(5);

        private int id;
        private static final ACTION[] actions = new ACTION[6];

        private ACTION(int id)
        {
            this.id = id;
        }

        static
        {
            for (ACTION act : values()) actions[act.getId()] = act;
        }

        public int getId()
        {
            return id;
        }

        public static ACTION fromId(int id)
        {
            return actions[id];
        }
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

    public static ACTION ParseLine(String line, Minecart cart)
    {
        if (Character.isDigit(line.charAt(0)))
        {
            return ACTION.ITEM;
        }

        switch (line.charAt(0))
        {
            case 'c':
                if (line.equals("collect items"))
                {
                    if (SignParser.CheckStorageCart(cart))
                    {
                        return ACTION.COLLECT;
                    }
                    else
                    {
                        return ACTION.NULL;
                    }
                }
                break;
            case 'd':
                if (line.equals("deposit items"))
                {
                    if (SignParser.CheckStorageCart(cart))
                    {
                        return ACTION.DEPOSIT;
                    }
                    else
                    {
                        return ACTION.NULL;
                    }
                }
                break;
            case 'f':
                if (line.charAt(1) == 'u' && line.charAt(2) == 'e' && line.charAt(3) == 'l')
                {
                    if (SignParser.CheckStorageCart(cart))
                    {
                        return ACTION.FUEL;
                    }
                    else
                    {
                        return ACTION.NULL;
                    }
                }
                break;
            case 's':
                if (line.charAt(1) == 'm' && line.charAt(2) == 'e' && line.charAt(3) == 'l' && line.charAt(4) == 't')
                {
                    if (SignParser.CheckStorageCart(cart))
                    {
                        return ACTION.SMELT;
                    }
                    else
                    {
                        return ACTION.NULL;
                    }
                }
                break;
            default:
                return ACTION.NULL;

        }
        return ACTION.NULL;
    }

    public static short getFirstAction(String[] lines, Minecart cart)
    {
        ACTION first;
        short _return;

        for (int a = 0; a < lines.length; a++)
        {
            first = ParseLine(lines[a].toLowerCase(), cart);
            if (first != ACTION.NULL)
            {
                return Integer.highestOneBit();
            }
        }

        return -1;
    }

    public static ACTION ParseSign(Sign sign, Minecart cart)
    {
        String[] lines = sign.getLines();

        ACTION FirstLine = SignParser.ParseLine(lines[0].toLowerCase(), cart);
        return ACTION.NULL;
    }

    public static boolean CheckStorageCart(Minecart cart)
    {
        return cart instanceof StorageMinecart;
    }

    public static boolean CheckCart(Minecart cart)
    {
        return !((cart instanceof StorageMinecart) || (cart instanceof PoweredMinecart));
    }
}
