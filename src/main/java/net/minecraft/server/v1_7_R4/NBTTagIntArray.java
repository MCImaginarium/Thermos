package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
    private int[] intArray;
    private static final String __OBFID = "CL_00001221";

    NBTTagIntArray() {}

    public NBTTagIntArray(int[] p_i45132_1_)
    {
        this.intArray = p_i45132_1_;
    }

    void write(DataOutput output) throws IOException
    {
        output.writeInt(this.intArray.length);

        for (int i = 0; i < this.intArray.length; ++i)
        {
            output.writeInt(this.intArray[i]);
        }
    }

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException
    {
        sizeTracker.a(32); //Forge: Count the length as well
        int j = input.readInt();
        sizeTracker.a((long)(32 * j));
        this.intArray = new int[j];

        for (int k = 0; k < j; ++k)
        {
            this.intArray[k] = input.readInt();
        }
    }

    public byte getTypeId()
    {
        return (byte)11;
    }

    public String toString()
    {
        String s = "[";
        int[] aint = this.intArray;
        int i = aint.length;

        for (int j = 0; j < i; ++j)
        {
            int k = aint[j];
            s = s + k + ",";
        }

        return s + "]";
    }

    public NBTBase clone()
    {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(aint);
    }

    public boolean equals(Object p_equals_1_)
    {
        return super.equals(p_equals_1_) ? Arrays.equals(this.intArray, ((NBTTagIntArray)p_equals_1_).intArray) : false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] getIntArray()
    {
        return this.intArray;
    }
}
