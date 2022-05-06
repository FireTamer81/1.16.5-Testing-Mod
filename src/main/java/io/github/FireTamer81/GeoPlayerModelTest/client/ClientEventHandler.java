package io.github.FireTamer81.GeoPlayerModelTest.client;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelGeckoPlayerFirstPerson;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelGeckoPlayerThirdPerson;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoFirstPersonRenderer;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoRenderPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityCameraShake;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.objects.ItemBlowgun;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.LogicalSide;

@OnlyIn(Dist.CLIENT)
public enum ClientEventHandler {
    INSTANCE;

    private static final ResourceLocation FROZEN_BLUR = new ResourceLocation(TestModMain.MODID, "textures/gui/frozenblur.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHandRender(RenderHandEvent event) {
        if (!ConfigHandler.CLIENT.customPlayerAnims.get()) return;
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        boolean shouldAnimate = false;
        AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
        if (abilityCapability != null) shouldAnimate = abilityCapability.getActiveAbility() != null;
//        shouldAnimate = (player.ticksExisted / 20) % 2 == 0;
        if (shouldAnimate) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
            if (playerCapability != null) {
                GeckoPlayer.GeckoPlayerFirstPerson geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
                if (geckoPlayer != null) {
                    ModelGeckoPlayerFirstPerson geckoFirstPersonModel = (ModelGeckoPlayerFirstPerson) geckoPlayer.getModel();
                    GeckoFirstPersonRenderer firstPersonRenderer = (GeckoFirstPersonRenderer) geckoPlayer.getPlayerRenderer();

                    if (geckoFirstPersonModel != null && firstPersonRenderer != null) {
//                        if (!geckoFirstPersonModel.isUsingSmallArms() && ((AbstractClientPlayerEntity) player).getSkinType().equals("slim")) {
                        firstPersonRenderer.setSmallArms();
//                        }
                        event.setCanceled(geckoFirstPersonModel.resourceForModelId((AbstractClientPlayerEntity) player));

                        if (event.isCanceled()) {
                            float delta = event.getPartialTicks();
                            float f1 = MathHelper.lerp(delta, player.prevRotationPitch, player.rotationPitch);
                            firstPersonRenderer.renderItemInFirstPerson((AbstractClientPlayerEntity) player, f1, delta, event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress(), event.getMatrixStack(), event.getBuffers(), event.getLight(), geckoPlayer);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void renderLivingEvent(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (!ConfigHandler.CLIENT.customPlayerAnims.get()) return;
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player == null) return;
            float delta = event.getPartialRenderTick();
            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
//        shouldAnimate = (player.ticksExisted / 20) % 2 == 0;
            if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
                PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
                if (playerCapability != null) {
                    GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer = playerCapability.getGeckoPlayer();
                    if (geckoPlayer != null) {
                        ModelGeckoPlayerThirdPerson geckoPlayerModel = (ModelGeckoPlayerThirdPerson) geckoPlayer.getModel();
                        GeckoRenderPlayer animatedPlayerRenderer = (GeckoRenderPlayer) geckoPlayer.getPlayerRenderer();

                        if (geckoPlayerModel != null && animatedPlayerRenderer != null) {
                            if (!geckoPlayerModel.isUsingSmallArms() && ((AbstractClientPlayerEntity) player).getSkinType().equals("slim")) {
                                animatedPlayerRenderer.setSmallArms();
                            }

                            event.setCanceled(geckoPlayerModel.resourceForModelId((AbstractClientPlayerEntity) player));

                            if (event.isCanceled()) {
                                animatedPlayerRenderer.render((AbstractClientPlayerEntity) event.getEntity(), event.getEntity().rotationYaw, delta, event.getMatrixStack(), event.getBuffers(), event.getLight(), geckoPlayer);
                            }
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void updateFOV(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();
        if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof ItemBlowgun) {
            int i = player.getItemInUseMaxCount();
            float f1 = (float)i / 5.0F;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 = f1 * f1;
            }

            event.setNewfov(1.0F - f1 * 0.15F);
        }
    }

    @SubscribeEvent
    public void onSetupCamera(EntityViewRenderEvent.CameraSetup event) {
        PlayerEntity player = Minecraft.getInstance().player;
        float delta = Minecraft.getInstance().getRenderPartialTicks();
        float ticksExistedDelta = player.ticksExisted + delta;
        if (player != null) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
            if (playerCapability != null && playerCapability.getGeomancy().canUse(player) && playerCapability.getGeomancy().isSpawningBoulder() && playerCapability.getGeomancy().getSpawnBoulderCharge() > 2) {
                Vector3d lookPos = playerCapability.getGeomancy().getLookPos();
                Vector3d playerEyes = player.getEyePosition(delta);
                Vector3d vec = playerEyes.subtract(lookPos).normalize();
                float yaw = (float) Math.atan2(vec.z, vec.x);
                float pitch = (float) Math.asin(vec.y);
                player.rotationYaw = (float) (yaw * 180f/Math.PI + 90);
                player.rotationPitch = (float) (pitch * 180f/Math.PI);
                player.rotationYawHead = player.rotationYaw;
                player.prevRotationYaw = player.rotationYaw;
                player.prevRotationPitch = player.rotationPitch;
                player.prevRotationYawHead = player.rotationYawHead;
                event.setPitch(pitch);
                event.setYaw(yaw);
            }

            if (ConfigHandler.CLIENT.doCameraShakes.get()) {
                float shakeAmplitude = 0;
                for (EntityCameraShake cameraShake : player.world.getEntitiesWithinAABB(EntityCameraShake.class, player.getBoundingBox().grow(20, 20, 20))) {
                    if (cameraShake.getDistance(player) < cameraShake.getRadius()) {
                        shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                    }
                }
                if (shakeAmplitude > 1.0f) shakeAmplitude = 1.0f;
                event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
                event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
                event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.player == null) {
            return;
        }
        PlayerEntity player = event.player;
        PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
        if (playerCapability != null && event.side == LogicalSide.CLIENT) {
            GeckoPlayer geckoPlayer = playerCapability.getGeckoPlayer();
            if (geckoPlayer != null) geckoPlayer.tick();
            if (player == Minecraft.getInstance().player) GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON.tick();
        }
    }
}
