package me.diced.deepslategenerator.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin {
    @Shadow @Final public static ImmutableList<Direction> FLOW_DIRECTIONS;
    @Shadow protected abstract void playExtinguishSound(WorldAccess world, BlockPos pos);
    @Shadow @Final protected FlowableFluid fluid;

    @Redirect(method = "receiveNeighborFluids", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FluidBlock;playExtinguishSound(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onGenerate(FluidBlock block, WorldAccess world, BlockPos pos) {
        if (fluid.isIn(FluidTags.LAVA)) {
            boolean bl = world.getBlockState(pos.down()).isOf(Blocks.SOUL_SOIL);
            UnmodifiableIterator var5 = FLOW_DIRECTIONS.iterator();

            while(var5.hasNext()) {
                Direction direction = (Direction)var5.next();
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    System.out.println(pos.getY());
                    Block b = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : (pos.getY() > 0 ? Blocks.COBBLESTONE : Blocks.DEEPSLATE);
                    world.setBlockState(pos, b.getDefaultState(), 0);
                    playExtinguishSound(world, pos);
                }

                if (bl && world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                    world.setBlockState(pos, Blocks.BASALT.getDefaultState(), 0);
                    playExtinguishSound(world, pos);
                }
            }
        }
    }
}
