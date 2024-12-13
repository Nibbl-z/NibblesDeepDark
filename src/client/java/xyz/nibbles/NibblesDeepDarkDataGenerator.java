package xyz.nibbles;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.recipe.Recipe;
import xyz.nibbles.datagen.ModelDatagen;
import xyz.nibbles.datagen.RecipeDatagen;

public class NibblesDeepDarkDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelDatagen::new);
		pack.addProvider(RecipeDatagen::new);
	}
}
