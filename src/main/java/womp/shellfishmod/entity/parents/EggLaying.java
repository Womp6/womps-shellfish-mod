package womp.shellfishmod.entity.parents;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public interface EggLaying {

    TrackedData<Boolean> HAS_EGG = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    ShellfishEntity getEntity();

    default boolean hasEgg() {
        return getEntity().getDataTracker().get(HAS_EGG);
    }

    default void setHasEgg(boolean value) {
        getEntity().getDataTracker().set(HAS_EGG, value);
    }

    default TrackedData<Boolean> getEggTracker() {
        return HAS_EGG;
    }

    default int getPartnerVariant() {
        return getEntity().partnerVariantStorage;
    }

    default void setPartnerVariant(int v) {
        getEntity().partnerVariantStorage = v;
    }
}
