package net.reikeb.electrona.events.world;

import com.google.common.collect.Lists;

import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketManager;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.electrona.Electrona;
import net.reikeb.electrona.entity.EnergeticLightningBolt;
import net.reikeb.electrona.init.EntityInit;

import java.util.*;

@Mod.EventBusSubscriber(modid = Electrona.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldTickEvent {

    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.world instanceof ServerWorld) {
                ServerChunkProvider chunkProvider = ((ServerWorld) event.world).getChunkSource();
                boolean flag = chunkProvider.level.isDebug();
                if (!flag) {
                    List<ChunkHolder> list = Lists.newArrayList(chunkProvider.chunkMap.getChunks());
                    list.forEach((p_241099_7_) -> {
                        Optional<Chunk> optional = p_241099_7_.getTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK).left();
                        if (optional.isPresent()) {
                            Optional<Chunk> optional1 = p_241099_7_.getEntityTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK).left();
                            if (optional1.isPresent()) {
                                Chunk chunk = optional1.get();
                                ChunkPos chunkpos = p_241099_7_.getPos();
                                if (!chunkProvider.chunkMap.noPlayersCloseForSpawning(chunkpos) ||
                                        chunkProvider.chunkMap.getDistanceManager().shouldForceTicks(chunkpos.toLong())) {
                                    lightnings((ServerWorld) event.world, chunk);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private static void lightnings(ServerWorld world, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        boolean flag = world.isRaining();
        int i = chunkPos.getMinBlockX();
        int j = chunkPos.getMinBlockZ();
        if (flag && world.isThundering() && world.random.nextInt(100000) == 0) {
            BlockPos pos = world.findLightingTargetAround(world.getBlockRandomPos(i, 0, j, 15));
            if (world.isRainingAt(pos)) {
                EnergeticLightningBolt energeticLightningBolt = EntityInit.ENERGETIC_LIGHTNING_BOLT_TYPE.create(world);
                if (energeticLightningBolt == null) return;
                energeticLightningBolt.moveTo(Vector3d.atBottomCenterOf(pos));
                world.addFreshEntity(energeticLightningBolt);
            }
        }
    }
}