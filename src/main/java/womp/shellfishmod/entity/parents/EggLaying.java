package womp.shellfishmod.entity.parents;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public interface EggLaying {

    EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);

    ShellfishEntity<?> getEntity();

    default boolean hasEgg() {
        return getEntity().getEntityData().get(HAS_EGG);
    }

    default void setHasEgg(boolean value) {
        getEntity().getEntityData().set(HAS_EGG, value);
    }

    default int getPartnerVariant() {
        return getEntity().partnerVariantStorage;
    }

    default void setPartnerVariant(int v) {
        getEntity().partnerVariantStorage = v;
    }
}
