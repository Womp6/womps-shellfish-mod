package womp.shellfishmod.client.states;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class ShellfishRenderState<T extends Enum<T>> extends LivingEntityRenderState {
    
    public T variant;
    public ShellfishEntity<? extends T> shellfish;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
}
