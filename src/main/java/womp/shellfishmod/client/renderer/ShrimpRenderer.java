package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ShrimpEntity;
import womp.shellfishmod.entity.ShrimpEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class ShrimpRenderer extends MobEntityRenderer<ShrimpEntity, ShellfishRenderState<ShrimpEntity.Variant>, ShrimpModel> {

    public ShrimpRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShrimpModel(renderManager.getPart(ShellfishClient.SHRIMP_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<ShrimpEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<ShrimpEntity.Variant> entity, MatrixStack poseStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 0.75f, 0.6f, 0.5f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.0f, 0.75f, 0.5f);
        else entity.shellfish.scale(poseStack, 0.45f, 0.25f);

        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(ShrimpEntity shrimp, ShellfishRenderState<Variant> shrimpState, float f) {
        super.updateRenderState(shrimp, shrimpState, f);
        shrimpState.variant = shrimp.getVariant();
        shrimpState.shellfish = shrimp;
        shrimpState.idleAnimationState.copyFrom(shrimp.idleAnimationState);
        shrimpState.moveAnimationState.copyFrom(shrimp.swimAnimationState);
    }
}
