package thermos;

import com.google.common.base.Charsets;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class ThermosUUIDManager {
    public static UUID getOfflineUUID(String name){
        switch (MinecraftServer.thermosConfig.uuidMode.getValue()){
            case 1:
                name=name.toLowerCase();
                break;
            case 2:
                name=name.toUpperCase();
                break;
        }
        return UUID.nameUUIDFromBytes( ( "OfflinePlayer:" + name ).getBytes( Charsets.UTF_8 ) );
    }
}