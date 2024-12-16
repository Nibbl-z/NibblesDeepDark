package xyz.nibbles.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.nibbles.NibblesDeepDark;
import xyz.nibbles.item.custom.EchoAxe;
import xyz.nibbles.item.custom.EchoBow;
import xyz.nibbles.item.custom.EchoCrossbow;
import xyz.nibbles.item.custom.EchoSword;

public class ModItems {
    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(NibblesDeepDark.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item SCULK_HEART = register(
            new Item(new Item.Settings()),
            "sculk_heart"
    );

    public static final Item SCULK_DUST = register(
            new Item(new Item.Settings()),
            "sculk_dust"
    );

    public static final Item SCULK_ARROW = register(
            new ArrowItem(new Item.Settings()),
            "sculk_arrow"
    );

    public static final Item ECHO_SWORD = register(
            new EchoSword(EchoMaterial.INSTANCE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(
                            EchoMaterial.INSTANCE, 4, -2.5F
                    ))),
            "echo_sword"
    );

    public static final Item ECHO_AXE = register(
            new EchoAxe(EchoMaterial.INSTANCE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(
                            EchoMaterial.INSTANCE, 6, -3F
                    ))),
            "echo_axe"
    );

    public static final Item ECHO_BOW = register(
            new EchoBow(new Item.Settings().maxDamage(550)),
            "echo_bow"
    );

    public static final Item ECHO_CROSSBOW = register(
            new EchoCrossbow(new Item.Settings().maxDamage(550)),
            "echo_crossbow"
    );

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.SCULK_HEART));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.SCULK_DUST));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.ECHO_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.ECHO_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.ECHO_BOW));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.ECHO_CROSSBOW));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.SCULK_ARROW));
    }
}
