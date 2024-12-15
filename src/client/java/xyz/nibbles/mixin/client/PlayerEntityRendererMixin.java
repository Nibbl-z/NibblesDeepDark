package xyz.nibbles.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nibbles.item.ModItems;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(at = @At("HEAD"), method = "getArmPose", cancellable = true)
    private static void getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!itemStack.isEmpty() && (!player.handSwinging && itemStack.isOf(ModItems.ECHO_CROSSBOW) && CrossbowItem.isCharged(itemStack))) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
            cir.cancel();
        }
    }
}
