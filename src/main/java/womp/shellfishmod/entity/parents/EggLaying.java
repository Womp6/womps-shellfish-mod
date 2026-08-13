package womp.shellfishmod.entity.parents;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public interface EggLaying {

    ShellfishEntity<?> getEntity();
    EntityDataAccessor<Boolean> getHasEgg();

    default boolean hasEgg() {
        return getEntity().getEntityData().get(getHasEgg());
    }

    default void setHasEgg(boolean value) {
        getEntity().getEntityData().set(getHasEgg(), value);
    }

    default int getPartnerVariant() {
        return getEntity().partnerVariantStorage;
    }

    default void setPartnerVariant(int v) {
        getEntity().partnerVariantStorage = v;
    }
}
