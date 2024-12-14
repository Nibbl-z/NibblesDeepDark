package xyz.nibbles.item;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class EchoMaterial implements ToolMaterial {
    public static final EchoMaterial INSTANCE = new EchoMaterial();


    @Override
    public int getDurability() {
        return 3031;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 11.0F;
    }

    @Override
    public float getAttackDamage() {
        return 5.0F;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantability() {
        return 17;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.ECHO_SHARD);
    }
}
