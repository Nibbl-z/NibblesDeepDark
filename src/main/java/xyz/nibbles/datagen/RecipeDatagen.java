package xyz.nibbles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
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

    public void offerEchoUpgradeRecipe(RecipeExporter recipeExporter, Item input, Item result) {
        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.SCULK_HEART),
                        Ingredient.ofItems(input),
                        Ingredient.ofItems(Items.ECHO_SHARD),
                        RecipeCategory.TOOLS,
                        result
                )
                .criterion("has_echo_shard", conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(recipeExporter, getItemPath(result) + "_smithing");
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        offerEchoUpgradeRecipe(recipeExporter, Items.NETHERITE_SWORD, ModItems.ECHO_SWORD);
        offerEchoUpgradeRecipe(recipeExporter, Items.NETHERITE_AXE, ModItems.ECHO_AXE);
    }
}
