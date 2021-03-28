package net.reikeb.electrona.init;

import net.minecraft.block.Block;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.electrona.Electrona;
import net.reikeb.electrona.blocks.*;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Electrona.MODID);

    // Generators
    public static final RegistryObject<SolarPanelT1> SOLAR_PANEL_T_1 = BLOCKS.register("solar_panel_tiers1", SolarPanelT1::new);
    public static final RegistryObject<SolarPanelT2> SOLAR_PANEL_T_2 = BLOCKS.register("solar_panel_tiers2", SolarPanelT2::new);
    public static final RegistryObject<WaterTurbine> WATER_TURBINE = BLOCKS.register("water_turbine", WaterTurbine::new);
    public static final RegistryObject<HeatGenerator> HEAT_GENERATOR = BLOCKS.register("heat_generator", HeatGenerator::new);
    public static final RegistryObject<BiomassGenerator> BIOMASS_GENERATOR = BLOCKS.register("biomass_generator", BiomassGenerator::new);
    public static final RegistryObject<NuclearGeneratorController> NUCLEAR_GENERATOR_CONTROLLER = BLOCKS.register("nuclear_generator_controller", NuclearGeneratorController::new);
    public static final RegistryObject<CreativeGenerator> CREATIVE_GENERATOR = BLOCKS.register("creative_generator", CreativeGenerator::new);

    // Machines
    public static final RegistryObject<Battery> BATTERY = BLOCKS.register("battery", Battery::new);
    public static final RegistryObject<ELConverter> EL_CONVERTER = BLOCKS.register("el_converter", ELConverter::new);
    public static final RegistryObject<Compressor> COMPRESSOR = BLOCKS.register("compressor", Compressor::new);
    public static final RegistryObject<XPGenerator> XP_GENERATOR = BLOCKS.register("xp_generator", XPGenerator::new);
    public static final RegistryObject<Teleporter> TELEPORTER = BLOCKS.register("teleporter", Teleporter::new);
    public static final RegistryObject<WaterPump> WATER_PUMP = BLOCKS.register("water_pump", WaterPump::new);
    public static final RegistryObject<Purificator> PURIFICATOR = BLOCKS.register("purificator", Purificator::new);

    // Other blocks
    public static final RegistryObject<TinOre> TIN_ORE = BLOCKS.register("tin_ore", TinOre::new);
    public static final RegistryObject<LeadOre> LEAD_ORE = BLOCKS.register("lead_ore", LeadOre::new);
    public static final RegistryObject<UraniumOre> URANIUM_ORE = BLOCKS.register("uranium_ore", UraniumOre::new);
    public static final RegistryObject<TinBlock> TIN_BLOCK = BLOCKS.register("tin_block", TinBlock::new);
    public static final RegistryObject<SteelBlock> STEEL_BLOCK = BLOCKS.register("steel_block", SteelBlock::new);
    public static final RegistryObject<SteelCrate> STEEL_CRATE = BLOCKS.register("steel_crate", SteelCrate::new);
    public static final RegistryObject<LeadBlock> LEAD_BLOCK = BLOCKS.register("lead_block", LeadBlock::new);
    public static final RegistryObject<LeadDoor> LEAD_DOOR = BLOCKS.register("lead_door", LeadDoor::new);
    public static final RegistryObject<MachineCasing> MACHINE_CASING = BLOCKS.register("machine_casing", MachineCasing::new);
    public static final RegistryObject<Cable> CABLE = BLOCKS.register("cable", Cable::new);
    public static final RegistryObject<BlueCable> BLUE_CABLE = BLOCKS.register("blue_cable", BlueCable::new);
    public static final RegistryObject<Cooler> COOLER = BLOCKS.register("cooler", Cooler::new);
    public static final RegistryObject<CompressedObsidian> COMPRESSED_OBSIDIAN = BLOCKS.register("compressed_obsidian", CompressedObsidian::new);
}
