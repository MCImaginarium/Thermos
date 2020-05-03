package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase
{
    private static final Logger b = LogManager.getLogger();
    private Map<String, NBTBase> map = null;

    public NBTTagCompound()
    {
    	this (false);
    }
    
    public NBTTagCompound(boolean _copy)
    {
    	if (!_copy)
    	{
    		map = new HashMap<String, NBTBase>();
        }
    }
    
    void write(DataOutput output) throws IOException
    {
        Iterator iterator = this.map.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();
            NBTBase nbtbase = (NBTBase)this.map.get(s);
            a(s, nbtbase, output);
        }

        output.writeByte(0);
    }

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException
    {
        if (depth > 512)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else
        {
            this.map.clear();
            byte b0;

            while ((b0 = a(input, sizeTracker)) != 0)
            {
                String s = b(input, sizeTracker);
                NBTReadLimiter.readUTF(sizeTracker, s); // Forge: Correctly read String length including header.
                NBTBase nbtbase = a(b0, s, input, depth + 1, sizeTracker);
                this.map.put(s, nbtbase);
            }
        }
    }

    public Set c()
    {
        return this.map.keySet();
    }

    public byte getTypeId()
    {
        return (byte)10;
    }

    public void set(String key, NBTBase value)
    {
        this.map.put(key, value);
    }

    public void setByte(String key, byte value)
    {
        this.map.put(key, new NBTTagByte(value));
    }

    public void setShort(String key, short value)
    {
        this.map.put(key, new NBTTagShort(value));
    }

    public void setInt(String key, int value)
    {
        this.map.put(key, new NBTTagInt(value));
    }

    public void setLong(String key, long value)
    {
        this.map.put(key, new NBTTagLong(value));
    }

    public void setFloat(String key, float value)
    {
        this.map.put(key, new NBTTagFloat(value));
    }

    public void setDouble(String key, double value)
    {
        this.map.put(key, new NBTTagDouble(value));
    }

    public void setString(String key, String value)
    {
        this.map.put(key, new NBTTagString(value));
    }

    public void setByteArray(String key, byte[] value)
    {
        this.map.put(key, new NBTTagByteArray(value));
    }

    public void setIntArray(String key, int[] value)
    {
        this.map.put(key, new NBTTagIntArray(value));
    }

    public void setBoolean(String key, boolean value)
    {
        this.setByte(key, (byte)(value ? 1 : 0));
    }

    public NBTBase get(String key)
    {
        return (NBTBase)this.map.get(key);
    }

    public byte b(String key)
    {
        NBTBase nbtbase = (NBTBase)this.map.get(key);
        return nbtbase != null ? nbtbase.getTypeId() : 0;
    }

    public boolean hasKey(String key)
    {
        return this.map.containsKey(key);
    }

    public boolean hasKeyOfType(String key, int type)
    {
        byte b0 = this.b(key);
        return b0 == type ? true : (type != 99 ? false : b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6);
    }

    public byte getByte(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive)this.map.get(key)).getByte();
        }
        catch (ClassCastException classcastexception)
        {
            return (byte)0;
        }
    }

    public short getShort(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive)this.map.get(key)).getShort();
        }
        catch (ClassCastException classcastexception)
        {
            return (short)0;
        }
    }

    public int getInt(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive)this.map.get(key)).getInt();
        }
        catch (ClassCastException classcastexception)
        {
            return 0;
        }
    }

    public long getLong(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0L : ((NBTBase.NBTPrimitive)this.map.get(key)).getLong();
        }
        catch (ClassCastException classcastexception)
        {
            return 0L;
        }
    }

    public float getFloat(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0.0F : ((NBTBase.NBTPrimitive)this.map.get(key)).getFloat();
        }
        catch (ClassCastException classcastexception)
        {
            return 0.0F;
        }
    }

    public double getDouble(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? 0.0D : ((NBTBase.NBTPrimitive)this.map.get(key)).getDouble();
        }
        catch (ClassCastException classcastexception)
        {
            return 0.0D;
        }
    }

    public String a_(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? "" : ((NBTBase)this.map.get(key)).a_();
        }
        catch (ClassCastException classcastexception)
        {
            return "";
        }
    }

    public byte[] getByteArray(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? new byte[0] : ((NBTTagByteArray)this.map.get(key)).getByteArray();
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(key, 7, classcastexception));
        }
    }

    public int[] getIntArray(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? new int[0] : ((NBTTagIntArray)this.map.get(key)).getIntArray();
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(key, 11, classcastexception));
        }
    }

    public NBTTagCompound getCompound(String key)
    {
        try
        {
            return !this.map.containsKey(key) ? new NBTTagCompound() : (NBTTagCompound)this.map.get(key);
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(key, 10, classcastexception));
        }
    }

    public NBTTagList getList(String key, int type)
    {
        try
        {
            if (this.b(key) != 9)
            {
                return new NBTTagList();
            }
            else
            {
                NBTTagList nbttaglist = (NBTTagList)this.map.get(key);
                return nbttaglist.size() > 0 && nbttaglist.d() != type ? new NBTTagList() : nbttaglist;
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(key, 9, classcastexception));
        }
    }

    public boolean getBoolean(String key)
    {
        return this.getByte(key) != 0;
    }

    public void remove(String key)
    {
        this.map.remove(key);
    }

    public String toString()
    {
        String s = "{";
        String s1;

        for (Iterator iterator = this.map.keySet().iterator(); iterator.hasNext(); s = s + s1 + ':' + this.map.get(s1) + ',')
        {
            s1 = (String)iterator.next();
        }

        return s + "}";
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    private CrashReport a(final String p_82581_1_, final int p_82581_2_, ClassCastException p_82581_3_)
    {
        CrashReport crashreport = CrashReport.makeCrashReport(p_82581_3_, "Reading NBT data");
        CrashReportCategory crashreportcategory = crashreport.makeCategoryDepth("Corrupt NBT tag", 1);
        crashreportcategory.addCrashSectionCallable("Tag type found", new Callable()
        {
            private static final String __OBFID = "CL_00001216";
            public String call()
            {
                return NBTBase.a[((NBTBase)NBTTagCompound.this.map.get(p_82581_1_)).getTypeId()];
            }
        });
        crashreportcategory.addCrashSectionCallable("Tag type expected", new Callable()
        {
            private static final String __OBFID = "CL_00001217";
            public String call()
            {
                return NBTBase.a[p_82581_2_];
            }
        });
        crashreportcategory.addCrashSection("Tag name", p_82581_1_);
        return crashreport;
    }

    public NBTBase clone()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound(true);
        nbttagcompound.map = new HashMap<String, NBTBase>((int)(this.map.size()*1.35)+1);
        
        for(Map.Entry<String, NBTBase> s : this.map.entrySet())
            nbttagcompound.set(s.getKey(), s.getValue().clone());

        return nbttagcompound;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)p_equals_1_;
            return this.map.entrySet().equals(nbttagcompound.map.entrySet());
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.map.hashCode();
    }

    private static void a(String name, NBTBase data, DataOutput output) throws IOException
    {
        output.writeByte(data.getTypeId());

        if (data.getTypeId() != 0)
        {
            output.writeUTF(name);
            data.write(output);
        }
    }

    private static byte a(DataInput input, NBTReadLimiter sizeTracker) throws IOException
    {
        sizeTracker.a(8);
        return input.readByte();
    }

    private static String b(DataInput input, NBTReadLimiter sizeTracker) throws IOException
    {
        return input.readUTF();
    }

    static NBTBase a(byte id, String key, DataInput input, int depth, NBTReadLimiter sizeTracker)
    {
        sizeTracker.a(32); //Forge: 4 extra bytes for the object allocation.
        NBTBase nbtbase = NBTBase.createTag(id);

        try
        {
            nbtbase.load(input, depth, sizeTracker);
            return nbtbase;
        }
        catch (IOException ioexception)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addCrashSection("Tag name", key);
            crashreportcategory.addCrashSection("Tag type", Byte.valueOf(id));
            throw new ReportedException(crashreport);
        }
    }
}
