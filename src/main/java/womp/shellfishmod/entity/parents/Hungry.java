package womp.shellfishmod.entity.parents;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public interface Hungry {

    ShellfishEntity<?> getEntity();
    EntityDataAccessor<Boolean> getHungry();

    default int getHungryTime() {
        return 6000;
    }
    default void setHungry(boolean value) {
        getEntity().getEntityData().set(getHungry(), value);
    }
    default boolean isHungry() {
        return getEntity().getEntityData().get(getHungry());
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
}
