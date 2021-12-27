package me.diced.deepslategenerator;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// https://fabricmc.net/wiki/tutorial:events
public interface StoneGeneratedCallback {
    Event<StoneGeneratedCallback> EVENT = EventFactory.createArrayBacked(StoneGeneratedCallback.class,
            (listeners) -> (world, pos) -> {
                for (StoneGeneratedCallback listener : listeners) {
                    ActionResult result = listener.interact(world, pos);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(World world, BlockPos pos);
}
