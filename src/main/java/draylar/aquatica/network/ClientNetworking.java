package draylar.aquatica.network;

import draylar.aquatica.entity.ChairEntity;
import draylar.aquatica.registry.AquaticaEntities;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.UUID;

public class ClientNetworking {

    private ClientNetworking() {

    }

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ChairEntity.PACKET_ID, (client, playHandler, packet, sender) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();
            int id = packet.readInt();
            UUID uuid = packet.readUuid();

            client.execute(() -> {
                ChairEntity chair = new ChairEntity(AquaticaEntities.CHAIR, client.world);
                chair.setPos(x, y, z);
                chair.updateTrackedPosition(x, y, z);
                chair.requestTeleport(x, y, z);
                chair.setUuid(uuid);
                client.world.addEntity(id, chair);
            });
        });
    }
}
