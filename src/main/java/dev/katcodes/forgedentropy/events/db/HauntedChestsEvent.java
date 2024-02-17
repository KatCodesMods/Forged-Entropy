package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class HauntedChestsEvent extends AbstractTimedEvent {
    private List<ChestBlockEntity> openedChests = new ArrayList<>();

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        super.tickClient();
        if(tickCount % 20 == 0) {
            Player player = Minecraft.getInstance().player;
            BlockPos.MutableBlockPos pos = player.blockPosition().mutable();

            Level world = player.level();

            boolean chestOpened = false;
            boolean chestClosed = false;

            for(int x = -16; x<=16; x+=16) {
                for(int z=-16;z<=16;z+=16) {
                    pos.set(pos.getX()+x,pos.getY(),pos.getZ()+z);
                    LevelChunk chunk = world.getChunkAt(pos);
                    for(BlockEntity be: chunk.getBlockEntitiesPos().stream().map(chunk::getBlockEntity).toList()) {
                        if(player.getRandom().nextInt(10)>=7 && be instanceof ChestBlockEntity chest) {
                            if(openedChests.contains(chest)) {
                                chest.stopOpen(player);
                                openedChests.remove(chest);
                                chestClosed=true;
                            } else {
                                chest.startOpen(player);
                                openedChests.add(chest);
                                chestOpened = true;
                            }
                        }
                    }
                }
            }
            if(chestOpened)
                player.playSound(SoundEvents.CHEST_OPEN,1,1);
            if(chestClosed)
                player.playSound(SoundEvents.CHEST_CLOSE,1,1);
        }
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        Player player = Minecraft.getInstance().player;
        if(!openedChests.isEmpty())
        {
            openedChests.forEach(chest -> chest.stopOpen(player));
            openedChests.clear();
            player.playSound(SoundEvents.CHEST_CLOSE,1,1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
