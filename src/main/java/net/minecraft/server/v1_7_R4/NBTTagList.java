package net.minecraft.server.v1_7_R4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase
{
    private List<NBTBase> list = new ArrayList<NBTBase>();
    private byte tagType = 0;

    void write(DataOutput output) throws IOException
    {
        if (!this.list.isEmpty())
        {
            this.tagType = ((NBTBase)this.list.get(0)).getTypeId();
        }
        else
        {
            this.tagType = 0;
        }

        output.writeByte(this.tagType);
        output.writeInt(this.list.size());

        for (int i = 0; i < this.list.size(); ++i)
        {
            ((NBTBase)this.list.get(i)).write(output);
        }
    }

    void load(DataInput input, int depth, NBTReadLimiter sizeTracker) throws IOException
    {
        if (depth > 512)
        {
            throw new RuntimeException("Tried to load NBT tag with too high complexity, depth > 512");
        }
        else
        {
            sizeTracker.a(8L);
            this.tagType = input.readByte();
            sizeTracker.a(32); //Forge: Count the length as well
            int j = input.readInt();
            this.list = new ArrayList();

            for (int k = 0; k < j; ++k)
            {
                sizeTracker.a(32); //Forge: 4 extra bytes for the object allocation.
                NBTBase nbtbase = NBTBase.createTag(this.tagType);
                nbtbase.load(input, depth + 1, sizeTracker);
                this.list.add(nbtbase);
            }
        }
    }

    public byte getTypeId()
    {
        return (byte)9;
    }

    public String toString()
    {
        String s = "[";
        int i = 0;

        for (Iterator iterator = this.list.iterator(); iterator.hasNext(); ++i)
        {
            NBTBase nbtbase = (NBTBase)iterator.next();
            s = s + "" + i + ':' + nbtbase + ',';
        }

        return s + "]";
    }

    public void add(NBTBase p_74742_1_)
    {
        if (this.tagType == 0)
        {
            this.tagType = p_74742_1_.getTypeId();
        }
        else if (this.tagType != p_74742_1_.getTypeId())
        {
            System.err.println("WARNING: Adding mismatching tag types to tag list");
            return;
        }

        this.list.add(p_74742_1_);
    }

    public void setTag(int i, NBTBase p_150304_2_)
    {
        if (i >= 0 && i < this.list.size())
        {
            if (this.tagType == 0)
            {
                this.tagType = p_150304_2_.getTypeId();
            }
            else if (this.tagType != p_150304_2_.getTypeId())
            {
                System.err.println("WARNING: Adding mismatching tag types to tag list");
                return;
            }

            this.list.set(i, p_150304_2_);
        }
        else
        {
            System.err.println("WARNING: index out of bounds to set tag in tag list");
        }
    }

    public NBTBase removeTag(int i)
    {
        return (NBTBase)this.list.remove(i);
    }

    public NBTTagCompound get(int i)
    {
        if (i >= 0 && i < this.list.size())
        {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 10 ? (NBTTagCompound)nbtbase : new NBTTagCompound();
        }
        else
        {
            return new NBTTagCompound();
        }
    }

    public int[] c(int i)
    {
        if (i >= 0 && i < this.list.size())
        {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 11 ? ((NBTTagIntArray)nbtbase).getIntArray() : new int[0];
        }
        else
        {
            return new int[0];
        }
    }

    public double d(int i)
    {
        if (i >= 0 && i < this.list.size())
        {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 6 ? ((NBTTagDouble)nbtbase).getDouble() : 0.0D;
        }
        else
        {
            return 0.0D;
        }
    }

    public float e(int i)
    {
        if (i >= 0 && i < this.list.size())
        {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 5 ? ((NBTTagFloat)nbtbase).getFloat() : 0.0F;
        }
        else
        {
            return 0.0F;
        }
    }

    public String a_(int i)
    {
        if (i >= 0 && i < this.list.size())
        {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 8 ? nbtbase.a_() : nbtbase.toString();
        }
        else
        {
            return "";
        }
    }

    public int size()
    {
        return this.list.size();
    }

    public NBTBase clone()
    {
        NBTTagList NBTTagList = new NBTTagList();
        NBTTagList.tagType = this.tagType;
        if ( NBTTagList.list instanceof ArrayList ) // Thermos, ensure we dont create arrays to then delete them
        	((ArrayList<NBTBase>)NBTTagList.list).ensureCapacity(this.list.size());
        
        for(NBTBase nbtbase : this.list)
        {
            NBTBase nbtbase1 = nbtbase.clone();
            NBTTagList.list.add(nbtbase1);
        }

        return NBTTagList;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagList NBTTagList = (NBTTagList)p_equals_1_;

            if (this.tagType == NBTTagList.tagType)
            {
                return this.list.equals(NBTTagList.list);
            }
        }

        return false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.list.hashCode();
    }

    public int d()
    {
        return this.tagType;
    }
}
