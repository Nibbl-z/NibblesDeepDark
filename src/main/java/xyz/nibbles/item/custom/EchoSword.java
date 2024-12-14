package xyz.nibbles.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.server.command.ParticleCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import org.slf4j.LoggerFactory;
import xyz.nibbles.NibblesDeepDark;

public class EchoSword extends SwordItem {
    public EchoSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //Random chance = Random.create();

        //int generatedChance = chance.nextInt(5);

        NibblesDeepDark.LOGGER.info(String.valueOf(attacker.getWorld().isClient()));



        //if (generatedChance == 1) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 6, 1));
        target.takeKnockback(1.5F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        if (!attacker.getWorld().isClient()) {
           attacker.getWorld().playSound(null, attacker.getBlockPos(),
                   SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, SoundCategory.PLAYERS, 1f, 1f);

           ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
           serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL,
                   target.getX(), target.getY(), target.getZ(), 20, 0.0, -1.0, 0.0, 0.1);
        }





        return super.postHit(stack, target, attacker);
    }
}
