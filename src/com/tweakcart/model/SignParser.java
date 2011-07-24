package com.tweakcart.model;

import com.tweakcart.util.Bitwise;
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
        NULL((short) 0),
        COLLECT((short) 1),
        DEPOSIT((short) 2),
        ITEM((short) 3);

        private short id;
        private static final ACTION[] actions = new ACTION[6];

        private ACTION(short id)
        {
            this.id = id;
        }

        static
        {
            for (ACTION act : values())
            {
                actions[act.getId()] = act;
            }
        }

        public short getId()
        {
            return id;
        }

        public static ACTION fromId(short id)
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
            first = ParseLine(SignParser.removeBracers(lines[a].toLowerCase()), cart);
            if (first != ACTION.NULL)
            {
                return Bitwise.packBits((short) a, (short) first.getId(), (short) 2);
            }
        }

        return 255;
    }

    public static String removeBracers(String line)
    {
        if ((line.charAt(0) != '[') && (line.charAt(line.length() - 2) != ']'))
        {
            return line;
        }

        return line.substring(1, line.length() - 3);
    }

    //TODO: ByteMap vullen.
    public static boolean buildByteMap(String line, Minecart cart)
    {
        //Parse next line items ?
        String temp = "";
        SignParser.getInstance().ByteMap = new byte[512];

        for (int b = 0; b <= line.length(); b++)
        {
            char c = line.charAt(b);

            if (Character.isDigit(c))
            {
                temp += c;
                continue;
            }

            //First letter can be a direction, because it isn't a digit.
            if (b == 0)
            {
                //Check if the second Char is a + with indicates that the first is a direction
                if (line.charAt(1) == '+')
                {
                    //Get cart current direction
                    Direction d = Direction.getHorizontalDirection(cart.getVelocity());

                    //Check if the cart has the desired direction, if not continue to the next line.
                    switch (c)
                    {
                        case 'n':
                            if (d != Direction.NORTH)
                            {
                                return false;
                            }
                            break;
                        case 's':
                            if (d != Direction.SOUTH)
                            {
                                return false;
                            }
                            break;
                        case 'e':
                            if (d != Direction.EAST)
                            {
                                return false;
                            }
                            break;
                        case 'w':
                            if (d != Direction.WEST)
                            {
                                return false;
                            }
                            break;
                    }

                    b = 1;
                    continue;
                }
            }

            switch (c)
            {
                case '!':
                    break;
                case ':':
                    break;
                case ';':
                    break;
                case '@':
                    break;
                case '-':
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    //TODO: Hoe gaan we dat oplossen als er zo wel collect als deposit op 1 sign staat.......
    public static void ParseSign(Sign sign, Minecart cart)
    {
        String[] lines = sign.getLines();
        boolean running = true;

        short result = SignParser.getFirstAction(lines, cart);
        short index = Bitwise.getHighBits(result, (short) 2);
        ACTION action = ACTION.fromId(Bitwise.getLowBits(result, (short) 2));

        System.out.println("First result on index " + index + " ACTION: " + action.toString());

        short a = (short) (index + 1);

        do
        {
            //TODO: Finish buildByteMap
            if (!(SignParser.buildByteMap(SignParser.removeBracers(lines[a].toLowerCase()), cart)))
            {
                //Sign didn't match requirements.
                //May by log this or not ?
            }
            else
            {
                //TODO: What to do XD ?
                //ByteMap ready (SignParser.getInstance().ByteMap)
                //Execute Action ? (Collect/Deposit)
            }

            do
            {
                a++;
                action = SignParser.ParseLine(SignParser.removeBracers(lines[a].toLowerCase()), cart);
            }
            while ((action != ACTION.NULL) || (a == (lines.length - 1)));

            if (a == (lines.length - 1))
            {
                running = false;
            }
        }
        while (running);
    }

    public static boolean CheckStorageCart(Minecart cart)
    {
        return (cart instanceof StorageMinecart);
    }

    public static boolean CheckCart(Minecart cart)
    {
        return !((cart instanceof StorageMinecart) || (cart instanceof PoweredMinecart));
    }
}
