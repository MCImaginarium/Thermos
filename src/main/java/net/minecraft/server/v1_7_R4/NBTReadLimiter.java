package net.minecraft.server.v1_7_R4;

public class NBTReadLimiter
{
    public static final NBTReadLimiter INFINITE = new NBTReadLimiter(0L)
    {
        private static final String __OBFID = "CL_00001902";
        public void a(long size) {}
    };
    private final long spaceAllocated;
    private long spaceRead;
    private static final String __OBFID = "CL_00001903";

    public NBTReadLimiter(long worldIn)
    {
        this.spaceAllocated = worldIn;
    }

    public void a(long size)
    {
        this.spaceRead += size / 8L;

        if (this.spaceRead > this.spaceAllocated)
        {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.spaceRead + "bytes where max allowed: " + this.spaceAllocated);
        }
    }

    /*
     * UTF8 is not a simple encoding system, each character can be either
     * 1, 2, or 3 bytes. Depending on where it's numerical value falls.
     * We have to count up each character individually to see the true
     * length of the data.
     *
     * Basic concept is that it uses the MSB of each byte as a 'read more' signal.
     * So it has to shift each 7-bit segment.
     *
     * This will accurately count the correct byte length to encode this string, plus the 2 bytes for it's length prefix.
     */
    public static void readUTF(NBTReadLimiter tracker, String data)
    {
        tracker.a(16); //Header length
        if (data == null)
            return;

        int len = data.length();
        int utflen = 0;

        for (int i = 0; i < len; i++)
        {
            int c = data.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) utflen += 1;
            else if (c > 0x07FF)                utflen += 3;
            else                                utflen += 2;
        }
        tracker.a(8 * utflen);
    }
}
