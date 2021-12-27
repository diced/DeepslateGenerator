package me.diced.deepslategenerator.mixin;

import me.diced.deepslategenerator.StoneGeneratedCallback;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin {
    @Inject(
            method = "flow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/LavaFluid;playExtinguishEvent(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V"
            ),
            cancellable = true
    )
    private void deepslateGenerator$onFlow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState, CallbackInfo ci) {
        ActionResult result = StoneGeneratedCallback.EVENT.invoker().interact((World) world, pos);

        if(result == ActionResult.FAIL) {
            ci.cancel();
        }
    }
}
