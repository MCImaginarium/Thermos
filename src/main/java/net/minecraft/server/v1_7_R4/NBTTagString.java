package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase
{
    private String data;
    private static final String __OBFID = "CL_00001228";

    public NBTTagString()
    {
        this.data = "";
    }

    public NBTTagString(String p_i1389_1_)
    {
        this.data = p_i1389_1_;

        if (p_i1389_1_ == null)
        {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    void write(DataOutput output) throws IOException
    {
        output.writeUTF(this.data);
    }

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException
    {
        this.data = input.readUTF();
        NBTReadLimiter.readUTF(sizeTracker, data); // Forge: Correctly load String length including header.
    }

    public byte getTypeId()
    {
        return (byte)8;
    }

    public String toString()
    {
        return "\"" + this.data + "\"";
    }

    public NBTBase clone()
    {
        return new NBTTagString(this.data);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!super.equals(p_equals_1_))
        {
            return false;
        }
        else
        {
            NBTTagString nbttagstring = (NBTTagString)p_equals_1_;
            return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.data.hashCode();
    }

    public String a_()
    {
        return this.data;
    }
}
