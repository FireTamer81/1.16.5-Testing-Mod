package io.github.FireTamer81.GeoPlayerModelTest.server.item;

import io.github.FireTamer81.GeoPlayerModelTest.client.render.item.RenderEarthboreGauntlet;
import io.github.FireTamer81.GeoPlayerModelTest.server.block.BlockHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.creativetab.CreativeTabHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityDart;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.MaskType;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = TestModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TestModMain.MODID)
public class ItemHandler {
    private ItemHandler() {}

    public static final ItemFoliaathSeed FOLIAATH_SEED = null;
    public static final ItemMobRemover MOB_REMOVER = null;
    public static final ItemWroughtAxe WROUGHT_AXE = null;
    public static final ItemWroughtHelm WROUGHT_HELMET = null;
    public static final ItemBarakoaMask BARAKOA_MASK_FURY = null;
    public static final ItemBarakoaMask BARAKOA_MASK_FEAR = null;
    public static final ItemBarakoaMask BARAKOA_MASK_RAGE = null;
    public static final ItemBarakoaMask BARAKOA_MASK_BLISS = null;
    public static final ItemBarakoaMask BARAKOA_MASK_MISERY = null;
    public static final ItemBarakoaMask BARAKOA_MASK_FAITH = null;
    public static final ItemBarakoMask BARAKO_MASK = null;
    public static final ItemDart DART = null;
    public static final ItemSpear SPEAR = null;
    public static final ItemSunblockStaff SUNBLOCK_STAFF = null;
    public static final ItemBlowgun BLOWGUN = null;
    public static final ItemGrantSunsBlessing GRANT_SUNS_BLESSING = null;
    public static final ItemIceCrystal ICE_CRYSTAL = null;
    public static final ItemEarthTalisman EARTH_TALISMAN = null;
    public static final ItemCapturedGrottol CAPTURED_GROTTOL = null;
    public static final ItemGlowingJelly GLOWING_JELLY = null;
    public static final ItemNagaFang NAGA_FANG = null;
    public static final ItemNagaFangDagger NAGA_FANG_DAGGER = null;
    public static final ItemEarthboreGauntlet EARTHBORE_GAUNTLET = null;
    public static final Item LOGO = null;
    public static final MusicDiscItem PETIOLE_MUSIC_DISC = null;

    public static final SpawnEggItem FOLIAATH_SPAWN_EGG = null;
    public static final SpawnEggItem WROUGHTNAUT_SPAWN_EGG = null;
    public static final SpawnEggItem BARAKOA_SPAWN_EGG = null;
    public static final SpawnEggItem BARAKOANA_SPAWN_EGG = null;
    public static final SpawnEggItem BARAKOA_SUNBLOCKER_SPAWN_EGG = null;
    public static final SpawnEggItem BARAKO_SPAWN_EGG = null;
    public static final SpawnEggItem FROSTMAW_SPAWN_EGG = null;
    public static final SpawnEggItem GROTTOL_SPAWN_EGG = null;
    public static final SpawnEggItem LANTERN_SPAWN_EGG = null;
    public static final SpawnEggItem NAGA_SPAWN_EGG = null;
//    public static final SpawnEggItem SCULPTOR_SPAWN_EGG = null;

    private static final int BARAKOA_GREEN_COLOR = 0x748C47;
    private static final int BARAKOA_PINK_COLOR = 0xBA6656;

    public static Style TOOLTIP_STYLE = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY));

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemFoliaathSeed(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("foliaath_seed"),
                new ItemMobRemover(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("mob_remover"),
                new ItemWroughtAxe(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.UNCOMMON)).setRegistryName("wrought_axe"),
                new ItemWroughtHelm(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.UNCOMMON)).setRegistryName("wrought_helmet"),
                new ItemBarakoaMask(MaskType.FURY, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_fury"),
                new ItemBarakoaMask(MaskType.FEAR, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_fear"),
                new ItemBarakoaMask(MaskType.RAGE, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_rage"),
                new ItemBarakoaMask(MaskType.BLISS, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_bliss"),
                new ItemBarakoaMask(MaskType.MISERY, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_misery"),
                new ItemBarakoaMask(MaskType.FAITH, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_mask_faith"),
                new ItemBarakoMask(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.RARE)).setRegistryName("barako_mask"),
                new ItemDart(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("dart"),
                new ItemSpear(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1)).setRegistryName("spear"),
                new ItemSunblockStaff(new Item.Properties().maxStackSize(1)).setRegistryName("sunblock_staff"),
                new ItemBlowgun(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1).maxDamage(300)).setRegistryName("blowgun"),
                new ItemGrantSunsBlessing(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1).rarity(Rarity.EPIC)).setRegistryName("grant_suns_blessing"),
                new ItemIceCrystal(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).defaultMaxDamage(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durability.get()).rarity(Rarity.RARE)).setRegistryName("ice_crystal"),
                new ItemEarthTalisman(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1).rarity(Rarity.EPIC)).setRegistryName("earth_talisman"),
                new ItemCapturedGrottol(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1)).setRegistryName("captured_grottol"),
                new ItemGlowingJelly( new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).food(ItemGlowingJelly.GLOWING_JELLY_FOOD)).setRegistryName("glowing_jelly"),
                new ItemNagaFang(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_fang"),
                new ItemNagaFangDagger(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_fang_dagger"),
                new ItemEarthboreGauntlet(new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).defaultMaxDamage(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.durability.get()).rarity(Rarity.RARE).setISTER(() -> RenderEarthboreGauntlet::new)).setRegistryName("earthbore_gauntlet"),
                new Item(new Item.Properties()).setRegistryName("logo"),
                new MusicDiscItem(14, MMSounds.MUSIC_PETIOLE, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab).maxStackSize(1).rarity(Rarity.RARE)).setRegistryName("music_disc_petiole"),

                new ModSpawnEggItem(EntityHandler.FOLIAATH, 0x47CC3B, 0xC03BCC, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("foliaath_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.WROUGHTNAUT, 0x8C8C8C, 0xFFFFFF, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("wroughtnaut_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.BARAKOA_VILLAGER, BARAKOA_GREEN_COLOR, 0xdbdbdb, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.BARAKOANA, BARAKOA_GREEN_COLOR, 0xFAFA78, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoana_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.BARAKOAYA, BARAKOA_GREEN_COLOR, 0xff8271, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barakoa_sunblocker_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.BARAKO, BARAKOA_GREEN_COLOR, 0xFFFF2F, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("barako_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.FROSTMAW, 0xf7faff, 0xafcdff, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("frostmaw_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.GROTTOL, 0x777777, 0xbce0ff, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("grottol_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.LANTERN, 0x6dea00, 0x235a10, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("lantern_spawn_egg"),
                new ModSpawnEggItem(EntityHandler.NAGA, 0x154850, 0x8dd759, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_spawn_egg"),
//            new ModSpawnEggItem(EntityHandler.SCULPTOR, 0x154850, 0x8dd759, new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("sculptor_spawn_egg"),

                new BlockItem(BlockHandler.PAINTED_ACACIA.get(), new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.PAINTED_ACACIA.get().getRegistryName()),
                new BlockItem(BlockHandler.PAINTED_ACACIA_SLAB.get(), new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.PAINTED_ACACIA_SLAB.get().getRegistryName()),
                new BlockItem(BlockHandler.THATCH.get(), new Item.Properties().group(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.THATCH.get().getRegistryName())
        );
    }

    public static void initializeAttributes() {
        WROUGHT_AXE.getAttributesFromConfig();
        WROUGHT_HELMET.getAttributesFromConfig();
        BARAKOA_MASK_FURY.getAttributesFromConfig();
        BARAKOA_MASK_FEAR.getAttributesFromConfig();
        BARAKOA_MASK_RAGE.getAttributesFromConfig();
        BARAKOA_MASK_BLISS.getAttributesFromConfig();
        BARAKOA_MASK_MISERY.getAttributesFromConfig();
        BARAKOA_MASK_FAITH.getAttributesFromConfig();
        BARAKO_MASK.getAttributesFromConfig();
        SPEAR.getAttributesFromConfig();
        NAGA_FANG_DAGGER.getAttributesFromConfig();

        int barakoaColor = ConfigHandler.CLIENT.oldBarakoaTextures.get() ? BARAKOA_PINK_COLOR : BARAKOA_GREEN_COLOR;
        BARAKO_SPAWN_EGG.primaryColor = barakoaColor;
        BARAKOA_SPAWN_EGG.primaryColor = barakoaColor;
        BARAKOANA_SPAWN_EGG.primaryColor = barakoaColor;
        BARAKOA_SUNBLOCKER_SPAWN_EGG.primaryColor = barakoaColor;
    }

    public static void initializeDispenserBehaviors() {
        DispenserBlock.registerDispenseBehavior(DART, new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EntityDart dartentity = new EntityDart(EntityHandler.DART.get(), worldIn, position.getX(), position.getY(), position.getZ());
                dartentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
                return dartentity;
            }
        });
    }
}
