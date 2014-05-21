package net.rush.model;

import java.util.List;
import java.util.Random;

import net.rush.model.block.BlockCrops;
import net.rush.model.block.BlockFlower;
import net.rush.model.block.BlockGrass;
import net.rush.model.block.BlockLog;
import net.rush.model.block.BlockSapling;
import net.rush.model.block.BlockSoil;
import net.rush.model.block.BlockStone;
import net.rush.model.misc.AxisAlignedBB;
import net.rush.model.misc.MovingObjectPosition;
import net.rush.model.misc.Vec3;
import net.rush.world.World;

public class Block {

	protected String textureName;
	
	public static class Sound {
		public static final StepSound STONE = new StepSound("stone", 1.0F, 1.0F);
		public static final StepSound WOOD = new StepSound("wood", 1.0F, 1.0F);
		public static final StepSound GRAVEL = new StepSound("gravel", 1.0F, 1.0F);
		public static final StepSound GRASS = new StepSound("grass", 1.0F, 1.0F);
		public static final StepSound METAL = new StepSound("stone", 1.0F, 1.5F);
		public static final StepSound GLASS = new StepSound.StepSoundStone("stone", 1.0F, 1.0F);
		public static final StepSound WOOL = new StepSound("cloth", 1.0F, 1.0F);
		public static final StepSound SAND = new StepSound("sand", 1.0F, 1.0F);
		public static final StepSound SNOW = new StepSound("snow", 1.0F, 1.0F);
		public static final StepSound LADDER = new StepSound.StepSoundLadder("ladder", 1.0F, 1.0F);
		public static final StepSound ANVIL = new StepSound.StepSoundAnvil("anvil", 0.3F, 1.0F);
	}

	public static final Block[] byId = new Block[4096];

	/**
	 * An array of 4096 booleans corresponding to the result of the isOpaqueCube() method for each block ID
	 */
	public static final boolean[] opaqueCubeLookup = new boolean[4096];

	/** How much light is subtracted for going through this block */
	public static final int[] lightOpacity = new int[4096];

	/** Array of booleans that tells if a block can grass */
	public static final boolean[] canBlockGrass = new boolean[4096];

	/** Amount of light emitted */
	public static final int[] lightValue = new int[4096];

	/**
	 * Flag if block ID should use the brightest neighbor light value as its own
	 */
	public static boolean[] useNeighborBrightness = new boolean[4096];

	public static final int AIR = 0;
	
	public static final Block STONE = new BlockStone(1).setHardness(1.5F).setResistance(10).setStepSound(Sound.STONE).setName("stone").setTextureName("stone");
	public static final BlockGrass GRASS = (BlockGrass) new BlockGrass(2).setHardness(0.6F).setStepSound(Sound.GRASS).setName("grass").setTextureName("grass").setTickRandomly(true);
	public static final Block DIRT = new Block(3, Material.DIRT).setHardness(0.5F).setStepSound(Sound.GRAVEL).setName("dirt").setTextureName("dirt");
	public static final Block COBBLESTONE = (new Block(4, Material.STONE)).setHardness(2.0F).setResistance(10F).setStepSound(Sound.STONE).setName("stonebrick").setTextureName("cobblestone");
	public static final Block WOOD = new BlockWood(5).setHardness(2F).setResistance(5F).setStepSound(Sound.WOOD).setName("wood").setTextureName("planks");
	public static final Block SAPLING = (new BlockSapling(6)).setHardness(0.0F).setStepSound(Sound.GRASS).setName("sapling").setTextureName("sapling");
	public static final Block BEDROCK = new Block(7, Material.STONE).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(Sound.STONE).setName("bedrock").setTextureName("bedrock");
	/*public static final BlockFluid WATER = (BlockFluid) (new BlockFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setName("water").setTextureName("water_flow");
	public static final Block STATIONARY_WATER = (new BlockStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setName("water").setTextureName("water_still");
	public static final BlockFluid LAVA = (BlockFluid) (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setName("lava").setTextureName("lava_flow");
	public static final Block STATIONARY_LAVA = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setName("lava").setTextureName("lava_still");
	public static final Block SAND = (new BlockSand(12)).setHardness(0.5F).setStepSound(soundSandFootstep).setName("sand").setTextureName("sand");
	public static final Block GRAVEL = (new BlockGravel(13)).setHardness(0.6F).setStepSound(soundGravelFootstep).setName("gravel").setTextureName("gravel");
	public static final Block GOLD_ORE = (new BlockOre(14)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreGold").setTextureName("gold_ore");
	public static final Block IRON_ORE = (new BlockOre(15)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreIron").setTextureName("iron_ore");
	public static final Block COAL_ORE = (new BlockOre(16)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreCoal").setTextureName("coal_ore");
	*/
	public static final Block LOG = new BlockLog(17).setHardness(2.0F).setStepSound(Sound.WOOD).setName("log").setTextureName("log");
	// TODO proper leaves
	public static final Block LEAVES = new Block(18, Material.LEAVES).setHardness(0.2F).setLightOpacity(1).setStepSound(Sound.GRASS).setName("leaves").setTextureName("leaves");
	//public static final BlockLeaves LEAVES = (BlockLeaves) (new BlockLeaves(18)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setName("leaves").setTextureName("leaves");
	/*public static final Block SPONGE = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setName("sponge").setTextureName("sponge");
	public static final Block GLASS = (new BlockGlass(20, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setName("glass").setTextureName("glass");
	public static final Block LAPIS_ORE = (new BlockOre(21)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreLapis").setTextureName("lapis_ore");
	public static final Block LAPIS_BLOCK = (new Block(22, Material.rock)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("blockLapis").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("lapis_block");
	public static final Block DISPENSER = (new BlockDispenser(23)).setHardness(3.5F).setStepSound(soundStoneFootstep).setName("dispenser").setTextureName("dispenser");
	public static final Block SANDSTONE = (new BlockSandStone(24)).setStepSound(soundStoneFootstep).setHardness(0.8F).setName("sandStone").setTextureName("sandstone");
	public static final Block NOTE_BLOCK = (new BlockNote(25)).setHardness(0.8F).setName("musicBlock").setTextureName("noteblock");
	public static final Block BED = (new BlockBed(26)).setHardness(0.2F).setName("bed").setTextureName("bed");
	public static final Block POWERED_RAILS = (new BlockRailPowered(27)).setHardness(0.7F).setStepSound(soundMetalFootstep).setName("goldenRail").setTextureName("rail_golden");
	public static final Block DETECTOR_RAILS = (new BlockDetectorRail(28)).setHardness(0.7F).setStepSound(soundMetalFootstep).setName("detectorRail").setTextureName("rail_detector");
	public static final BlockPistonBase PISTON_STICKY_BASE = (BlockPistonBase) (new BlockPistonBase(29, true)).setName("pistonStickyBase");
	public static final Block WEB = (new BlockWeb(30)).setLightOpacity(1).setHardness(4.0F).setName("web").setTextureName("web");
	*/public static final Block TALL_GRASS = new Block/*TallGrass*/(31, Material.PLANT).setHardness(0.0F).setStepSound(Sound.GRASS).setName("tallgrass"); // TODO was BlockTallGrass
	public static final Block DEAD_BUSH = (new Block/*DeadBush*/(32, Material.PLANT)).setHardness(0.0F).setStepSound(Sound.GRASS).setName("deadbush").setTextureName("deadbush"); // TODO was BlockDeadBush
	/*public static final BlockPistonBase PISTON_BASE = (BlockPistonBase) (new BlockPistonBase(33, false)).setName("pistonBase");
	public static final BlockPistonExtension PISTON_EXTENSION = new BlockPistonExtension(34);
	public static final Block WOOL = (new BlockColored(35, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep).setName("cloth").setTextureName("wool_colored");
	public static final BlockPistonMoving MOVING_PISTON = new BlockPistonMoving(36);
	*/public static final Block YELLOW_FLOWER = new BlockFlower(37).setHardness(0.0F).setStepSound(Sound.GRASS).setName("flower").setTextureName("flower_dandelion");
	public static final Block RED_ROSE = new BlockFlower(38).setHardness(0.0F).setStepSound(Sound.GRASS).setName("rose").setTextureName("flower_rose");
	/*public static final BlockFlower BROWN_MUSHROOM = (BlockFlower) (new BlockMushroom(39)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F).setName("mushroom").setTextureName("mushroom_brown");
	public static final BlockFlower RED_MUSHROOM = (BlockFlower) (new BlockMushroom(40)).setHardness(0.0F).setStepSound(soundGrassFootstep).setName("mushroom").setTextureName("mushroom_red");
	public static final Block GOLD_BLOCK = (new BlockOreStorage(41)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("blockGold").setTextureName("gold_block");
	public static final Block IRON_BLOCK = (new BlockOreStorage(42)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("blockIron").setTextureName("iron_block");
	public static final BlockHalfSlab DOUBLE_STEP = (BlockHalfSlab) (new BlockStep(43, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("stoneSlab");
	public static final BlockHalfSlab STEP = (BlockHalfSlab) (new BlockStep(44, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("stoneSlab");
	public static final Block BRICK = (new Block(45, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("brick").setCreativeTab(CreativeTabs.tabBlock).setTextureName("brick");
	public static final Block TNT = (new BlockTNT(46)).setHardness(0.0F).setStepSound(soundGrassFootstep).setName("tnt").setTextureName("tnt");
	public static final Block BOOKSHELF = (new BlockBookshelf(47)).setHardness(1.5F).setStepSound(soundWoodFootstep).setName("bookshelf").setTextureName("bookshelf");
	public static final Block MOSSY_STONE = (new Block(48, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock).setTextureName("cobblestone_mossy");
	public static final Block OBSIDIAN = (new BlockObsidian(49)).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundStoneFootstep).setName("obsidian").setTextureName("obsidian");
	public static final Block TORCH = (new BlockTorch(50)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep).setName("torch").setTextureName("torch_on");
	public static final BlockFire FIRE = (BlockFire) (new BlockFire(51)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setName("fire").setTextureName("fire");
	public static final Block MONSTER_SPAWNER = (new BlockMobSpawner(52)).setHardness(5.0F).setStepSound(soundMetalFootstep).setName("mobSpawner").setTextureName("mob_spawner");
	public static final Block WOODEN_STAIRS = (new BlockStairs(53, planks, 0)).setName("stairsWood");
	public static final BlockChest CHEST = (BlockChest) (new BlockChest(54, 0)).setHardness(2.5F).setStepSound(soundWoodFootstep).setName("chest");
	public static final BlockRedstoneWire REDSTONE_WIRE = (BlockRedstoneWire) (new BlockRedstoneWire(55)).setHardness(0.0F).setStepSound(soundPowderFootstep).setName("redstoneDust").setTextureName("redstone_dust");
	public static final Block DIAMOND_ORE = (new BlockOre(56)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreDiamond").setTextureName("diamond_ore");
	public static final Block DIAMOND_BLOCK = (new BlockOreStorage(57)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("blockDiamond").setTextureName("diamond_block");
	public static final Block WORKBENCH = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep).setName("workbench").setTextureName("crafting_table");
	*/public static final Block CROPS = new BlockCrops(59).setName("crops").setTextureName("wheat");
	public static final Block SOIL = new BlockSoil(60).setHardness(0.6F).setStepSound(Sound.GRAVEL).setName("farmland").setTextureName("farmland");
	/*public static final Block FURNACE = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep).setName("furnace").setCreativeTab(CreativeTabs.tabDecorations);
	public static final Block BURNING_FURNACE = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F).setName("furnace");
	public static final Block SIGN_POST = (new BlockSign(63, TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setName("sign").disableStats();
	public static final Block WOOD_DOOR = (new BlockDoor(64, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setName("doorWood").setTextureName("door_wood");
	public static final Block LADDER = (new BlockLadder(65)).setHardness(0.4F).setStepSound(soundLadderFootstep).setName("ladder").setTextureName("ladder");
	public static final Block RAIL = (new BlockRail(66)).setHardness(0.7F).setStepSound(soundMetalFootstep).setName("rail").setTextureName("rail_normal");
	public static final Block COBBLESTONE_STAIRS = (new BlockStairs(67, cobblestone, 0)).setName("stairsStone");
	public static final Block WALL_SIGN = (new BlockSign(68, TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setName("sign").disableStats();
	public static final Block LEVER = (new BlockLever(69)).setHardness(0.5F).setStepSound(soundWoodFootstep).setName("lever").setTextureName("lever");
	public static final Block STONE_PLATE = (new BlockPressurePlate(70, "stone", Material.rock, EnumMobType.mobs)).setHardness(0.5F).setStepSound(soundStoneFootstep).setName("pressurePlate");
	public static final Block IRON_DOOR = (new BlockDoor(71, Material.iron)).setHardness(5.0F).setStepSound(soundMetalFootstep).setName("doorIron").setTextureName("door_iron");
	public static final Block WOOD_PLATE = (new BlockPressurePlate(72, "planks_oak", Material.wood, EnumMobType.everything)).setHardness(0.5F).setStepSound(soundWoodFootstep).setName("pressurePlate");
	public static final Block REDSTONE_ORE = (new BlockRedstoneOre(73, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("redstone_ore");
	public static final Block GLOWING_REDSTONE_ORE = (new BlockRedstoneOre(74, true)).setLightValue(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreRedstone")
			.setTextureName("redstone_ore");
	public static final Block REDSTONE_TORCH_OFF = (new BlockRedstoneTorch(75, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setName("notGate").setTextureName("redstone_torch_off");
	public static final Block REDSTONE_TORCH_ON = (new BlockRedstoneTorch(76, true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setName("notGate").setCreativeTab(CreativeTabs.tabRedstone)
			.setTextureName("redstone_torch_on");
	public static final Block STONE_BUTTON = (new BlockButtonStone(77)).setHardness(0.5F).setStepSound(soundStoneFootstep).setName("button");
	*/public static final Block SNOW = new Block(78, Material.SNOW_LAYER)/*new BlockSnow(78)*/.setHardness(0.1F).setStepSound(Sound.SNOW).setName("snow").setLightOpacity(0).setTextureName("snow");
	/*public static final Block ICE = (new BlockIce(79)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setName("ice").setTextureName("ice");
	public static final Block SNOW_BLOCK = (new BlockSnowBlock(80)).setHardness(0.2F).setStepSound(soundSnowFootstep).setName("snow").setTextureName("snow");
	public static final Block CACTUS = (new BlockCactus(81)).setHardness(0.4F).setStepSound(soundClothFootstep).setName("cactus").setTextureName("cactus");
	public static final Block CLAY_BLOCK = (new BlockClay(82)).setHardness(0.6F).setStepSound(soundGravelFootstep).setName("clay").setTextureName("clay");
	public static final Block SUGAR_CANE_BLOCK = (new BlockReed(83)).setHardness(0.0F).setStepSound(soundGrassFootstep).setName("reeds").setTextureName("reeds");
	public static final Block JUKEBOX = (new BlockJukeBox(84)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("jukebox").setTextureName("jukebox");
	public static final Block FENCE = (new BlockFence(85, "planks_oak", Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setName("fence");
	public static final Block PUMPKIN = (new BlockPumpkin(86, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setName("pumpkin").setTextureName("pumpkin");
	public static final Block NETHERRACK = (new BlockNetherrack(87)).setHardness(0.4F).setStepSound(soundStoneFootstep).setName("hellrock").setTextureName("netherrack");
	public static final Block SOUL_SAND = (new BlockSoulSand(88)).setHardness(0.5F).setStepSound(soundSandFootstep).setName("hellsand").setTextureName("soul_sand");
	public static final Block GLOWSTONE = (new BlockGlowStone(89, Material.glass)).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setName("lightgem").setTextureName("glowstone");
	public static final BlockPortal PORTAL = (BlockPortal) (new BlockPortal(90)).setHardness(-1.0F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setName("portal").setTextureName("portal");
	public static final Block JACK_O_LANTERN = (new BlockPumpkin(91, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setName("litpumpkin").setTextureName("pumpkin");
	public static final Block CAKEB_BLOCK = (new BlockCake(92)).setHardness(0.5F).setStepSound(soundClothFootstep).setName("cake").setTextureName("cake");
	public static final BlockRedstoneRepeater DIODE_BLOCK_OFF = (BlockRedstoneRepeater) (new BlockRedstoneRepeater(93, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setName("diode").disableStats()
			.setTextureName("repeater_off");
	public static final BlockRedstoneRepeater DIODE_BLOCK_ON = (BlockRedstoneRepeater) (new BlockRedstoneRepeater(94, true)).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setName("diode").disableStats()
			.setTextureName("repeater_on");
	public static final Block LOCKED_CHEST = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setName("lockedchest").setTickRandomly(true);
	public static final Block TRAP_DOOR = (new BlockTrapDoor(96, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setName("trapdoor").setTextureName("trapdoor");
	public static final Block MONSTER_EGG_BLOCK = (new BlockSilverfish(97)).setHardness(0.75F).setName("monsterStoneEgg");
	public static final Block SMOOTH_BRICK = (new BlockStoneBrick(98)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("stonebricksmooth").setTextureName("stonebrick");
	public static final Block BROWN_MUSHROOM_BLOCK = (new BlockMushroomCap(99, Material.wood, 0)).setHardness(0.2F).setStepSound(soundWoodFootstep).setName("mushroom").setTextureName("mushroom_block");
	public static final Block RED_MUSHROOM_BLOCK = (new BlockMushroomCap(100, Material.wood, 1)).setHardness(0.2F).setStepSound(soundWoodFootstep).setName("mushroom").setTextureName("mushroom_block");
	public static final Block IRON_BARS = (new BlockPane(101, "iron_bars", "iron_bars", Material.iron, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("fenceIron");
	public static final Block THIN_GLASS = (new BlockPane(102, "glass", "glass_pane_top", Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setName("thinGlass");
	public static final Block MELON = (new BlockMelon(103)).setHardness(1.0F).setStepSound(soundWoodFootstep).setName("melon").setTextureName("melon");
	public static final Block PUMPKIN_STEM = (new BlockStem(104, pumpkin)).setHardness(0.0F).setStepSound(soundWoodFootstep).setName("pumpkinStem").setTextureName("pumpkin_stem");
	public static final Block MELON_STEM = (new BlockStem(105, melon)).setHardness(0.0F).setStepSound(soundWoodFootstep).setName("pumpkinStem").setTextureName("melon_stem");
	*/public static final Block VINE = new Block(106, Material.REPLACEABLE_PLANT)/*(new BlockVine(106))*/.setHardness(0.2F).setStepSound(Sound.GRASS).setName("vine").setTextureName("vine");
	/*public static final Block FENCE_GATE = (new BlockFenceGate(107)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setName("fenceGate");
	public static final Block BRICK_STAIRS = (new BlockStairs(108, brick, 0)).setName("stairsBrick");
	public static final Block SMOOTH_STONE_STAIRS = (new BlockStairs(109, stoneBrick, 0)).setName("stairsStoneBrickSmooth");
	public static final BlockMycelium MYCEL = (BlockMycelium) (new BlockMycelium(110)).setHardness(0.6F).setStepSound(soundGrassFootstep).setName("mycel").setTextureName("mycelium");
	public static final Block WATER_LILY = (new BlockLilyPad(111)).setHardness(0.0F).setStepSound(soundGrassFootstep).setName("waterlily").setTextureName("waterlily");
	public static final Block NETHER_BRICK = (new Block(112, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("netherBrick").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("nether_brick");
	public static final Block NETHER_FENCE = (new BlockFence(113, "nether_brick", Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("netherFence");
	public static final Block NETHER_STAIRS = (new BlockStairs(114, netherBrick, 0)).setName("stairsNetherBrick");
	public static final Block NETHER_STALK = (new BlockNetherStalk(115)).setName("netherStalk").setTextureName("nether_wart");
	public static final Block ENCHANTMENT_TABLE = (new BlockEnchantmentTable(116)).setHardness(5.0F).setResistance(2000.0F).setName("enchantmentTable").setTextureName("enchanting_table");
	public static final Block BREWING_STAND = (new BlockBrewingStand(117)).setHardness(0.5F).setLightValue(0.125F).setName("brewingStand").setTextureName("brewing_stand");
	public static final BlockCauldron CAULDRON = (BlockCauldron) (new BlockCauldron(118)).setHardness(2.0F).setName("cauldron").setTextureName("cauldron");
	public static final Block ENDER_PORTAL = (new BlockEndPortal(119, Material.portal)).setHardness(-1.0F).setResistance(6000000.0F);
	public static final Block ENDER_PORTAL_FRAME = (new BlockEndPortalFrame(120)).setStepSound(soundGlassFootstep).setLightValue(0.125F).setHardness(-1.0F).setName("endPortalFrame").setResistance(6000000.0F)
			.setCreativeTab(CreativeTabs.tabDecorations).setTextureName("endframe");
	public static final Block END_STONE = (new Block(121, Material.rock)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setName("whiteStone").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("end_stone");
	public static final Block DRAGON_EGG = (new BlockDragonEgg(122)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setLightValue(0.125F).setName("dragonEgg").setTextureName("dragon_egg");
	public static final Block REDSTONE_LAMP_OFF = (new BlockRedstoneLight(123, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone)
			.setTextureName("redstone_lamp_off");
	public static final Block REDSTONE_LAMP_ON = (new BlockRedstoneLight(124, true)).setHardness(0.3F).setStepSound(soundGlassFootstep).setName("redstoneLight").setTextureName("redstone_lamp_on");
	public static final BlockHalfSlab DOUBLE_WOOD_STEP = (BlockHalfSlab) (new BlockWoodSlab(125, true)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setName("woodSlab");
	public static final BlockHalfSlab WOOD_STEP = (BlockHalfSlab) (new BlockWoodSlab(126, false)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setName("woodSlab");
	public static final Block COCOA = (new BlockCocoa(127)).setHardness(0.2F).setResistance(5.0F).setStepSound(soundWoodFootstep).setName("cocoa").setTextureName("cocoa");
	public static final Block SANDSTONE_STAIRS = (new BlockStairs(128, sandStone, 0)).setName("stairsSandStone");
	public static final Block EMERALD_ORE = (new BlockOre(129)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("oreEmerald").setTextureName("emerald_ore");
	public static final Block ENDER_CHEST = (new BlockEnderChest(130)).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundStoneFootstep).setName("enderChest").setLightValue(0.5F);
	public static final BlockTripWireSource TRIP_WIRE_SOURCE = (BlockTripWireSource) (new BlockTripWireSource(131)).setName("tripWireSource").setTextureName("trip_wire_source");
	public static final Block TRIP_WIRE = (new BlockTripWire(132)).setName("tripWire").setTextureName("trip_wire");
	public static final Block EMERALD_BLOCK = (new BlockOreStorage(133)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("blockEmerald").setTextureName("emerald_block");
	public static final Block SPRUCE_STAIRS = (new BlockStairs(134, planks, 1)).setName("stairsWoodSpruce");
	public static final Block BIRCH_STAIRS = (new BlockStairs(135, planks, 2)).setName("stairsWoodBirch");
	public static final Block JUNGLE_STAIRS = (new BlockStairs(136, planks, 3)).setName("stairsWoodJungle");
	public static final Block COMMAND = (new BlockCommandBlock(137)).setBlockUnbreakable().setResistance(6000000.0F).setName("commandBlock").setTextureName("command_block");
	public static final BlockBeacon BEACON = (BlockBeacon) (new BlockBeacon(138)).setName("beacon").setLightValue(1.0F).setTextureName("beacon");
	public static final Block COBBLE_WALL = (new BlockWall(139, cobblestone)).setName("cobbleWall");
	public static final Block FLOWER_PORT = (new BlockFlowerPot(140)).setHardness(0.0F).setStepSound(soundPowderFootstep).setName("flowerPot").setTextureName("flower_pot");
	public static final Block CARROT = (new BlockCarrot(141)).setName("carrots").setTextureName("carrots");
	public static final Block POTATO = (new BlockPotato(142)).setName("potatoes").setTextureName("potatoes");
	public static final Block WOOD_BUTTON = (new BlockButtonWood(143)).setHardness(0.5F).setStepSound(soundWoodFootstep).setName("button");
	public static final Block SKULL = (new BlockSkull(144)).setHardness(1.0F).setStepSound(soundStoneFootstep).setName("skull").setTextureName("skull");
	public static final Block ANVIL = (new BlockAnvil(145)).setHardness(5.0F).setStepSound(soundAnvilFootstep).setResistance(2000.0F).setName("anvil");
	public static final Block TRAPPED_CHEST = (new BlockChest(146, 1)).setHardness(2.5F).setStepSound(soundWoodFootstep).setName("chestTrap");
	public static final Block GOLD_PLATE = (new BlockPressurePlateWeighted(147, "gold_block", Material.iron, 64)).setHardness(0.5F).setStepSound(soundWoodFootstep).setName("weightedPlate_light");
	public static final Block IRON_PLATE = (new BlockPressurePlateWeighted(148, "iron_block", Material.iron, 640)).setHardness(0.5F).setStepSound(soundWoodFootstep).setName("weightedPlate_heavy");
	public static final BlockComparator REDSTONE_COMPARATOR_OFF = (BlockComparator) (new BlockComparator(149, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setName("comparator").disableStats()
			.setTextureName("comparator_off");
	public static final BlockComparator REDSTONE_COMPARATOR_ON = (BlockComparator) (new BlockComparator(150, true)).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setName("comparator").disableStats()
			.setTextureName("comparator_on");
	public static final BlockDaylightDetector DAYLIGHT_DETECTOR = (BlockDaylightDetector) (new BlockDaylightDetector(151)).setHardness(0.2F).setStepSound(soundWoodFootstep).setName("daylightDetector")
			.setTextureName("daylight_detector");
	public static final Block REDSTONE_BLOCK = (new BlockPoweredOre(152)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setName("blockRedstone").setTextureName("redstone_block");
	public static final Block QUARTZ_ORE = (new BlockOre(153)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setName("netherquartz").setTextureName("quartz_ore");
	public static final BlockHopper HOPPER = (BlockHopper) (new BlockHopper(154)).setHardness(3.0F).setResistance(8.0F).setStepSound(soundWoodFootstep).setName("hopper").setTextureName("hopper");
	public static final Block QUARTZ_BLOCK = (new BlockQuartz(155)).setStepSound(soundStoneFootstep).setHardness(0.8F).setName("quartzBlock").setTextureName("quartz_block");
	public static final Block QUARTZ_STAIRS = (new BlockStairs(156, blockNetherQuartz, 0)).setName("stairsQuartz");
	public static final Block ACTIVATOR_RAIL = (new BlockRailPowered(157)).setHardness(0.7F).setStepSound(soundMetalFootstep).setName("activatorRail").setTextureName("rail_activator");
	public static final Block DROPPED = (new BlockDropper(158)).setHardness(3.5F).setStepSound(soundStoneFootstep).setName("dropper").setTextureName("dropper");
	public static final Block STAINED_CLAY = (new BlockColored(159, Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundStoneFootstep).setName("clayHardenedStained").setTextureName("hardened_clay_stained");
	public static final Block HAY_BLOCK = (new BlockHay(170)).setHardness(0.5F).setStepSound(soundGrassFootstep).setName("hayBlock").setCreativeTab(CreativeTabs.tabBlock).setTextureName("hay_block");
	public static final Block CARPET = (new BlockCarpet(171)).setHardness(0.1F).setStepSound(soundClothFootstep).setName("woolCarpet").setLightOpacity(0);
	public static final Block HARD_CLAY = (new Block(172, Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundStoneFootstep).setName("clayHardened").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("hardened_clay");
	public static final Block COAL_BLOCK = (new Block(173, Material.rock)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setName("blockCoal").setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("coal_block");*/

	/** ID of the block. */
	public final int id;

	/** Indicates how many hits it takes to break a block. */
	protected float blockHardness;

	/** Indicates the blocks resistance to explosions. */
	protected float blockResistance;

	/**
	 * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
	 * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
	 */
	protected boolean needsRandomTick = false;

	/** true if the Block contains a Tile Entity */
	protected boolean isBlockContainer;

	/** minimum X for the block bounds (local coordinates) */
	protected double minX;

	/** minimum Y for the block bounds (local coordinates) */
	protected double minY;

	/** minimum Z for the block bounds (local coordinates) */
	protected double minZ;

	/** maximum X for the block bounds (local coordinates) */
	protected double maxX;

	/** maximum Y for the block bounds (local coordinates) */
	protected double maxY;

	/** maximum Z for the block bounds (local coordinates) */
	protected double maxZ;

	/** Sound of stepping on the block */
	public StepSound sound;
	
	public float blockParticleGravity;

	/** Block material definition. */
	public final Material material;

	/**
	 * Determines how much velocity is maintained while moving on top of this block
	 */
	public float slipperiness;

	/** The unlocalized name of this block. */
	private String name;

	protected Block(int id, Material material) {
		sound = Sound.STONE;
		blockParticleGravity = 1.0F;
		slipperiness = 0.6F;

		if (byId[id] != null) {
			throw new IllegalArgumentException("Slot " + id + " is already occupied by " + byId[id] + " when adding " + this);
		} else {
			this.material = material;
			byId[id] = this;
			this.id = id;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[id] = isOpaqueCube();
			lightOpacity[id] = isOpaqueCube() ? 255 : 0;
			canBlockGrass[id] = !material.getCanBlockGrass();
		}
	}

	/**
	 * This method is called on a block after all other blocks gets already created. You can use it to reference and
	 * configure something on the block that needs the others ones.
	 */
	protected void initializeBlock() {
	}

	/**
	 * Sets the footstep sound for the block. Returns the object for convenience in constructing.
	 */
	protected Block setStepSound(StepSound stepSound) {
		this.sound = stepSound;
		return this;
	}

	/**
	 * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
	 */
	protected Block setLightOpacity(int light_opacity) {
		lightOpacity[id] = light_opacity;
		return this;
	}

	/**
	 * Sets the amount of light emitted by a block from 0.0f to 1.0f (converts internally to 0-15). Returns the object
	 * for convenience in constructing.
	 */
	protected Block setLightValue(float light_Value) {
		lightValue[id] = (int) (15.0F * light_Value);
		return this;
	}

	/**
	 * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
	 */
	protected Block setResistance(float blockResistance) {
		this.blockResistance = blockResistance * 3.0F;
		return this;
	}

	public static boolean isNormalCube(int blockId) {
		Block id = byId[blockId];
		return id == null ? false : id.material.isOpaque() && id.renderAsNormalBlock() && !id.canProvidePower();
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	public int getTypeId() {
		return id;
	}

	public boolean getBlocksMovement(/*IBlockAccess par1IBlockAccess,*/ int x, int y, int z) {
		return !material.blocksMovement();
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType() {
		return 0;
	}

	/**
	 * Sets how many hits it takes to break a block.
	 */
	protected Block setHardness(float blockHardness) {
		this.blockHardness = blockHardness;

		if (blockResistance < blockHardness * 5.0F) {
			blockResistance = blockHardness * 5.0F;
		}

		return this;
	}

	protected Block setBlockUnbreakable() {
		setHardness(-1.0F);
		return this;
	}

	/**
	 * Returns the block hardness at a location. Args: world, x, y, z
	 */
	public float getBlockHardness(World world, int x, int y, int z) {
		return blockHardness;
	}

	/**
	 * Sets whether this block type will receive random update ticks
	 */
	protected Block setTickRandomly(boolean tickrandomly) {
		needsRandomTick = tickrandomly;
		return this;
	}

	/**
	 * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
	 * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
	 */
	public boolean getTickRandomly() {
		return needsRandomTick;
	}

	public boolean hasTileEntity() {
		return isBlockContainer;
	}

	/**
	 * Sets the bounds of the block.  minX, minY, minZ, maxX, maxY, maxZ
	 */
	protected final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = (double) minX;
		this.minY = (double) minY;
		this.minZ = (double) minZ;
		this.maxX = (double) maxX;
		this.maxY = (double) maxY;
		this.maxZ = (double) maxZ;
	}

	/**
	 * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
	 * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
	 */
	// TODO
	/*public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return blockAccess.getBlockMaterial(x, y, z).isSolid();
	}*/

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	public void addCollisionBoxesToList(World world, int y, int x, int z, AxisAlignedBB axis, List<AxisAlignedBB> list, Entity collidingEntity) {
		AxisAlignedBB mask = getCollisionBoundingBoxFromPool(world, y, x, z);

		if (mask != null && axis.intersectsWith(mask)) {
			list.add(mask);
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getAABBPool().getAABB((double) x + minX, (double) y + minY, (double) z + minZ, (double) x + maxX, (double) y + maxY, (double) z + maxZ);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return true;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in
	 */
	public boolean canCollideCheck() {
		return isCollidable();
	}

	/**
	 * Returns if this block is collidable (only used by Fire). Args: x, y, z
	 */
	public boolean isCollidable() {
		return true;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void tick(World world, int x, int y, int z, Random rand) {}

	/**
	 * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
	 */
	public void onBlockDestroyedByPlayer(World world, Player player, int x, int y, int z, int metaData) {
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate() {
		return 10;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, int x, int y, int z) {
	}

	/**
	 * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
	 * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
	 * metadata
	 */
	public void breakBlock(World world, int x, int y, int z, int oldId, int oldData) {
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped() {
		return 1;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	//public int idDropped(int par1, Random par2Random, int par3) {
	public int idDropped() {
		return id;
	}

	/**
	 * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
	 * EntityPlayer.
	 */
	public float getPlayerRelativeBlockHardness(Player player, World world, int x, int y, int z) {
		float hardness = getBlockHardness(world, x, y, z);
		return hardness;//hardness < 0.0F ? 0.0F : (!player.canHarvestBlock(this) ? player.getCurrentPlayerStrVsBlock(this, false) / hardness / 100.0F : player.getCurrentPlayerStrVsBlock(this, true) / hardness / 30.0F);
				// TODO
	}

	/**
	 * Drops the specified block items
	 */
	public final void dropBlock(World world, int x, int y, int z, int damage, int bonus) {
		dropBlockWithChance(world, x, y, z, damage, 1.0F, bonus);
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	public void dropBlockWithChance(World world, int x, int y, int z, int damage, float chance, int bonus) {
		int quantity = quantityDroppedWithBonus(bonus);

		for (int i = 0; i < quantity; ++i) {
			if (world.rand.nextFloat() <= chance) {
				int id = idDropped();

				if (id > 0) {
					world.dropItem(x, y, z, id, 1, damageDropped(damage));
				}
			}
		}
	}

	/**
	 * called by spawner, ore, redstoneOre blocks
	 */
	protected void dropXpOnBlockBreak(World world, int x, int y, int z, int amount) {
		// TODO
		/*while (amount > 0) {
			int var6 = EntityXPOrb.getXPSplit(amount);
			amount -= var6;
			world.spawnEntityInWorld(new EntityXPOrb(world, (double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, var6));
		}*/
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(int dmg) {
		return 0;
	}

	/**
	 * Returns how much this block can resist explosions from the passed in entity.
	 */
	public float getExplosionResistance(Entity en) {
		return blockResistance / 5.0F;
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVector, Vec3 endVector) {
		// TODO
		//setBlockBoundsBasedOnState(world, x, y, z);
		startVector = startVector.addVector((double) (-x), (double) (-y), (double) (-z));
		endVector = endVector.addVector((double) (-x), (double) (-y), (double) (-z));
		Vec3 minXVector = startVector.getIntermediateWithXValue(endVector, this.minX);
		Vec3 maxXVector = startVector.getIntermediateWithXValue(endVector, this.maxX);
		Vec3 minYVector = startVector.getIntermediateWithYValue(endVector, this.minY);
		Vec3 maxYVector = startVector.getIntermediateWithYValue(endVector, this.maxY);
		Vec3 minZVector = startVector.getIntermediateWithZValue(endVector, this.minZ);
		Vec3 maxZVector = startVector.getIntermediateWithZValue(endVector, this.maxZ);

		if (!this.isVecInsideYZBounds(minXVector)) {
			minXVector = null;
		}

		if (!this.isVecInsideYZBounds(maxXVector)) {
			maxXVector = null;
		}

		if (!this.isVecInsideXZBounds(minYVector)) {
			minYVector = null;
		}

		if (!this.isVecInsideXZBounds(maxYVector)) {
			maxYVector = null;
		}

		if (!this.isVecInsideXYBounds(minZVector)) {
			minZVector = null;
		}

		if (!this.isVecInsideXYBounds(maxZVector)) {
			maxZVector = null;
		}

		Vec3 finalVector = null;

		if (minXVector != null && (finalVector == null || startVector.squareDistanceTo(minXVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = minXVector;
		}

		if (maxXVector != null && (finalVector == null || startVector.squareDistanceTo(maxXVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = maxXVector;
		}

		if (minYVector != null && (finalVector == null || startVector.squareDistanceTo(minYVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = minYVector;
		}

		if (maxYVector != null && (finalVector == null || startVector.squareDistanceTo(maxYVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = maxYVector;
		}

		if (minZVector != null && (finalVector == null || startVector.squareDistanceTo(minZVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = minZVector;
		}

		if (maxZVector != null && (finalVector == null || startVector.squareDistanceTo(maxZVector) < startVector.squareDistanceTo(finalVector))) {
			finalVector = maxZVector;
		}

		if (finalVector == null) {
			return null;
		} else {
			byte var14 = -1;

			if (finalVector == minXVector) {
				var14 = 4;
			}

			if (finalVector == maxXVector) {
				var14 = 5;
			}

			if (finalVector == minYVector) {
				var14 = 0;
			}

			if (finalVector == maxYVector) {
				var14 = 1;
			}

			if (finalVector == minZVector) {
				var14 = 2;
			}

			if (finalVector == maxZVector) {
				var14 = 3;
			}

			return new MovingObjectPosition(x, y, z, var14, finalVector.addVector((double) x, (double) y, (double) z));
		}
	}

	/**
	 * Checks if a vector is within the Y and Z bounds of the block.
	 */
	private boolean isVecInsideYZBounds(Vec3 vector) {
		return vector == null ? false : vector.yCoord >= minY && vector.yCoord <= maxY && vector.zCoord >= minZ && vector.zCoord <= maxZ;
	}

	/**
	 * Checks if a vector is within the X and Z bounds of the block.
	 */
	private boolean isVecInsideXZBounds(Vec3 vector) {
		return vector == null ? false : vector.xCoord >= minX && vector.xCoord <= maxX && vector.zCoord >= minZ && vector.zCoord <= maxZ;
	}

	/**
	 * Checks if a vector is within the X and Y bounds of the block.
	 */
	private boolean isVecInsideXYBounds(Vec3 vector) {
		return vector == null ? false : vector.xCoord >= minX && vector.xCoord <= maxX && vector.yCoord >= minY && vector.yCoord <= maxY;
	}

	/**
	 * Called upon the block being destroyed by an explosion
	 */
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
	}

	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, ItemStack is) {
		return canPlaceBlockOnSide(world, x, y, z);
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
	 */
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z) {
		return canPlaceBlockAt(world, x, y, z);
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int id = world.getTypeId(x, y, z);
		return id == 0 || byId[id].material.isReplaceable();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, Player par5EntityPlayer, int par6, float par7, float par8, float par9) {
		return false;
	}

	/**
	 * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
	 */
	public void onEntityWalking(World world, int x, int y, int z, Entity en) {
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int blockData) {
		return 0;
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	public void onBlockClicked(World world, int x, int y, int z, Player pl) {
	}

	/**
	 * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
	 */
	public void velocityToAddToEntity(World world, int x, int y, int z, Entity en, Vec3 vector) {
	}

	/**
	 * Used on growing trees, mushrooms, crops etc
	 * @returns true if the grow was successfully
	 */
	public boolean grow(World world, int x, int y, int z, boolean bonemeal) {
		return true;
	}
	
	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	// TODO
	/*public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
	}*/

	/**
	 * returns the block bounderies minX value
	 */
	public final double getMinX() {
		return minX;
	}

	/**
	 * returns the block bounderies maxX value
	 */
	public final double getBlockBoundsMaxX() {
		return maxX;
	}

	/**
	 * returns the block bounderies minY value
	 */
	public final double getBlockBoundsMinY() {
		return minY;
	}

	/**
	 * returns the block bounderies maxY value
	 */
	public final double getBlockBoundsMaxY() {
		return maxY;
	}

	/**
	 * returns the block bounderies minZ value
	 */
	public final double getBlockBoundsMinZ() {
		return minZ;
	}

	/**
	 * returns the block bounderies maxZ value
	 */
	public final double getBlockBoundsMaxZ() {
		return maxZ;
	}

	/**
	 * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
	 * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
	 * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	 */
	// TODO
	/*public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return 0;
	}*/

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	public boolean canProvidePower() {
		return false;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity en) {
	}

	/**
	 * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
	 * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	 */
	// TODO
	/*public int isProvidingStrongPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return 0;
	}*/

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	public void harvestBlock(World world, Player pl, int x, int y, int z, int blockData) {
		pl.addExhaustion(0.025F);

		// TODO
		/*if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(pl)) {
			ItemStack var8 = createStackedBlock(blockData);

			if (var8 != null) {
				dropBlockAsItem_do(world, x, y, z, var8);
			}
		} else {
			int var7 = EnchantmentHelper.getFortuneModifier(pl);
			dropBlockAsItem(world, x, y, z, blockData, var7);
		}*/
	}

	/**
	 * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
	 */
	protected boolean canSilkHarvest() {
		return renderAsNormalBlock() && !isBlockContainer;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	protected ItemStack createStackedBlock(int blockType) {
		int data = 0;

		// TODO
		/*if (blockID >= 0 && blockID < Item.itemsList.length && Item.itemsList[blockID].getHasSubtypes()) {
			data = blockType;
		}*/

		return new ItemStack(id, 1, data);
	}

	/**
	 * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
	 */
	public int quantityDroppedWithBonus(int bonus) {
		return quantityDropped();
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	public boolean canBlockStay(World world, int x, int y, int z) {
		return true;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, LivingEntity livingEntity, ItemStack itemStack) {
	}

	/**
	 * Called after a block is placed
	 */
	public void onPostBlockPlaced(World world, int x, int y, int z, int blockId) {
	}

	public Block setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the unlocalized name of this block.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	 * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	 */
	public boolean onBlockEventReceived(World world, int x, int y, int z, int blockId, int eventId) {
		return false;
	}

	/**
	 * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
	 * and stop pistons
	 */
	public int getMobilityFlag() {
		return material.getMaterialMobility();
	}

	/**
	 * Block's chance to react to an entity falling on it.
	 */
	public void onFallenUpon(World world, int x, int y, int z, Entity en, float chance) {
	}

	/**
	 * Get the block's damage value (for use with pick block).
	 */
	public int getDamageValue(World world, int x, int y, int z) {
		return damageDropped(world.getBlockData(x, y, z));
	}

	/**
	 * Sets the CreativeTab to display this block on.
	 */
	/*public Block setCreativeTab(CreativeTabs par1CreativeTabs) {
		displayOnCreativeTab = par1CreativeTabs;
		return this;
	}*/

	/**
	 * Called when the block is attempted to be harvested
	 */
	public void onBlockHarvested(World world, int x, int y, int z, int blockId, Player player) {
	}

	/**
	 * Called on server worlds only when the block is about to be replaced by a different block or the same block with a
	 * different metadata value. Args: world, x, y, z, old metadata
	 */
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldBlockData) {
	}

	/**
	 * currently only used by BlockCauldron to incrament meta-data during rain
	 */
	public void fillWithRain(World world, int x, int y, int z) {
	}

	public boolean func_82506_l() {
		return true;
	}

	/**
	 * Return whether this block can drop from an explosion.
	 */
	public boolean canDropFromExplosion(Explosion explosion) {
		return true;
	}

	/**
	 * Returns true if the given block ID is equivalent to this one. Example: redstoneTorchOn matches itself and
	 * redstoneTorchOff, and vice versa. Most blocks only match themselves.
	 */
	public boolean isAssociatedBlockID(int blockId) {
		return this.id == blockId;
	}

	/**
	 * Static version of isAssociatedBlockID.
	 */
	public static boolean isAssociatedWith(int firstId, int secondId) {
		return firstId == secondId ? true : (firstId != 0 && secondId != 0 && byId[firstId] != null && byId[secondId] != null ? byId[firstId].isAssociatedBlockID(secondId) : false);
	}

	/**
	 * If this returns true, then comparators facing away from this block will use the value from
	 * getComparatorInputOverride instead of the actual redstone signal strength.
	 */
	public boolean hasComparatorInputOverride() {
		return false;
	}

	/**
	 * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	 * strength when this block inputs to a comparator.
	 */
	public int getComparatorInputOverride(World world, int x, int y, int z, int par5) {
		return 0;
	}

	protected Block setTextureName(String textureName) {
		this.textureName = textureName;
		return this;
	}

	static {
		/*Item.itemsList[WOOL.blockID] = (new ItemCloth(cloth.blockID - 256)).setName("cloth");
		Item.itemsList[STAINED_CLAY.blockID] = (new ItemCloth(stainedClay.blockID - 256)).setName("clayHardenedStained");
		Item.itemsList[CARPET.blockID] = (new ItemCloth(carpet.blockID - 256)).setName("woolCarpet");
		Item.itemsList[LOG.blockID] = (new ItemMultiTextureTile(wood.blockID - 256, wood, BlockLog.woodType)).setName("log");
		Item.itemsList[WOOD.blockID] = (new ItemMultiTextureTile(planks.blockID - 256, planks, BlockWood.woodType)).setName("wood");
		Item.itemsList[MONSTER_EGG_BLOCK.blockID] = (new ItemMultiTextureTile(silverfish.blockID - 256, silverfish, BlockSilverfish.silverfishStoneTypes)).setName("monsterStoneEgg");
		Item.itemsList[SMOOTH_BRICK.blockID] = (new ItemMultiTextureTile(stoneBrick.blockID - 256, stoneBrick, BlockStoneBrick.STONE_BRICK_TYPES)).setName("stonebricksmooth");
		Item.itemsList[SANDSTONE.blockID] = (new ItemMultiTextureTile(sandStone.blockID - 256, sandStone, BlockSandStone.SAND_STONE_TYPES)).setName("sandStone");
		Item.itemsList[QUARTZ_BLOCK.blockID] = (new ItemMultiTextureTile(blockNetherQuartz.blockID - 256, blockNetherQuartz, BlockQuartz.quartzBlockTypes)).setName("quartzBlock");
		Item.itemsList[STEP.blockID] = (new ItemSlab(stoneSingleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, false)).setName("stoneSlab");
		Item.itemsList[DOUBLE_STEP.blockID] = (new ItemSlab(stoneDoubleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, true)).setName("stoneSlab");
		Item.itemsList[WOOD_STEP.blockID] = (new ItemSlab(woodSingleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, false)).setName("woodSlab");
		Item.itemsList[DOUBLE_WOOD_STEP.blockID] = (new ItemSlab(woodDoubleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, true)).setName("woodSlab");
		Item.itemsList[SAPLING.blockID] = (new ItemMultiTextureTile(sapling.blockID - 256, sapling, BlockSapling.WOOD_TYPES)).setName("sapling");
		Item.itemsList[LEAVES.blockID] = (new ItemLeaves(leaves.blockID - 256)).setName("leaves");
		Item.itemsList[VINE.blockID] = new ItemColored(vine.blockID - 256, false);
		Item.itemsList[TALL_GRASS.blockID] = (new ItemColored(tallGrass.blockID - 256, true)).setBlockNames(new String[] { "shrub", "grass", "fern" });
		Item.itemsList[SNOW.blockID] = new ItemSnow(snow.blockID - 256, snow);
		Item.itemsList[WATER_LILY.blockID] = new ItemLilyPad(waterlily.blockID - 256);
		Item.itemsList[PISTON_BASE.blockID] = new ItemPiston(pistonBase.blockID - 256);
		Item.itemsList[PISTON_STICKY_BASE.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);
		Item.itemsList[COBBLE_WALL.blockID] = (new ItemMultiTextureTile(cobblestoneWall.blockID - 256, cobblestoneWall, BlockWall.types)).setName("cobbleWall");
		Item.itemsList[ANVIL.blockID] = (new ItemAnvilBlock(anvil)).setName("anvil");
		 */
		for (int i = 0; i < 256; ++i) {
			if (byId[i] != null) {
				/*if (Item.itemsList[var0] == null) {
					Item.itemsList[var0] = new ItemBlock(var0 - 256);
					byId[var0].initializeBlock();
				}*/

				boolean useNeightborBrightness = false;

				if (i > 0 && byId[i].getRenderType() == 10) {
					useNeightborBrightness = true;
				}

				/*if (var0 > 0 && byId[var0] instanceof BlockHalfSlab) {
					var1 = true;
				}*/ // TODO

				if (i == /*tilledField.blockID*/60) { // TODO farmland (soil)
					useNeightborBrightness = true;
				}

				if (canBlockGrass[i]) {
					useNeightborBrightness = true;
				}

				if (lightOpacity[i] == 0) {
					useNeightborBrightness = true;
				}

				useNeighborBrightness[i] = useNeightborBrightness;
			}
		}

		canBlockGrass[0] = true;
	}
}
