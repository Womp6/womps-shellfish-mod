package womp.shellfishmod.entity.parents;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public interface Hungry {

    TrackedData<Boolean> IS_HUNGRY = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    ShellfishEntity<?> getEntity();

    default int getHungryTime() {
        return 6000;
    }
    default void setHungry(boolean value) {
        getEntity().getDataTracker().set(IS_HUNGRY, value);
    }
    default boolean isHungry() {
        return getEntity().getDataTracker().get(IS_HUNGRY);
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
