package xyz.nibbles.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nibbles.NibblesDeepDark;
import xyz.nibbles.item.ModItems;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@Unique
	private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
		int i = arm == Arm.RIGHT ? 1 : -1;
		matrices.translate((float)i * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
	}

	@Unique
	private void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress) {
		int i = arm == Arm.RIGHT ? 1 : -1;
		float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * (45.0F + f * -20.0F)));
		float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)i * g * -20.0F));
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0F));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * -45.0F));
	}

	@Inject(at = @At("HEAD"), method = "isChargedCrossbow", cancellable = true)
	private static void isChargedCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue((stack.isOf(Items.CROSSBOW) || stack.isOf(ModItems.ECHO_CROSSBOW)) && CrossbowItem.isCharged(stack));
	}

	@Inject(at = @At("HEAD"), method = "renderFirstPersonItem")
	private void renderEchoCrossbow(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (item.getItem() == ModItems.ECHO_CROSSBOW) {
			boolean bl = hand == Hand.MAIN_HAND;
			Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
			matrices.push();

			if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
				boolean bl2 = CrossbowItem.isCharged(item);
				boolean bl3 = arm == Arm.RIGHT;
				int i = bl3 ? 1 : -1;
				if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
					this.applyEquipOffset(matrices, arm, equipProgress);
					matrices.translate((float)i * -0.4785682F, -0.094387F, 0.05731531F);
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935F));
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * 65.3F));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)i * -9.785F));
					float f = (float)item.getMaxUseTime(player) - ((float)player.getItemUseTimeLeft() - tickDelta + 1.0F);
					float g = f / (float)CrossbowItem.getPullTime(item, player);
					if (g > 1.0F) {
						g = 1.0F;
					}

					if (g > 0.1F) {
						float h = MathHelper.sin((f - 0.1F) * 1.3F);
						float j = g - 0.1F;
						float k = h * j;
						matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
					}

					matrices.translate(g * 0.0F, g * 0.0F, g * 0.04F);
					matrices.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
					matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)i * 45.0F));
				} else {
					float fx = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
					float gx = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
					float h = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
					matrices.translate((float)i * fx, gx, h);
					this.applyEquipOffset(matrices, arm, equipProgress);
					this.applySwingOffset(matrices, arm, swingProgress);
					if (bl2 && swingProgress < 0.001F && bl) {
						NibblesDeepDark.LOGGER.info("Crossbow is charged");
						matrices.translate((float)i * -0.641864F, 0.0F, 0.0F);
						matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * 10.0F));
					}
				}

				((HeldItemRenderer) (Object) this).renderItem(
						player,
						item,
						bl3 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
						!bl3,
						matrices,
						vertexConsumers,
						light
				);
			}

			matrices.pop();
		}


	}
}