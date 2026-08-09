package womp.shellfishmod.client.states;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public class ShellfishTrapBlockEntityRenderState extends BlockEntityRenderState {
    
    public float tickDelta;
    public ItemRenderState renderBait;
    public long worldTime;
}
