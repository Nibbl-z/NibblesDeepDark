package xyz.nibbles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import xyz.nibbles.item.ModItems;

public class ModelDatagen extends FabricModelProvider {
    public ModelDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SCULK_HEART, Models.GENERATED);
        itemModelGenerator.register(ModItems.SCULK_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.ECHO_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ECHO_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SCULK_ARROW, Models.GENERATED);


    }
}
