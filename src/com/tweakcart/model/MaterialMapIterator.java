package com.tweakcart.model;

import com.tweakcart.TweakCartException;

public class MaterialMapIterator
{
    private transient int[] _keys, _values;
    private int pointer = -1;
    private int size = 0;

    public MaterialMapIterator(int[] keys, int[] values) throws TweakCartException
    {
        if (keys.length != values.length)
            throw new TweakCartException(3);
        _keys = keys;
        _values = values;
        size = _keys.length;
    }

    public boolean hasNext()
    {
        return pointer < (size - 1);
    }

    public boolean hasPrevious()
    {
        return pointer > 1;
    }

    public void next()
    {
        pointer++;
    }

    public void remove()
    {
        int[] keys = new int[size - 1];
        int[] values = new int[size - 1];

        int index = 0;
        for (int k = 0; k < size; k++)
        {
            if (index == pointer)
                continue;
            values[index] = _values[k];
            keys[index] = _keys[k];
            index++;
        }

        size = _keys.length;
    }

    public void previous()
    {
        pointer--;
    }

    public int key()
    {
        return _keys[pointer];
    }

    public int value()
    {
        return _values[pointer];
    }

    public void rewind()
    {
        pointer = -1;
    }

    public void end()
    {
        pointer = size - 1;
    }

    @Override
    public String toString()
    {
        if (size == 0)
            return "[0 items] {} @ 0";
        String msg = "[" + size + " items] :: { ";
        for (int index = 0; index < size; index++)
        {
            msg += "[" + _keys[index] + ", " + _values[index] + "], ";
        }
        return msg.substring(0, msg.length() - 2) + " } @ " + pointer;
    }
}
