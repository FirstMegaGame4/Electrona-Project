package net.reikeb.electrona.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.reikeb.electrona.tileentities.TileBlueCable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BlueCable extends AbstractCable {

    public BlueCable() {
        super("cable", Material.GLASS, 1f, 6f, SoundType.GLASS, 4);
    }

    @Override
    public boolean canConnectTo(BlockState wireState, World worldIn, BlockPos wirePos, BlockPos connectPos, Direction direction) {
        BlockState otherState = worldIn.getBlockState(connectPos);

        ITagCollection<Block> tagCollection = BlockTags.getAllTags();
        ITag<Block> generatorTag, machineTag, cableTag;
        generatorTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge", "electrona/generators"));
        machineTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge", "electrona/machines_all"));
        cableTag = tagCollection.getTagOrEmpty(new ResourceLocation("forge", "electrona/blue_cable"));

        return (generatorTag.contains(otherState.getBlock())) || (machineTag.contains(otherState.getBlock()))
                || (cableTag.contains(otherState.getBlock()));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        BlockState stateIn = worldIn.getBlockState(pos);
        TileEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof TileBlueCable) {
            TileBlueCable tileBlueCable = (TileBlueCable) tile;
            if ((hasOpenEnd(stateIn)) && (entityIn instanceof LivingEntity) && (tileBlueCable.getTileData().getDouble("ElectronicPower") > 0)) {
                double damage = Math.random() * 10;
                if (damage > 0) entityIn.hurt(new DamageSource("electric_shock").bypassArmor(), (float) damage);
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileBlueCable();
    }
}