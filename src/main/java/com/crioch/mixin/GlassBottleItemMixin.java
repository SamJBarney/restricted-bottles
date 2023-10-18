package com.crioch.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.item.GlassBottleItem;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {
	@Redirect(at = @At( value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;")
	private boolean checkIfUseable(FluidState state, TagKey<Fluid> key) {
		BlockState blockState = state.getBlockState();
		Block block = blockState.getBlock();
		return !blockState.contains(Properties.WATERLOGGED) && !block.equals(Blocks.WATER) && !state.isIn(key);
	}
}