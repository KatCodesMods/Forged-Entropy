package dev.katcodes.forgedentropy.client.UIStyles;


import dev.katcodes.forgedentropy.mixin.BossBarAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

import java.util.UUID;

public class MinecraftUIRenderer  implements UIRenderer {
    net.minecraft.client.gui.components.LerpingBossEvent bar;

    public MinecraftUIRenderer() {
        UUID uuid= UUID.randomUUID();
        this.bar=new LerpingBossEvent(uuid, Component.translatable("entropy.title"),0, BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_20,false,false,false);
        ((BossBarAccessor) Minecraft.getInstance().gui.getBossOverlay()).getEvents().put(uuid,bar);
    }
    @Override
    public void renderTimer(GuiGraphics guiGraphics, int width, double time, double timerDuration) {
        this.bar.setProgress((float) (time/timerDuration));

    }
    //private final BossB
}
