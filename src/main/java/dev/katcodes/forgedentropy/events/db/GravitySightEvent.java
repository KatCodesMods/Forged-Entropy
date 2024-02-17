package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import dev.katcodes.forgedentropy.mixin.FallingBlockEntityAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class GravitySightEvent extends AbstractTimedEvent {
    private BlockPos _lastBlockInSight = null;
    private int _stareTimer = 0;

    @Override
    public void init() {
        super.init();
        _lastBlockInSight = null;
        _stareTimer = 0;


    }

    @Override
    public void tick() {

        if(tickCount%2==0) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                var rayVector = serverPlayer.getLookAngle().normalize().scale(64d);
                var fromVector = serverPlayer.getEyePosition();
                var toVector = fromVector.add(rayVector);

                var box = new AABB(serverPlayer.position().add(64,64,64),serverPlayer.position().subtract(64,64,64));
                var hitRes = ProjectileUtil.getEntityHitResult(serverPlayer,fromVector,toVector,box, x -> true,2048);
                if(hitRes!=null) {
                    var direction = serverPlayer.getLookAngle().normalize().scale(-1);
                    var entity = hitRes.getEntity();
                    entity.setOnGround(false);
                    entity.setDeltaMovement(direction);
                } else if(_lastBlockInSight==null || _stareTimer < 10) {
                    var hitRes2 = serverPlayer.pick(64,1,false);
                    if(hitRes2.getType()== HitResult.Type.BLOCK) {
                        var blockHitRes = (BlockHitResult)hitRes2;
                        var blockPos = blockHitRes.getBlockPos();
                        if(blockPos.equals(_lastBlockInSight))
                            _stareTimer++;
                        else
                        {
                            _lastBlockInSight=blockPos;
                            _stareTimer=1;
                        }
                    }
                } else {
                    var world = serverPlayer.level();
                    var blockState = world.getBlockState(_lastBlockInSight);
                    if(!blockState.is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS)) {
                        world.setBlockAndUpdate(_lastBlockInSight, Blocks.AIR.defaultBlockState());
                        var direction = serverPlayer.getLookAngle().normalize().scale(-1);
                        var fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK,world);
                        var fallingBlockAccessor = (FallingBlockEntityAccessor)fallingBlockEntity;
                        fallingBlockAccessor.setBlockState(blockState);
                        fallingBlockEntity.blocksBuilding=true;
                        fallingBlockEntity.setPos(new Vec3(_lastBlockInSight.getX()+.5,_lastBlockInSight.getY(),_lastBlockInSight.getZ()+.5));
                        fallingBlockEntity.setDeltaMovement(direction);
                        fallingBlockEntity.setStartPos(fallingBlockEntity.blockPosition());
                        world.addFreshEntity(fallingBlockEntity);
                        _lastBlockInSight=null;
                        _stareTimer=0;
                    }
                }
            });
        }
        ForgedEntropyMod.LOGGER.info("Running tavity sight with tick: {}",tickCount);
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

    @Override
    public String type() {
        return "sight";
    }
}
