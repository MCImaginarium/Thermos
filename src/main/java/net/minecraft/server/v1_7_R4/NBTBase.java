package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase
{
    public static final String[] a = new String[] {"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

    abstract void write(DataOutput output) throws IOException;

    abstract void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException;

    public abstract String toString();

    public abstract byte getTypeId();

    protected static NBTBase createTag(byte id)
    {
        switch (id)
        {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte();
            case 2:
                return new NBTTagShort();
            case 3:
                return new NBTTagInt();
            case 4:
                return new NBTTagLong();
            case 5:
                return new NBTTagFloat();
            case 6:
                return new NBTTagDouble();
            case 7:
                return new NBTTagByteArray();
            case 8:
                return new NBTTagString();
            case 9:
                return new NBTTagList();
            case 10:
                return new NBTTagCompound();
            case 11:
                return new NBTTagIntArray();
            default:
                return null;
        }
    }

    public abstract NBTBase clone();

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof NBTBase))
        {
            return false;
        }
        else
        {
            NBTBase nbtbase = (NBTBase)p_equals_1_;
            return this.getTypeId() == nbtbase.getTypeId();
        }
    }

    public int hashCode()
    {
        return this.getTypeId();
    }

    protected String a_()
    {
        return this.toString();
    }

    public abstract static class NBTPrimitive extends NBTBase
        {
            public abstract long getLong();

            public abstract int getInt();

            public abstract short getShort();

            public abstract byte getByte();

            public abstract double getDouble();

            public abstract float getFloat();
        }
}
