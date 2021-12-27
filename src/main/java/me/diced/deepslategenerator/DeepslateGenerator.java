package me.diced.deepslategenerator;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ActionResult;

import java.util.Random;

public class DeepslateGenerator implements ModInitializer {
    @Override
    public void onInitialize() {
        StoneGeneratedCallback.EVENT.register((world, pos) -> {
            int y = pos.getY();
            if (y > 7) {
                return ActionResult.PASS;
            }
            if (y > 0 && (new Random()).nextInt(8) < y) {
                return ActionResult.PASS;
            }
            Block replaceThis = world.getBlockState(pos).getBlock();

            if (replaceThis == Blocks.STONE) {
                world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState());
            } else if (replaceThis == Blocks.COBBLESTONE) {
                world.setBlockState(pos, Blocks.COBBLED_DEEPSLATE.getDefaultState());
            }
            return ActionResult.PASS;
        });
    }
}
