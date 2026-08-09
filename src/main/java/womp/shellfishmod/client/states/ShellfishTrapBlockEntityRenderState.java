package womp.shellfishmod.client.states;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class ShellfishTrapBlockEntityRenderState extends BlockEntityRenderState {
    
    public float tickDelta;
    public ItemStackRenderState renderBait;
    public long worldTime;
}
