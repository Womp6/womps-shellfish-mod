package womp.shellfishmod.client.states;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class ShellfishRenderState<T extends Enum<T>> extends LivingEntityRenderState {
    
    public T variant;
    public ShellfishEntity<? extends T> shellfish;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
}
