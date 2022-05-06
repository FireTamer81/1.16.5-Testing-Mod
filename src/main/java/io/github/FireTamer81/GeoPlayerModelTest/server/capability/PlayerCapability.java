package io.github.FireTamer81.GeoPlayerModelTest.server.capability;

import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoFirstPersonRenderer;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.objects.ItemEarthTalisman;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageLeftMouseDown;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageLeftMouseUp;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageRightMouseDown;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageRightMouseUp;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.power.Power;
import io.github.FireTamer81.GeoPlayerModelTest.server.power.PowerGeomancy;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.LogicalSide;

public class PlayerCapability {
    public interface IPlayerCapability {
        INBT writeNBT();

        void readNBT(INBT nbt);

        Power[] getPowers();

        void tick(TickEvent.PlayerTickEvent event);

        void addedToWorld(EntityJoinWorldEvent event);

        boolean isVerticalSwing();

        void setVerticalSwing(boolean verticalSwing);

        int getUntilSunstrike();

        void setUntilSunstrike(int untilSunstrike);

        int getUntilAxeSwing();

        void setUntilAxeSwing(int untilAxeSwing);

        void setAxeCanAttack(boolean axeCanAttack);

        boolean getAxeCanAttack();

        boolean isMouseRightDown();

        void setMouseRightDown(boolean mouseRightDown);

        boolean isMouseLeftDown();

        void setMouseLeftDown(boolean mouseLeftDown);

        boolean isPrevSneaking();

        void setPrevSneaking(boolean prevSneaking);

        int getTribeCircleTick();

        void setTribeCircleTick(int tribeCircleTick);

        int getTribePackRadius();

        void setTribePackRadius(int tribePackRadius);

        Vector3d getPrevMotion();

        PowerGeomancy getGeomancy();

        void setUsingSolarBeam(boolean b);

        boolean getUsingSolarBeam();

        float getPrevCooledAttackStrength();

        void setPrevCooledAttackStrength(float cooledAttackStrength);

        @OnlyIn(Dist.CLIENT)
        GeckoPlayer.GeckoPlayerThirdPerson getGeckoPlayer();
    }



    public static class PlayerCapabilityImp implements IPlayerCapability {
        public boolean verticalSwing = false;
        public int untilSunstrike = 0;
        public int untilAxeSwing = 0;
        private int prevTime;
        private int time;
        public boolean mouseRightDown = false;
        public boolean mouseLeftDown = false;
        public boolean prevSneaking;
        private float prevCooledAttackStrength;

        public int tribeCircleTick;
        public int tribePackRadius = 3;

        @OnlyIn(Dist.CLIENT)
        private GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer;

        public boolean isVerticalSwing() {
            return verticalSwing;
        }

        public void setVerticalSwing(boolean verticalSwing) {
            this.verticalSwing = verticalSwing;
        }

        public int getUntilSunstrike() {
            return untilSunstrike;
        }

        public void setUntilSunstrike(int untilSunstrike) {
            this.untilSunstrike = untilSunstrike;
        }

        public int getUntilAxeSwing() {
            return untilAxeSwing;
        }

        public void setUntilAxeSwing(int untilAxeSwing) {
            this.untilAxeSwing = untilAxeSwing;
        }

        public void setAxeCanAttack(boolean axeCanAttack) {
            this.axeCanAttack = axeCanAttack;
        }

        public boolean getAxeCanAttack() {
            return axeCanAttack;
        }

        public boolean isMouseRightDown() {
            return mouseRightDown;
        }

        public void setMouseRightDown(boolean mouseRightDown) {
            this.mouseRightDown = mouseRightDown;
        }

        public boolean isMouseLeftDown() {
            return mouseLeftDown;
        }

        public void setMouseLeftDown(boolean mouseLeftDown) {
            this.mouseLeftDown = mouseLeftDown;
        }

        public boolean isPrevSneaking() {
            return prevSneaking;
        }

        public void setPrevSneaking(boolean prevSneaking) {
            this.prevSneaking = prevSneaking;
        }

        public int getTribeCircleTick() {
            return tribeCircleTick;
        }

        public void setTribeCircleTick(int tribeCircleTick) {
            this.tribeCircleTick = tribeCircleTick;
        }

        public int getTribePackRadius() {
            return tribePackRadius;
        }

        public void setTribePackRadius(int tribePackRadius) {
            this.tribePackRadius = tribePackRadius;
        }

        public Vector3d getPrevMotion() {
            return prevMotion;
        }

        public PowerGeomancy getGeomancy() {
            return geomancy;
        }

        public void setUsingSolarBeam(boolean b) { this.usingSolarBeam = b; }

        public boolean getUsingSolarBeam() { return this.usingSolarBeam; }

        @Override
        public float getPrevCooledAttackStrength() {
            return prevCooledAttackStrength;
        }

        @Override
        public void setPrevCooledAttackStrength(float cooledAttackStrength) {
            prevCooledAttackStrength = cooledAttackStrength;
        }

        @OnlyIn(Dist.CLIENT)
        public GeckoPlayer.GeckoPlayerThirdPerson getGeckoPlayer() {
            return geckoPlayer;
        }

        private boolean usingSolarBeam;

        public boolean axeCanAttack;

        public Vector3d prevMotion;

        public PowerGeomancy geomancy = new PowerGeomancy(this);
        public Power[] powers = new Power[]{geomancy};

        @Override
        public void addedToWorld(EntityJoinWorldEvent event) {
            if (event.getWorld().isRemote) {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                geckoPlayer = new GeckoPlayer.GeckoPlayerThirdPerson(player);
                if (event.getEntity() == Minecraft.getInstance().player) GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON = new GeckoPlayer.GeckoPlayerFirstPerson(player);
            }
        }

        public void tick(TickEvent.PlayerTickEvent event) {
            PlayerEntity player = event.player;

            prevMotion = player.getPositionVec().subtract(new Vector3d(player.prevPosX, player.prevPosY, player.prevPosZ));
            prevTime = time;
            if (untilSunstrike > 0) {
                untilSunstrike--;
            }
            if (untilAxeSwing > 0) {
                untilAxeSwing--;
            }

            if (event.side == LogicalSide.SERVER) {
                for (ItemStack itemStack : event.player.inventory.mainInventory) {
                    if (itemStack.getItem() instanceof ItemEarthTalisman)
                        player.addPotionEffect(new EffectInstance(EffectHandler.GEOMANCY, 20, 0, false, false));
                }
                if (player.getHeldItemOffhand().getItem() instanceof ItemEarthTalisman)
                    player.addPotionEffect(new EffectInstance(EffectHandler.GEOMANCY, 20, 0, false, false));

            }

            Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
            if (iceBreathAbility != null && !iceBreathAbility.isUsing()) {
                for (ItemStack stack : player.inventory.mainInventory) {
                    restoreIceCrystalStack(player, stack);
                }
                for (ItemStack stack : player.inventory.offHandInventory) {
                    restoreIceCrystalStack(player, stack);
                }
            }

            useIceCrystalStack(player);

            if (event.side == LogicalSide.CLIENT) {
                if (Minecraft.getInstance().gameSettings.keyBindAttack.isKeyDown() && !mouseLeftDown) {
                    mouseLeftDown = true;
                    TestModMain.NETWORK.sendToServer(new MessageLeftMouseDown());
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onLeftMouseDown(player);
                    }
                    AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                    if (abilityCapability != null) {
                        for (Ability ability : abilityCapability.getAbilities()) {
                            ability.onLeftMouseDown(player);
                        }
                    }
                }
                if (Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown() && !mouseRightDown) {
                    mouseRightDown = true;
                    TestModMain.NETWORK.sendToServer(new MessageRightMouseDown());
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onRightMouseDown(player);
                    }
                    AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                    if (abilityCapability != null) {
                        for (Ability ability : abilityCapability.getAbilities()) {
                            ability.onRightMouseDown(player);
                        }
                    }
                }
                if (!Minecraft.getInstance().gameSettings.keyBindAttack.isKeyDown() && mouseLeftDown) {
                    mouseLeftDown = false;
                    TestModMain.NETWORK.sendToServer(new MessageLeftMouseUp());
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onLeftMouseUp(player);
                    }
                    AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                    if (abilityCapability != null) {
                        for (Ability ability : abilityCapability.getAbilities()) {
                            ability.onLeftMouseUp(player);
                        }
                    }
                }
                if (!Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown() && mouseRightDown) {
                    mouseRightDown = false;
                    TestModMain.NETWORK.sendToServer(new MessageRightMouseUp());
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onRightMouseUp(player);
                    }
                    AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                    if (abilityCapability != null) {
                        for (Ability ability : abilityCapability.getAbilities()) {
                            ability.onRightMouseUp(player);
                        }
                    }
                }
            }

            if (player.isSneaking() && !prevSneaking) {
                for (int i = 0; i < powers.length; i++) {
                    powers[i].onSneakDown(player);
                }
                AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                if (abilityCapability != null) {
                    for (Ability ability : abilityCapability.getAbilities()) {
                        ability.onSneakDown(player);
                    }
                }
            }
            else if (!player.isSneaking() && prevSneaking) {
                for (int i = 0; i < powers.length; i++) {
                    powers[i].onSneakUp(player);
                }
                AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                if (abilityCapability != null) {
                    for (Ability ability : abilityCapability.getAbilities()) {
                        ability.onSneakUp(player);
                    }
                }
            }
            prevSneaking = player.isSneaking();
        }

        private void restoreIceCrystalStack(PlayerEntity entity, ItemStack stack) {
            if (stack.getItem() == ItemHandler.ICE_CRYSTAL) {
                if (!ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
                    stack.setDamage(Math.max(stack.getDamage() - 1, 0));
                }
            }
        }

        private void useIceCrystalStack(PlayerEntity player) {
            ItemStack stack = player.getActiveItemStack();
            if (stack.getItem() == ItemHandler.ICE_CRYSTAL) {
                Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
                if (iceBreathAbility != null && iceBreathAbility.isUsing()) {
                    Hand handIn = player.getActiveHand();
                    if (stack.getDamage() + 5 < stack.getMaxDamage()) {
                        stack.damageItem(5, player, p -> p.sendBreakAnimation(handIn));
                    }
                    else {
                        if (ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
                            stack.damageItem(5, player, p -> p.sendBreakAnimation(handIn));
                        }
                        iceBreathAbility.end();
                    }
                }
            }
        }

        public int getTick() {
            return time;
        }

        public void decrementTime() {
            time--;
        }
                public Power[] getPowers() {
            return powers;
        }

        @Override
        public INBT writeNBT() {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("untilSunstrike", untilSunstrike);
            compound.putInt("untilAxeSwing", untilAxeSwing);
            compound.putInt("prevTime", prevTime);
            compound.putInt("time", time);
            return compound;
        }

        @Override
        public void readNBT(INBT nbt) {
            CompoundNBT compound = (CompoundNBT) nbt;
            untilSunstrike = compound.getInt("untilSunstrike");
            untilAxeSwing = compound.getInt("untilAxeSwing");
            prevTime = compound.getInt("prevTime");
            time = compound.getInt("time");
        }
    }



    public static class PlayerStorage implements Capability.IStorage<IPlayerCapability> {
        @Override
        public INBT writeNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side) {
            return instance.writeNBT();
        }

        @Override
        public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side, INBT nbt) {
            instance.readNBT(nbt);
        }
    }



    public static class PlayerProvider implements ICapabilitySerializable<INBT> {
        @CapabilityInject(IPlayerCapability.class)
        public static Capability<IPlayerCapability> PLAYER_CAPABILITY = null;

        private final LazyOptional<IPlayerCapability> instance = LazyOptional.of(PLAYER_CAPABILITY::getDefaultInstance);

        @Override
        public INBT serializeNBT() {
            return PLAYER_CAPABILITY.getStorage().writeNBT(
                    PLAYER_CAPABILITY,
                    this.instance.orElseThrow(() ->
                            new IllegalArgumentException("Lazy Optional all messed up")),
                    null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            PLAYER_CAPABILITY.getStorage().readNBT(
                    PLAYER_CAPABILITY,
                    this.instance.orElseThrow(() ->
                            new IllegalArgumentException("Lazy Optional all messed up")),
                    null,
                    nbt
            );
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == PLAYER_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }
    }
}
