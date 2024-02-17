package dev.katcodes.forgedentropy.events.db;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

import static dev.katcodes.forgedentropy.ForgedEntropyMod.MODID;

public class HerobrineEvent extends AbstractTimedEvent {
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation(MODID, "textures/vignette.png");

    Random random;
    Minecraft client;

    public HerobrineEvent() {
        random=new Random();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        client = Minecraft.getInstance();
        CurrentState.Get().customFog=true;
        client.getSoundManager().pause();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        CurrentState.Get().customFog=true;
        client.getSoundManager().stop(ForgedEntropyClient.herobrineAmbianceID, SoundSource.BLOCKS);
        client.getSoundManager().resume();
        super.endClient();
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {
        renderVignetteOverlay();
    }

    @Override
    public void tick() {
        if(getTickCount() % 20 == 0)
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                if(random.nextInt(100)>=95) {
                    serverPlayer.hurt(serverPlayer.damageSources().generic(),1);

                }
            });
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {

        Player player = Minecraft.getInstance().player;
        if(getTickCount() % 10 == 0) {
            playStepSound(getLandingPos(), player.getCommandSenderWorld().getBlockState(getLandingPos()));

        }
        if(getTickCount() % 70 ==0) {
            player.getCommandSenderWorld().playSound(player,player.blockPosition(),ForgedEntropyClient.herobrineAmbiance,SoundSource.BLOCKS,1f,0.9f);
        }
        super.tickClient();
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 1.25f);

    }


    @OnlyIn(Dist.CLIENT)
    private BlockPos getLandingPos() {
        Player player = Minecraft.getInstance().player;
        int i = Mth.floor(player.position().x);
        int j = Mth.floor(player.position().y- 0.20000000298023224D);
        int k = Mth.floor(player.position().z);
        BlockPos pos = new BlockPos(i,j,k);
        if(player.getCommandSenderWorld().getBlockState(pos).isAir()) {
            BlockPos pos2 = pos.below();
            BlockState blockState=player.getCommandSenderWorld().getBlockState(pos2);
            Block block = blockState.getBlock();
            if(block.defaultBlockState().is(BlockTags.FENCES) || block.defaultBlockState().is(BlockTags.WALLS) || block.defaultBlockState().is(BlockTags.FENCE_GATES)) {
                return pos2;
            }
        }
        return pos;
    }

    @OnlyIn(Dist.CLIENT)
    private void playStepSound(BlockPos pos, BlockState state) {
        Player player=client.player;
        if(!state.liquid()){
            BlockState blockState = player.getCommandSenderWorld().getBlockState(pos.above());
            SoundType soundType = blockState.is(BlockTags.SNOW) ? blockState.getSoundType() : state.getSoundType();
            player.playSound(soundType.getStepSound(),soundType.getVolume() * 0.25f,soundType.getPitch());

        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderVignetteOverlay() {
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO,GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
        float sin = 0.75f + Mth.abs(0.25f * Mth.sin(getTickCount() * 0.0625f));
        RenderSystem.setShaderColor(sin,sin,sin,1.0f);
        int scaledHeight = client.getWindow().getGuiScaledHeight();
        int scaledWidth = client.getWindow().getGuiScaledWidth();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,VIGNETTE_TEXTURE);
        Tesselator tesselator=Tesselator.getInstance();
        BufferBuilder bufferBuilder=tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(0.0D, scaledHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(scaledWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
    }
}
