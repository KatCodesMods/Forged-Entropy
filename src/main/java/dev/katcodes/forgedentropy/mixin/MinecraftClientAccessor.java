package dev.katcodes.forgedentropy.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface MinecraftClientAccessor {

    @Accessor
    public void setMissTime(int missTime);

    @Accessor
    public int getMissTime();

    @Invoker
    public boolean callStartAttack();

    @Invoker
    public void callContinueAttack(boolean leftClick);
}
