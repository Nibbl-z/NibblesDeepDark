package xyz.nibbles.loottable;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import xyz.nibbles.item.ModItems;

public class ModLootTables {
    public static void initialize() {
        final RegistryKey<LootTable> SCULK_LOOT_TABLE_KEY = Blocks.SCULK.getLootTableKey();

        LootTableEvents.MODIFY.register(((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (registryKey.equals(SCULK_LOOT_TABLE_KEY)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.SCULK_DUST))
                        .conditionally(RandomChanceLootCondition.builder(0.5f).build());

                builder.pool(poolBuilder);
            }
        }));
    }
}
