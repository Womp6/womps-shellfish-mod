package womp.shellfishmod.client.states;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class ShellfishRenderState<T extends Enum<T>> extends LivingEntityRenderState {
    
    public T variant;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
}
