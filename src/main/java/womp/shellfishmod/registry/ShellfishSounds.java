package womp.shellfishmod.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;

public class ShellfishSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ShellfishMod.MOD_ID);

    public static final RegistryObject<SoundEvent> CRAYFISH_HURT = registerSound("entity.crayfish.hurt");
    public static final RegistryObject<SoundEvent> CRAYFISH_ATTACK = registerSound("entity.crayfish.attack");
    public static final RegistryObject<SoundEvent> CRAYFISH_DEATH = registerSound("entity.crayfish.death");
    public static final RegistryObject<SoundEvent> LOBSTER_HURT = registerSound("entity.lobster.hurt");
    public static final RegistryObject<SoundEvent> LOBSTER_ATTACK = registerSound("entity.lobster.attack");
    public static final RegistryObject<SoundEvent> LOBSTER_DEATH = registerSound("entity.lobster.death");
    public static final RegistryObject<SoundEvent> CRAB_HURT = registerSound("entity.crab.hurt");
    public static final RegistryObject<SoundEvent> CRAB_ATTACK = registerSound("entity.crab.attack");
    public static final RegistryObject<SoundEvent> CRAB_DEATH = registerSound("entity.crab.death");
    public static final RegistryObject<SoundEvent> CRAYFISHES_HATCH = registerSound("entity.crayfish.hatch");
    public static final RegistryObject<SoundEvent> CRAYFISH_LAYS_EGGS = registerSound("entity.crayfish.lay_eggs");
    public static final RegistryObject<SoundEvent> LOBSTERS_HATCH = registerSound("entity.lobster.hatch");
    public static final RegistryObject<SoundEvent> LOBSTER_LAYS_EGGS = registerSound("entity.lobster.lay_eggs");
    public static final RegistryObject<SoundEvent> CRABS_HATCH = registerSound("entity.crab.hatch");
    public static final RegistryObject<SoundEvent> CRAB_LAYS_EGGS = registerSound("entity.crab.lay_eggs");
    public static final RegistryObject<SoundEvent> SHRIMP_HURT = registerSound("entity.shrimp.hurt");
    public static final RegistryObject<SoundEvent> SHRIMP_DEATH = registerSound("entity.shrimp.death");
    public static final RegistryObject<SoundEvent> SHRIMPS_HATCH = registerSound("entity.shrimp.hatch");
    public static final RegistryObject<SoundEvent> SHRIMP_LAYS_EGGS = registerSound("entity.shrimp.lay_eggs");
    public static final RegistryObject<SoundEvent> SEA_SNAILS_HATCH = registerSound("entity.sea_snail.hatch");
    public static final RegistryObject<SoundEvent> SEA_SNAIL_LAYS_EGGS = registerSound("entity.sea_snail.lay_eggs");
    public static final RegistryObject<SoundEvent> SEA_SNAIL_HURT = registerSound("entity.sea_snail.hurt");
    public static final RegistryObject<SoundEvent> SEA_SNAIL_DEATH = registerSound("entity.sea_snail.death");
    public static final RegistryObject<SoundEvent> SEA_URCHIN_HURT = registerSound("entity.sea_urchin.hurt");
    public static final RegistryObject<SoundEvent> SEA_URCHIN_DEATH = registerSound("entity.sea_urchin.death");
    public static final RegistryObject<SoundEvent> CLAM_HURT = registerSound("entity.clam.hurt");
    public static final RegistryObject<SoundEvent> CLAM_DEATH = registerSound("entity.clam.death");
    public static final RegistryObject<SoundEvent> OYSTER_HURT = registerSound("entity.oyster.hurt");
    public static final RegistryObject<SoundEvent> OYSTER_DEATH = registerSound("entity.oyster.death");
    public static final RegistryObject<SoundEvent> MUSSEL_HURT = registerSound("entity.mussel.hurt");
    public static final RegistryObject<SoundEvent> MUSSEL_DEATH = registerSound("entity.mussel.death");
    public static final RegistryObject<SoundEvent> WORK_TRAPPER = registerSound("entity.villager.work_trapper");
    public static final RegistryObject<SoundEvent> TRAP_REPAIR = registerSound("block.trap.repair");


    private static RegistryObject<SoundEvent> registerSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(ShellfishMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(BusGroup bus) {
        SOUND_EVENTS.register(bus);
    }
}
