package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLGBucketEvent extends AbstractTimedEvent {
    private Map<Level, List<BlockPos>> placedWaterSourcePositions = new HashMap<>();

    @Override
    public void tick() {
        super.tick();
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(this::doTickForPlayer);

        if(getTickCount() % 20 == 0)
            removeAllPlacedWaterSources();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        super.tickClient();
        doTickForPlayer(Minecraft.getInstance().player);
        if(getTickCount() % 20 == 0)
            removeAllPlacedWaterSources();
    }

    @Override
    public void end() {
        super.end();
        removeAllPlacedWaterSources();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        removeAllPlacedWaterSources();
    }

    private void removeAllPlacedWaterSources() {
        placedWaterSourcePositions.keySet().forEach(level -> {
            List<BlockPos> positions = placedWaterSourcePositions.get(level);
            positions.forEach(pos -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
            positions.clear();
        });
    }

    private void doTickForPlayer(Player player) {
        Level level = player.level();
        BlockPos posDown = player.blockPosition().below();
        BlockState state = level.getBlockState(posDown);

        if(state.canBeReplaced()) {
            state = level.getBlockState(posDown.below());
            if(!state.canBeReplaced()) {
                level.setBlockAndUpdate(posDown, Blocks.WATER.defaultBlockState());
                addPositionToList(level, posDown);
            }
        }

    }

    private void addPositionToList(Level level, BlockPos posDown) {
        if(!placedWaterSourcePositions.containsKey(level))
            placedWaterSourcePositions.put(level, new ArrayList<>());
        placedWaterSourcePositions.get(level).add(posDown);
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
