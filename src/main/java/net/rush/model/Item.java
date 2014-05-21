package net.rush.model;

import java.util.Random;

import net.rush.model.item.ItemDye;
import net.rush.model.item.ItemHoe;
import net.rush.model.item.ItemRecord;
import net.rush.util.enums.EnumToolMaterial;
import net.rush.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Item {

	protected static Random rand = new Random();
	public static Item[] byId = new Item[32000];

	/*public static Item IRON_SPADE = new ItemSpade(0, EnumToolMaterial.IRON).setName("shovelIron");
	public static Item IRON_PICKAXE = new ItemPickaxe(1, EnumToolMaterial.IRON).setName("pickaxeIron");
	public static Item IRON_AXE = new ItemAxe(2, EnumToolMaterial.IRON).setName("hatchetIron");
	public static Item FLINT_AND_STEEL = new ItemFlintAndSteel(3).setName("flintAndSteel");
	public static Item APPLE = new ItemFood(4, 4, 0.3F, false).setName("apple");
	public static ItemBow BOW = (ItemBow) new ItemBow(5).setName("bow");
	*/public static Item ARROW = new Item(6).setName("arrow");
	//public static Item COAL = new ItemCoal(7).setName("coal");
	public static Item DIAMOND = new Item(8).setName("diamond");
	public static Item IRON_INGOT = new Item(9).setName("ingotIron");
	public static Item GOLD_INGOT = new Item(10).setName("ingotGold");
	/*public static Item IRON_SWORD = new ItemSword(11, EnumToolMaterial.IRON).setName("swordIron");
	public static Item WOODEN_SWORD = new ItemSword(12, EnumToolMaterial.WOOD).setName("swordWood");
	public static Item WOODEN_SPADE = new ItemSpade(13, EnumToolMaterial.WOOD).setName("shovelWood");
	public static Item WOODEN_PICKAXE = new ItemPickaxe(14, EnumToolMaterial.WOOD).setName("pickaxeWood");
	public static Item WOODEN_AXE = new ItemAxe(15, EnumToolMaterial.WOOD).setName("hatchetWood");
	public static Item STONE_SWORD = new ItemSword(16, EnumToolMaterial.STONE).setName("swordStone");
	public static Item STONE_SPADE = new ItemSpade(17, EnumToolMaterial.STONE).setName("shovelStone");
	public static Item STONE_PICKAXE = new ItemPickaxe(18, EnumToolMaterial.STONE).setName("pickaxeStone");
	public static Item STONE_AXE = new ItemAxe(19, EnumToolMaterial.STONE).setName("hatchetStone");
	public static Item DIAMOND_SWORD = new ItemSword(20, EnumToolMaterial.DIAMOND).setName("swordDiamond");
	public static Item DIAMOND_SPADE = new ItemSpade(21, EnumToolMaterial.DIAMOND).setName("shovelDiamond");
	public static Item DIAMOND_PICKAXE = new ItemPickaxe(22, EnumToolMaterial.DIAMOND).setName("pickaxeDiamond");
	public static Item DIAMOND_AXE = new ItemAxe(23, EnumToolMaterial.DIAMOND).setName("hatchetDiamond");
	*/public static Item STICK = new Item(24).setFull3D().setName("stick");
	public static Item BOWL = new Item(25).setName("bowl");
	/*public static Item SOUP = new ItemSoup(26, 6).setName("mushroomStew");
	public static Item GOLDEN_SWORD = new ItemSword(27, EnumToolMaterial.GOLD).setName("swordGold");
	public static Item GOLDEN_SPADE = new ItemSpade(28, EnumToolMaterial.GOLD).setName("shovelGold");
	public static Item GOLDEN_PICKAXE = new ItemPickaxe(29, EnumToolMaterial.GOLD).setName("pickaxeGold");
	public static Item GOLDEN_AXE = new ItemAxe(30, EnumToolMaterial.GOLD).setName("hatchetGold");
	public static Item TRIPWIRE = new ItemReed(31, Block.tripWire).setName("string");
	*/public static Item FEATHER = new Item(32).setName("feather");
	/*public static Item GUNPOWDER = new Item(33).setName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect);
	*/public static Item WOODEN_HOE = new ItemHoe(34, EnumToolMaterial.WOOD).setName("hoeWood");
	public static Item STONE_HOE = new ItemHoe(35, EnumToolMaterial.STONE).setName("hoeStone");
	public static Item IRON_HOE = new ItemHoe(36, EnumToolMaterial.IRON).setName("hoeIron");
	public static Item DIAMOND_HOE = new ItemHoe(37, EnumToolMaterial.DIAMOND).setName("hoeDiamond");
	public static Item GOLDEN_HOE = new ItemHoe(38, EnumToolMaterial.GOLD).setName("hoeGold");
	/*public static Item SEEDS = new ItemSeeds(39, Block.crops.id, Block.tilledField.id).setName("seeds");
	*/public static Item WHEAT = new Item(40).setName("wheat");
	/*public static Item BREAD = new ItemFood(41, 5, 0.6F, false).setName("bread");
	public static ItemArmor LEATHER_HELMET = (ItemArmor) new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0).setName("helmetCloth");
	public static ItemArmor LEATHER_CHESTPLATE = (ItemArmor) new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1).setName("chestplateCloth");
	public static ItemArmor LEATHER_LEGGINGS = (ItemArmor) new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2).setName("leggingsCloth");
	public static ItemArmor LEATHER_BOOTS = (ItemArmor) new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3).setName("bootsCloth");
	public static ItemArmor CHAIN_HELMET = (ItemArmor) new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0).setName("helmetChain");
	public static ItemArmor CHAIN_CHESTPLATE = (ItemArmor) new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1).setName("chestplateChain");
	public static ItemArmor CHAIN_LEGGINGS = (ItemArmor) new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2).setName("leggingsChain");
	public static ItemArmor CHAIN_BOOTS = (ItemArmor) new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3).setName("bootsChain");
	public static ItemArmor IRON_HELMET = (ItemArmor) new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0).setName("helmetIron");
	public static ItemArmor IRON_CHESTPLATE = (ItemArmor) new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1).setName("chestplateIron");
	public static ItemArmor IRON_LEGGINGS = (ItemArmor) new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2).setName("leggingsIron");
	public static ItemArmor IRON_BOOTS = (ItemArmor) new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3).setName("bootsIron");
	public static ItemArmor DIAMOND_HELMET = (ItemArmor) new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0).setName("helmetDiamond");
	public static ItemArmor DIAMOND_CHESTPLATE = (ItemArmor) new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1).setName("chestplateDiamond");
	public static ItemArmor DIAMOND_LEGGINGS = (ItemArmor) new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2).setName("leggingsDiamond");
	public static ItemArmor DIAMOND_BOOTS = (ItemArmor) new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3).setName("bootsDiamond");
	public static ItemArmor GOLD_HELMET = (ItemArmor) new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0).setName("helmetGold");
	public static ItemArmor GOLD_CHESTPLATE = (ItemArmor) new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1).setName("chestplateGold");
	public static ItemArmor GOLD_LEGGINGS = (ItemArmor) new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2).setName("leggingsGold");
	public static ItemArmor GOLD_BOOTS = (ItemArmor) new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3).setName("bootsGold");
	*/public static Item FLINT = new Item(62).setName("flint");
	/*public static Item RAW_PORK = new ItemFood(63, 3, 0.3F, true).setName("porkchopRaw");
	public static Item COOKED_PORT = new ItemFood(64, 8, 0.8F, true).setName("porkchopCooked");
	public static Item PAINTING = new ItemHangingEntity(65, EntityPainting.class).setName("painting");
	public static Item GOLDEN_APPLE = new ItemAppleGold(66, 4, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setName("appleGold");
	public static Item SIGN = new ItemSign(67).setName("sign");
	public static Item WOOD_DOOR = new ItemDoor(68, Material.wood).setName("doorWood");
	public static Item EMPTY_BUCKET = new ItemBucket(69, 0).setName("bucket").setMaxStackSize(16);
	public static Item WATER_BUCKET = new ItemBucket(70, Block.waterMoving.id).setName("bucketWater").setContainerItem(EMPTY_BUCKET);
	public static Item LAVA_BUCKET = new ItemBucket(71, Block.lavaMoving.id).setName("bucketLava").setContainerItem(EMPTY_BUCKET);
	public static Item MINECART = new ItemMinecart(72, 0).setName("minecart");
	public static Item SADDLE = new ItemSaddle(73).setName("saddle");
	public static Item IRON_DOOR = new ItemDoor(74, Material.iron).setName("doorIron");
	public static Item REDSTONE = new ItemRedstone(75).setName("redstone").setPotionEffect(PotionHelper.redstoneEffect);
	public static Item SNOWBALL = new ItemSnowball(76).setName("snowball");
	public static Item BOAT = new ItemBoat(77).setName("boat");
	*/public static Item LEATHER = new Item(78).setName("leather");
	//public static Item MILK_BUCKET = new ItemBucketMilk(79).setName("milk").setContainerItem(EMPTY_BUCKET);
	public static Item BRICK = new Item(80).setName("brick");
	public static Item CLAY = new Item(81).setName("clay");
	//public static Item SUGAR_CANE = new ItemReed(82, Block.reed).setName("reeds");
	public static Item PAPER = new Item(83).setName("paper");
	//public static Item BOOK = new ItemBook(84).setName("book");
	public static Item SLIMEBALL = new Item(85).setName("slimeball");
	//public static Item CHEST_MINECART = new ItemMinecart(86, 1).setName("minecartChest");
	//public static Item POWERED_MINECART = new ItemMinecart(87, 2).setName("minecartFurnace");
	//public static Item EGG = new ItemEgg(88).setName("egg");
	public static Item COMPASS = new Item(89).setName("compass");
	//public static ItemFishingRod FISHING_ROD = (ItemFishingRod) new ItemFishingRod(90).setName("fishingRod");
	public static Item CLOCK = new Item(91).setName("clock");
	/*public static Item GLOWSTONE = new Item(92).setName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect);
	public static Item RAW_FISH = new ItemFood(93, 2, 0.3F, false).setName("fishRaw");
	public static Item COOKED_FISH = new ItemFood(94, 5, 0.6F, false).setName("fishCooked");
	*/public static Item INK_SACK = new ItemDye(95).setName("dyePowder");
	/*public static Item BONE = new Item(96).setName("bone").setFull3D();
	public static Item SUGAR = new Item(97).setName("sugar").setPotionEffect(PotionHelper.sugarEffect);
	public static Item CAKE = new ItemReed(98, Block.cake).setMaxStackSize(1).setName("cake");
	public static Item BED = new ItemBed(99).setMaxStackSize(1).setName("bed");
	public static Item REDSTONE_REPEATER = new ItemReed(100, Block.redstoneRepeaterIdle).setName("diode");
	public static Item COOKIE = new ItemFood(101, 2, 0.1F, false).setName("cookie");
	public static ItemMap MAP = (ItemMap) new ItemMap(102).setName("map");
	public static ItemShears SHEARS = (ItemShears) new ItemShears(103).setName("shears");
	public static Item MELON = new ItemFood(104, 2, 0.3F, false).setName("melon");
	public static Item PUPMKIN_SEEDS = new ItemSeeds(105, Block.pumpkinStem.id, Block.tilledField.id).setName("seeds_pumpkin");
	public static Item MELON_SEEDS = new ItemSeeds(106, Block.melonStem.id, Block.tilledField.id).setName("seeds_melon");
	public static Item RAW_BEEF = new ItemFood(107, 3, 0.3F, true).setName("beefRaw");
	public static Item COOKED_BEEF = new ItemFood(108, 8, 0.8F, true).setName("beefCooked");
	public static Item RAW_CHICKEN = new ItemFood(109, 2, 0.3F, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setName("chickenRaw");
	public static Item COOKED_CHICKEN = new ItemFood(110, 6, 0.6F, true).setName("chickenCooked");
	public static Item ROTTEN_FLESH = new ItemFood(111, 4, 0.1F, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setName("rottenFlesh");
	public static Item ENDER_PEARL = new ItemEnderPearl(112).setName("enderPearl");*/
	public static Item BLAZE_ROD = new Item(113).setName("blazeRod");
	//public static Item GHAST_TEAR = new Item(114).setName("ghastTear").setPotionEffect(PotionHelper.ghastTearEffect);
	public static Item GOLD_NUGGET = new Item(115).setName("goldNugget");
	/*public static Item NETHER_STALK = new ItemSeeds(116, Block.netherStalk.id, Block.slowSand.id).setName("netherStalkSeeds").setPotionEffect("+4");
	public static ItemPotion POTION = (ItemPotion) new ItemPotion(117).setName("potion");
	public static Item GLASS_BOTTLE = new ItemGlassBottle(118).setName("glassBottle");
	public static Item SPIDER_EYE = new ItemFood(119, 2, 0.8F, false).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect);
	public static Item FERMENTED_SPIDER_EYE = new Item(120).setName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect);
	public static Item BLAZE_POWDER = new Item(121).setName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect);
	public static Item MAGMA_CREAM = new Item(122).setName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect);
	public static Item BREWIND_STAND = new ItemReed(123, Block.brewingStand).setName("brewingStand");
	public static Item CAULDRON = new ItemReed(124, Block.cauldron).setName("cauldron");
	public static Item EYE_OF_ENDER = new ItemEnderEye(125).setName("eyeOfEnder");
	public static Item SPECKLED_MELON = new Item(126).setName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect);
	public static Item MONSTER_EGG = new ItemMonsterEgg(127).setName("monsterPlacer");
	public static Item EXP_BOTTLE = new ItemExpBottle(128).setName("expBottle");
	public static Item FIREBALL_CHARGE = new ItemFireball(129).setName("fireball");
	public static Item WRITABLE_BOOK = new ItemWritableBook(130).setName("writingBook");
	public static Item WRITTEN_BOOK = new ItemEditableBook(131).setName("writtenBook");
	*/public static Item EMERALD = new Item(132).setName("emerald");
	/*public static Item ITEM_FRAME = new ItemHangingEntity(133, EntityItemFrame.class).setName("frame");
	public static Item FLOWER_POT = new ItemReed(134, Block.flowerPot).setName("flowerPot");
	public static Item CARROT = new ItemSeedFood(135, 4, 0.6F, Block.carrot.id, Block.tilledField.id).setName("carrots");
	public static Item POTATO = new ItemSeedFood(136, 1, 0.3F, Block.potato.id, Block.tilledField.id).setName("potato");
	public static Item BAKED_POTATO = new ItemFood(137, 6, 0.6F, false).setName("potatoBaked");
	public static Item POISONOUS_POTATO = new ItemFood(138, 2, 0.3F, false).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setName("potatoPoisonous");
	public static ItemEmptyMap EMPTY_MAP = (ItemEmptyMap) new ItemEmptyMap(139).setName("emptyMap");
	public static Item GOLDEN_CARROT = new ItemFood(140, 6, 1.2F, false).setName("carrotGolden").setPotionEffect(PotionHelper.goldenCarrotEffect);
	public static Item SKULL = new ItemSkull(141).setName("skull");
	public static Item CARROT_ON_STICK = new ItemCarrotOnAStick(142).setName("carrotOnAStick");
	public static Item NETHER_STAR = new ItemSimpleFoiled(143).setName("netherStar");
	public static Item PUMPKIN_PIE = new ItemFood(144, 8, 0.3F, false).setName("pumpkinPie");
	public static Item FIREWORK = new ItemFirework(145).setName("fireworks");
	public static Item FIREWORK_CHARGE = new ItemFireworkCharge(146).setName("fireworksCharge");
	public static ItemEnchantedBook ENCHANTED_BOOK = (ItemEnchantedBook) new ItemEnchantedBook(147).setMaxStackSize(1).setName("enchantedBook");
	public static Item REDSTONE_COMPARATOR = new ItemReed(148, Block.redstoneComparatorIdle).setName("comparator");*/
	public static Item NETHER_BRICK = new Item(149).setName("netherbrick");
	public static Item QUARTZ = new Item(150).setName("netherquartz");
	//public static Item TNT_MINECART = new ItemMinecart(151, 3).setName("minecartTnt");
	//public static Item HOPPER_MINECART = new ItemMinecart(152, 5).setName("minecartHopper");
	public static Item IRON_BARDING = new Item(161).setName("horsearmormetal").setMaxStackSize(1);
	public static Item GOLD_BARDING = new Item(162).setName("horsearmorgold").setMaxStackSize(1);
	public static Item DIAMOND_BARDING = new Item(163).setName("horsearmordiamond").setMaxStackSize(1);
	//public static Item LEASH = new ItemLeash(164).setName("leash");
	//public static Item NAME_TAG = new ItemNameTag(165).setName("nameTag");
	public static Item RECORD_13 = new ItemRecord(2000, "13").setName("record");
	public static Item RECORD_CAT = new ItemRecord(2001, "cat").setName("record");
	public static Item RECORD_BLOCKS = new ItemRecord(2002, "blocks").setName("record");
	public static Item RECORD_CHIRP = new ItemRecord(2003, "chirp").setName("record");
	public static Item RECORD_FAR = new ItemRecord(2004, "far").setName("record");
	public static Item RECORD_MALL = new ItemRecord(2005, "mall").setName("record");
	public static Item RECORD_MELLOHI = new ItemRecord(2006, "mellohi").setName("record");
	public static Item RECORD_STAL = new ItemRecord(2007, "stal").setName("record");
	public static Item RECORD_STRAD = new ItemRecord(2008, "strad").setName("record");
	public static Item RECORD_WARD = new ItemRecord(2009, "ward").setName("record");
	public static Item RECORD_11 = new ItemRecord(2010, "11").setName("record");
	public static Item RECORD_WAIT = new ItemRecord(2011, "wait").setName("record");

	/** The ID of this item. */
	public final int id;

	/** Maximum size of the stack. */
	protected int maxStackSize = 64;

	/** Maximum damage an item can handle. */
	private int maxDamage;

	/** If true, render the object in full 3D, like weapons and tools. */
	protected boolean bFull3D;

	/**
	 * Some items (like dyes) have multiple subtypes on same item, this is field define this behavior
	 */
	protected boolean hasSubtypes;
	private Item containerItem;

	/**
	 * The string representing this item's effect on a potion when used as an ingredient.
	 */
	private String potionEffect;

	/** The unlocalized name of this item. */
	private String name;

	protected Item(int id) {
		this.id = 256 + id;

		if (byId[256 + id] != null)
			System.out.println("ITEM CONFLICT @ " + id);

		byId[256 + id] = this;
	}

	public Item setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
		return this;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {
		return false;
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack item, Block block) {
		return 1.0F;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, Player
	 */
	public ItemStack onItemRightClick(ItemStack item, World world, Player player) {
		return item;
	}

	public ItemStack onEaten(ItemStack item, World world, Player player) {
		return item;
	}

	/**
	 * Returns the maximum size of the stack for a specific item. *Isn't this more a Set than a Get?*
	 */
	public int getItemStackLimit() {
		return maxStackSize;
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	public int getMetadata(int value) {
		return 0;
	}

	public boolean getHasSubtypes() {
		return hasSubtypes;
	}

	protected Item setHasSubtypes(boolean hasSubtypes) {
		this.hasSubtypes = hasSubtypes;
		return this;
	}

	/**
	 * Returns the maximum damage an item can take.
	 */
	public int getMaxDamage() {
		return maxDamage;
	}

	/**
	 * set max damage of an Item
	 */
	protected Item setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
		return this;
	}

	public boolean isDamageable() {
		return maxDamage > 0 && !hasSubtypes;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack item, LivingEntity damager, LivingEntity entity) {
		return false;
	}

	public boolean onBlockDestroyed(ItemStack item, World world, int x, int y, int z, int data, LivingEntity livingentity) {
		return false;
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block block) {
		return false;
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 */
	public boolean itemInteractionForEntity(ItemStack item, Player player, LivingEntity livingentity) {
		return false;
	}

	/**
	 * Sets bFull3D to True and return the object.
	 */
	public Item setFull3D() {
		bFull3D = true;
		return this;
	}

	/**
	 * Sets the unlocalized name of this item to the string passed as the parameter, prefixed by "item."
	 */
	public Item setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the name of this item.
	 */
	public String getName() {
		return "item." + name;
	}

	public Item setContainerItem(Item containerItem) {
		this.containerItem = containerItem;
		return this;
	}

	/**
	 * If this returns true, after a recipe involving this item is crafted the container item will be added to the
	 * player's inventory instead of remaining in the crafting grid.
	 */
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack item) {
		return true;
	}

	/**
	 * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
	 */
	public boolean getShareTag() {
		return true;
	}

	public Item getContainerItem() {
		return containerItem;
	}

	/**
	 * True if this Item has a container item (a.k.a. crafting result)
	 */
	public boolean hasContainerItem() {
		return containerItem != null;
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	public void onUpdate(ItemStack item, World world, Entity par3Entity, int par4, boolean par5) {
	}

	/**
	 * Called when item is crafted/smelted. Used only by maps so far.
	 */
	public void onCreated(ItemStack item, World world, Player player) {
	}

	/**
	 * false for all Items except sub-classes of ItemMapBase
	 */
	public boolean isMap() {
		return false;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public ActionOnUse getItemUseAction(ItemStack item) {
		return ActionOnUse.NONE;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack item) {
		return 0;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, Player, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack item, World world, Player player, int par4) {
	}

	/**
	 * Sets the string representing this item's effect on a potion when used as an ingredient.
	 */
	protected Item setPotionEffect(String potionEffect) {
		this.potionEffect = potionEffect;
		return this;
	}

	/**
	 * Returns a string representing what this item does to a potion.
	 */
	public String getPotionEffect() {
		return potionEffect;
	}

	/**
	 * Returns true if this item serves as a potion ingredient (its ingredient information is not null).
	 */
	public boolean isPotionIngredient() {
		return potionEffect != null;
	}

	/**
	 * Checks isDamagable and if it cannot be stacked
	 */
	public boolean isItemTool(ItemStack item) {
		return getItemStackLimit() == 1 && isDamageable();
	}

	/*protected MovingObjectPosition getMovingObjectPositionFromPlayer(World world, Player player, boolean par3) {
		float motion = 1.0F;
		float prevPitch = (float) (player.getPreviousRotation().getPitch() + (player.getRotation().getPitch() - player.getPreviousRotation().getPitch()) * motion);
		float prevYaw = (float) (player.getPreviousRotation().getYaw() + (player.getRotation().getYaw() - player.getPreviousRotation().getYaw()) * motion);
		double prevX = player.getPreviousPosition().getX() + (player.getPosition().getX() - player.getPreviousPosition().getX()) * motion;
		double prevY = player.getPreviousPosition().getY() + (player.getPosition().getY() - player.getPreviousPosition().getY()) * motion + 1.62D - player.yOffset;
		double prevZ = player.getPreviousPosition().getZ() + (player.getPosition().getZ() - player.getPreviousPosition().getZ()) * motion;
		
		Vec3 vector = world.getWorldVec3Pool().getVecFromPool(prevX, prevY, prevZ);
		float yawCos = MathHelper.cos(-prevYaw * 0.017453292F - (float) Math.PI);
		float yawSin = MathHelper.sin(-prevYaw * 0.017453292F - (float) Math.PI);
		float pitchCos = -MathHelper.cos(-prevPitch * 0.017453292F);
		float pitchSin = MathHelper.sin(-prevPitch * 0.017453292F);
		float yawSinAndPitchCos = yawSin * pitchCos;
		float yawCosAndPitchCos = yawCos * pitchCos;
		double var21 = 5.0D;
		
		Vec3 finalVector = vector.addVector(yawSinAndPitchCos * var21, pitchSin * var21, yawCosAndPitchCos * var21);
		return world.rayTraceBlocks(vector, finalVector, par3, !par3);
	}*/

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability() {
		return 0;
	}

	/**
	 * Returns true if players can use this item to affect the world (e.g. placing blocks, placing ender eyes in portal)
	 * when not in creative
	 */
	public boolean canItemEditBlocks() {
		return true;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack item, ItemStack item2) {
		return false;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<?, ?> getItemAttributeModifiers() {
		return HashMultimap.create();
	}
	
	public enum ActionOnUse {
		NONE, EAT, DRINK, BLOCK, SHOOT_ARROW;
	}
}
