package womp.shellfishmod.entity.parents;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public interface Hungry {

    EntityDataAccessor<Boolean> IS_HUNGRY = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);

    ShellfishEntity<?> getEntity();

    default int getHungryTime() {
        return 6000;
    }
    default void setHungry(boolean value) {
        getEntity().getEntityData().set(IS_HUNGRY, value);
    }
    default boolean isHungry() {
        return getEntity().getEntityData().get(IS_HUNGRY);
    }

    default int tick(int counter) {
        counter++;

        if (counter < getHungryTime()) {
            this.setHungry(false);
        }

        if (counter >= getHungryTime() && !this.isHungry()) {
            this.setHungry(true);
        }

        if (this.isHungry() && counter >= (getHungryTime() + 600)) {
            this.setHungry(false);
            return 0;
        }
        return counter;
    }

    default EntityDataAccessor<Boolean> getHungryTracker() {
        return IS_HUNGRY;
    }
}
