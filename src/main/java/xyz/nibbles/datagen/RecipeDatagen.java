package xyz.nibbles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import xyz.nibbles.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends FabricRecipeProvider {
    public RecipeDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        SmithingTransformRecipeJsonBuilder.create(
                Ingredient.ofItems(ModItems.SCULK_HEART),
                Ingredient.ofItems(Items.NETHERITE_SWORD),
                Ingredient.ofItems(Items.ECHO_SHARD),
                RecipeCategory.TOOLS,
                ModItems.ECHO_SWORD
        )
                .criterion("has_echo_shard", conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(recipeExporter, getItemPath(ModItems.ECHO_SWORD) + "_smithing");

    }
}
