package thermos.wrapper;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import org.bukkit.craftbukkit.util.LongHash;

import java.util.concurrent.ConcurrentHashMap;

public class VanillaChunkHashMap extends LongHashMap {

    /*
     * VanillaChunkHashMap implementation using ChunkHashMap
     */

    private ChunkBlockHashMap ChunkBlockHashMap;
    private Long2ObjectMap<Chunk> vanilla_sync;

    private boolean notRealFace = false;

    public VanillaChunkHashMap(ChunkBlockHashMap ChunkBlockHashMap) {

        this.ChunkBlockHashMap = ChunkBlockHashMap;
        this.vanilla_sync = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());

    }

    public VanillaChunkHashMap(ChunkBlockHashMap ChunkBlockHashMap, ConcurrentHashMap<Long, Chunk> vanilla) {

        this.ChunkBlockHashMap = ChunkBlockHashMap;

        this.vanilla_sync = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>(vanilla));

        this.notRealFace = true;

    }

    public ConcurrentHashMap<Long, Chunk> convert(Long2ObjectMap<Chunk> map) {
        return new ConcurrentHashMap<Long, Chunk>(map);
    }

    public VanillaChunkHashMap thisIsNotMyRealFace() {
        return new VanillaChunkHashMap(ChunkBlockHashMap, convert(vanilla_sync));
    }

    private long V2B(long key) {
        if (notRealFace) {
            return key;
        } else {
            return ChunkCoordIntPair.chunkXZ2Int(LongHash.msw(key), LongHash.lsw(key));
        }
    }

    public ConcurrentHashMap<Long, Chunk> rawVanilla() {
        return convert(vanilla_sync);
    }

    public ChunkBlockHashMap rawThermosMap() {
        return ChunkBlockHashMap;
    }

    public int size() {
        return this.ChunkBlockHashMap.size();
    }

    @Override
    public void add(long key, Object value) {
        if (value instanceof Chunk) {

            Chunk c = (Chunk) value;

            ChunkBlockHashMap.put(c);
            vanilla_sync.put(V2B(key), c);
        }
    }


    @Override
    public boolean containsItem(long key) {
        return vanilla_sync.containsKey(V2B(key));
    }

    @Override
    public Object getValueByKey(long key) {
        return vanilla_sync.get(V2B(key));
    }

    @Override
    public Object remove(long key) {
        Object o = vanilla_sync.remove(V2B(key));
        if (o instanceof Chunk) // Thermos - Use our special map
        {
            Chunk c = (Chunk) o;
            ChunkBlockHashMap.remove(c);
        }
        return o;
    }

}