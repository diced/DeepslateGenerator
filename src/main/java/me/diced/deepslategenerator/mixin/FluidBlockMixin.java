package me.diced.deepslategenerator.mixin;

import me.diced.deepslategenerator.StoneGeneratedCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin {
    @Inject(
            method = "receiveNeighborFluids",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/FluidBlock;playExtinguishSound(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V"
            ),
            cancellable = true
    )
    private void deepslateGenerator$onGenerate(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        ActionResult result = StoneGeneratedCallback.EVENT.invoker().interact(world, pos);

        if(result == ActionResult.FAIL) {
            cir.cancel();
        }
    }
}
