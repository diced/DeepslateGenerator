package me.diced.deepslategenerator;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// https://fabricmc.net/wiki/tutorial:events
public interface CobblestoneGeneratedCallback {
    Event<CobblestoneGeneratedCallback> EVENT = EventFactory.createArrayBacked(CobblestoneGeneratedCallback.class,
            (listeners) -> (world, pos) -> {
                for (CobblestoneGeneratedCallback listener : listeners) {
                    ActionResult result = listener.interact(world, pos);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(World world, BlockPos pos);
}
