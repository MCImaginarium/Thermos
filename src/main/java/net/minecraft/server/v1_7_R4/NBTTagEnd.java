package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase
{
    private static final String __OBFID = "CL_00001219";

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException {}

    void write(DataOutput output) throws IOException {}

    public byte getTypeId()
    {
        return (byte)0;
    }

    public String toString()
    {
        return "END";
    }

    public NBTBase clone()
    {
        return new NBTTagEnd();
    }
}
