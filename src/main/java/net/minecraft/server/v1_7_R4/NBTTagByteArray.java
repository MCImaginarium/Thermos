package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase
{
    private byte[] byteArray;
    private static final String __OBFID = "CL_00001213";

    NBTTagByteArray() {}

    public NBTTagByteArray(byte[] p_i45128_1_)
    {
        this.byteArray = p_i45128_1_;
    }

    void write(DataOutput output) throws IOException
    {
        output.writeInt(this.byteArray.length);
        output.write(this.byteArray);
    }

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException
    {
        sizeTracker.a(32); //Forge: Count the length as well
        int j = input.readInt();
        sizeTracker.a((long)(8 * j));
        this.byteArray = new byte[j];
        input.readFully(this.byteArray);
    }

    public byte getTypeId()
    {
        return (byte)7;
    }

    public String toString()
    {
        return "[" + this.byteArray.length + " bytes]";
    }

    public NBTBase clone()
    {
        byte[] abyte = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, abyte, 0, this.byteArray.length);
        return new NBTTagByteArray(abyte);
    }

    public boolean equals(Object p_equals_1_)
    {
        return super.equals(p_equals_1_) ? Arrays.equals(this.byteArray, ((NBTTagByteArray)p_equals_1_).byteArray) : false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }

    public byte[] getByteArray()
    {
        return this.byteArray;
    }
}
