package net.reikeb.electrona.world.gen.injection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

import java.util.Map;
import java.util.function.Consumer;

public final class BiomeInjection {

    private BiomeInjection() {
    }

    public static void addStructureToBiomes(Map<StructureFeature<?>, Multimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap, Registry<Biome> biomeRegistry) {
        addConfiguredStructureEntries(structureToMultiMap, biomeRegistry, Ruins::addRuins);
    }

    private static void addConfiguredStructureEntries(Map<StructureFeature<?>, Multimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap, Registry<Biome> biomeRegistry, Consumer<BiomeInjectionHelper> structureAddition) {
        for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : biomeRegistry.entrySet()) {
            structureAddition.accept(new BiomeInjectionHelper(biomeEntry, biomeRegistry, structureToMultiMap));
        }
    }

    public static class BiomeInjectionHelper {
        public final Biome biome;
        public final ResourceKey<Biome> biomeKey;
        public final Registry<Biome> biomeRegistry;
        public final Map<StructureFeature<?>, Multimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap;

        public BiomeInjectionHelper(Map.Entry<ResourceKey<Biome>, Biome> biomeEntry, Registry<Biome> biomeRegistry, Map<StructureFeature<?>, Multimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap) {
            this.biome = biomeEntry.getValue();
            this.biomeKey = biomeEntry.getKey();
            this.biomeRegistry = biomeRegistry;
            this.structureToMultiMap = structureToMultiMap;
        }

        public ResourceKey<Biome> getBiomeKey() {
            return biomeRegistry.getResourceKey(biome).get();
        }

        public void addStructure(ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
            structureToMultiMap.computeIfAbsent(configuredStructureFeature.feature, (f) -> HashMultimap.create());
            structureToMultiMap.get(configuredStructureFeature.feature).put(configuredStructureFeature, this.biomeKey);
        }

        public void removeStructure(ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
            Multimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> structureFeatureResourceKeyMultimap = structureToMultiMap.get(configuredStructureFeature.feature);
            structureFeatureResourceKeyMultimap.remove(configuredStructureFeature, biomeKey);
        }
    }
}
