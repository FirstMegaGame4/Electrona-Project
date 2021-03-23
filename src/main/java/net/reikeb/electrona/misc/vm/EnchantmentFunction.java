package net.reikeb.electrona.misc.vm;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.reikeb.electrona.init.EnchantmentInit;

public class EnchantmentFunction {

    /**
     * Main Lumberjack enchantment function
     *
     * @param player     The player who cuts the tree
     * @param world      The world
     * @param pos        The position of the broken block
     * @param directions The directions to get neighbour blocks
     */
    public static void lumberjackMain(ServerPlayerEntity player, World world, BlockPos pos, Direction[] directions) {
        ITagCollection<Block> tagCollection = BlockTags.getAllTags();
        ITag<Block> logTag;
        logTag = tagCollection.getTagOrEmpty(new ResourceLocation("minecraft:logs"));
        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.LUMBERJACK.get(), player.getMainHandItem()) > 0) {
            for (Direction dir : directions) {
                BlockPos otherPos = pos.relative(dir);
                if (logTag.contains(world.getBlockState(otherPos).getBlock())) {
                    Block.dropResources(world.getBlockState(otherPos), world, otherPos);
                    world.destroyBlock(otherPos, false);
                    lumberjackDef(world, otherPos, directions);
                }
            }
        }
    }

    /**
     * Second Lumberjack enchantment function
     *
     * @param world      The world
     * @param pos        The position of the neighbour blocks
     * @param directions The directions to get the neighbour blocks
     */
    public static void lumberjackDef(World world, BlockPos pos, Direction[] directions) {
        ITagCollection<Block> tagCollection = BlockTags.getAllTags();
        ITag<Block> logTag;
        logTag = tagCollection.getTagOrEmpty(new ResourceLocation("minecraft:logs"));

        for (Direction dir : directions) {
            BlockPos otherPos = pos.relative(dir);
            if (logTag.contains(world.getBlockState(otherPos).getBlock())) {
                Block.dropResources(world.getBlockState(otherPos), world, otherPos);
                world.destroyBlock(otherPos, false);
                lumberjackDef(world, otherPos, directions);
            }
        }
    }

    /**
     * Main Veinminer enchantment function
     *
     * @param player     The player who mines the ore
     * @param world      The world
     * @param pos        The position of the broken block
     * @param directions The directions to get neighbour blocks
     */
    public static void veinminerMain(ServerPlayerEntity player, World world, BlockPos pos, Direction[] directions) {
        ITagCollection<Block> tagCollection = BlockTags.getAllTags();
        ITag<Block> mcOreTag, fgOreTag, elOreTag;
        mcOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("minecraft:ores"));
        fgOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge:ores"));
        elOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge:electrona/ores"));

        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.VEINMINER.get(), player.getMainHandItem()) > 0) {
            for (Direction dir : directions) {
                BlockPos otherPos = pos.relative(dir);
                if ((mcOreTag.contains(world.getBlockState(otherPos).getBlock()))
                        || (fgOreTag.contains(world.getBlockState(otherPos).getBlock()))
                        || (elOreTag.contains(world.getBlockState(otherPos).getBlock()))) {
                    Block.dropResources(world.getBlockState(otherPos), world, otherPos);
                    world.destroyBlock(otherPos, false);
                    veinminerDef(world, otherPos, directions);
                }
            }
        }
    }

    /**
     * Second Veinminer enchantment function
     *
     * @param world      The world
     * @param pos        The position of the neighbour blocks
     * @param directions The directions to get the neighbour blocks
     */
    public static void veinminerDef(World world, BlockPos pos, Direction[] directions) {
        ITagCollection<Block> tagCollection = BlockTags.getAllTags();
        ITag<Block> mcOreTag, fgOreTag, elOreTag;
        mcOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("minecraft:ores"));
        fgOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge:ores"));
        elOreTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge:electrona/ores"));

        for (Direction dir : directions) {
            BlockPos otherPos = pos.relative(dir);
            if ((mcOreTag.contains(world.getBlockState(otherPos).getBlock()))
                    || (fgOreTag.contains(world.getBlockState(otherPos).getBlock()))
                    || (elOreTag.contains(world.getBlockState(otherPos).getBlock()))) {
                Block.dropResources(world.getBlockState(otherPos), world, otherPos);
                world.destroyBlock(otherPos, false);
                veinminerDef(world, otherPos, directions);
            }
        }
    }

    /**
     * Thundering enchantment function
     *
     * @param world  The world
     * @param pos    The position of the clicked block
     * @param player The player who clicks on the block
     * @param handIn The hand of the player
     */
    public static void thundering(World world, BlockPos pos, PlayerEntity player, Hand handIn) {
        ItemStack stack = handIn == Hand.MAIN_HAND ? player.getMainHandItem() : player.getOffhandItem();
        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.THUNDERING.get(), stack) > 0) {
            if (world instanceof ServerWorld) {
                LightningBoltEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);
                int damageValue = ((9 - (3 * unbreakingLevel)) + (unbreakingLevel == 0 ? 0 : 3));
                if (lightning == null) return;
                lightning.moveTo(Vector3d.atBottomCenterOf(pos));
                lightning.setVisualOnly(false);
                world.addFreshEntity(lightning);
                if (!player.abilities.instabuild) stack.hurtAndBreak(damageValue, player, (entity) ->
                            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
            }
        }
    }
}
