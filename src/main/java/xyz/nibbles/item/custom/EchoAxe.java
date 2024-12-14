package xyz.nibbles.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;

public class EchoAxe extends AxeItem {
    public EchoAxe(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (Random.create().nextInt(3) == 1) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 6 * 20, 1));
            target.takeKnockback(1.5F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            if (!attacker.getWorld().isClient()) {
                attacker.getWorld().playSound(null, attacker.getBlockPos(),
                        SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, SoundCategory.PLAYERS, 1f, 1f);

                ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
                serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL,
                        target.getX(), target.getY(), target.getZ(), 20, 0.0, -1.0, 0.0, 0.1);
            }
        }

        return super.postHit(stack, target, attacker);
    }
}
