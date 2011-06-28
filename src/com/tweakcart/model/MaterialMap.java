package com.tweakcart.model;

import com.tweakcart.TweakCartException;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class MaterialMap
{
    private int size = 0;

    private transient int[] _keys;
    private transient int[] _values;

    public MaterialMap()
    {
        _keys = new int[size];
        _values = new int[size];
    }

    private MaterialMap(int[] k, int[] v)
    {
        _keys = k;
        _values = v;
        size = k.length;
    }

    private int getKey(int id)
    {
        return getKey(id, (byte) -1);
    }

    private int getKey(int id, byte data)
    {
        return ((id << 8) | data);
    }

    public void put(int id, byte data, int value)
    {
        put(getKey(id, data), value);
    }

    private void put(int key, int value)
    {
        try
        {
            update(key, value);
        } catch (TweakCartException e)
        {
            int[] keys = new int[size + 1];
            int[] values = new int[size + 1];

            for (int i = 0; i < size; i++)
            {
                keys[i] = _keys[i];
                values[i] = _values[i];
            }

            keys[size] = key;
            values[size] = value;

            _values = values;
            _keys = keys;

            size = _values.length;
        }
    }

    public void add(int id, byte data, int amount)
    {
        int key = getKey(id, data);
        try
        {
            int index = getIndex(key);
            _values[index] += amount;
        } catch (TweakCartException e)
        {
            put(id, data, amount);
        }
    }

    public void remove(int id, byte data, int amount) throws TweakCartException
    {
        int key = getKey(id, data);
        int index = getIndex(key);
        _values[index] -= amount;
    }

    public void remove(int id, byte data) throws TweakCartException
    {
        int key = getKey(id, data);
        int keyIndex = getIndex(key);

        size--;

        int[] keys = new int[size];
        int[] values = new int[size];

        int index = 0;
        for (int k = 0; k < size + 1; k++)
        {
            if (index == keyIndex)
                continue;
            values[index] = _values[k];
            keys[index] = _keys[k];
            index++;
        }

        size = _values.length;
    }

    public int get(int id, byte data) throws TweakCartException
    {
        int key = getKey(id, data);
        return _values[getIndex(key)];
    }

    public boolean containsKey(int id, byte data)
    {
        try
        {
            getIndex(getKey(id, data));
            return true;
        } catch (TweakCartException e)
        {
            return false;
        }
    }

    public MaterialMapIterator iterator()
    {
        try
        {
            return new MaterialMapIterator(_keys, _values);
        } catch (TweakCartException e)
        {
            return null;
        }
    }

    private int getIndex(int key) throws TweakCartException
    {
        for (int index = 0; index < size; index++)
        {
            if (_keys[index] == key)
                return index;
        }
        throw new TweakCartException(2);
    }

    private void update(int key, int value) throws TweakCartException
    {
        _values[getIndex(key)] = value;
    }

    @Override
    public String toString()
    {
        if (size == 0)
            return "{}";
        String msg = "{ ";
        for (int index = 0; index < size; index++)
        {
            msg += "[" + _keys[index] + ", " + _values[index] + "], ";
        }
        return msg.substring(0, msg.length() - 2) + " }";
    }

    @Override
    public MaterialMap clone()
    {
        MaterialMap clone = new MaterialMap();
        MaterialMapIterator iterator = iterator();
        while (iterator.hasNext())
        {
            iterator.next();
            clone.put(iterator.key(), iterator.value());
        }
        return clone;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}

