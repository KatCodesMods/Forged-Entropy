package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;

public class RainbowPathEvent extends AbstractTimedEvent {
    private static ArrayList<Block> _rainbowBlocks = new ArrayList<Block>() {
        {
            add(Blocks.RED_CONCRETE);
            add(Blocks.ORANGE_CONCRETE);
            add(Blocks.YELLOW_CONCRETE);
            add(Blocks.GREEN_CONCRETE);
            add(Blocks.LIGHT_BLUE_CONCRETE);
            add(Blocks.BLUE_CONCRETE);
            add(Blocks.PURPLE_CONCRETE);
        }
    };

    private HashMap<ServerPlayer, Integer> _playerStates = new HashMap<>();

    @Override
    public void init() {
        super.init();
        _playerStates.clear();
    }

    @Override
    public void tick() {

        for(var player: ForgedEntropyMod.eventHandler.getActivePlayers()) {
            var playerState = 0;
            if (_playerStates.containsKey(player)) {
                playerState = _playerStates.get(player);
            }
            var level = player.level();
            var blockPos = player.blockPosition().offset(0, -1, 0);
            var state = level.getBlockState(blockPos);
            if (state.is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
                continue;
            if (state.getBlock().equals(_rainbowBlocks.get(playerState % _rainbowBlocks.size())))
                continue;
            playerState++;
            level.setBlockAndUpdate(blockPos, _rainbowBlocks.get(playerState % _rainbowBlocks.size()).defaultBlockState());
            _playerStates.put(player, playerState);
        }
        super.tick();
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
