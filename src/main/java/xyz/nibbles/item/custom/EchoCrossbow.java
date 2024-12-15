package xyz.nibbles.item.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EchoCrossbow extends CrossbowItem {
    public EchoCrossbow(Settings settings) {
        super(settings);
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        ServerWorld world = (ServerWorld) shooter.getWorld();

        Vec3d shooterPos = shooter.getCameraPosVec(1.0F);
        Vec3d direction = shooter.getRotationVec(1.0F).multiply(20.0 * (speed / 3.0F));
        Vec3d raycastEnd = shooterPos.add(direction);

        RaycastContext context = new RaycastContext(shooterPos, raycastEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, shooter);
        HitResult hitResult = world.raycast(context);

        int i = MathHelper.floor(hitResult.squaredDistanceTo(shooter));

        ItemStack mainHandStack = shooter.getMainHandStack();
        ItemStack offHandStack = shooter.getOffHandStack();
        ItemStack bowStack = mainHandStack.getItem() instanceof BowItem ? mainHandStack : offHandStack.getItem() instanceof BowItem ? offHandStack : ItemStack.EMPTY;

        shooter.getWorld().playSound(null, shooter.getBlockPos(),
                SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 2f, 1f);

        RegistryEntry<Enchantment> power = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.POWER);
        RegistryEntry<Enchantment> flame = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FLAME);
        RegistryEntry<Enchantment> punch = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.PUNCH);

        for (int j = 1; j < i; j++) {
            Vec3d particlePos = shooterPos.add(direction.normalize().multiply(j));
            Box boundingBox = new Box(particlePos.subtract(1.0,1.0,1.0), particlePos.add(1.0, 1.0, 1.0));
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, boundingBox, entity -> !entity.equals(shooter));

            DamageSource damageSource = new DamageSource(
                    world.getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(DamageTypes.PLAYER_ATTACK));

            for (LivingEntity entity : entities) {
                entity.damage(damageSource, (float)
                        (2.0F + EnchantmentHelper.getLevel(power, bowStack) / 2.0) * (speed / 3.0F)
                );

                if (EnchantmentHelper.getLevel(flame, bowStack) > 0) {
                    entity.setOnFireFor(5);
                }

                entity.takeKnockback(1.5F + (EnchantmentHelper.getLevel(punch, bowStack) * 0.5F), shooter.getX() - entity.getX(), shooter.getZ() - entity.getZ());
            }

            world.spawnParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
        }

        projectile.remove(Entity.RemovalReason.DISCARDED);
    }
}
