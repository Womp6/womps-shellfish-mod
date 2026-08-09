package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.MusselEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class MusselRenderer extends MobEntityRenderer<MusselEntity, ShellfishRenderState<MusselEntity.Variant>, MusselModel> {
    
    public MusselRenderer(Context ctx) {
        super(ctx, new MusselModel(ctx.getPart(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<MusselEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<MusselEntity.Variant> entity, MatrixStack poseStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 1) entity.shellfish.scale(poseStack, 0.95f, 0.65f, 0.4f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.1f, 0.8f, 0.4f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.55f, 0.3f);
        else entity.shellfish.scale(poseStack, 0.8f, 0.4f);

        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(MusselEntity mussel, ShellfishRenderState<Variant> musselState, float f) {
        super.updateRenderState(mussel, musselState, f);
        musselState.variant = mussel.getVariant();
        musselState.shellfish = mussel;
        musselState.idleAnimationState.copyFrom(mussel.idleAnimationState);
        musselState.moveAnimationState.copyFrom(mussel.moveAnimationState);
    }
}
