package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.entity.CrabEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, ShellfishRenderState<CrabEntity.Variant>, CrabModel> {

    public CrabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.getPart(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<CrabEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<CrabEntity.Variant> entity, MatrixStack poseStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 1.1f, 0.85f, 0.4f);
        else if (variant == 1) entity.shellfish.scale(poseStack, 0.6f, 0.35f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 0.85f, 0.6f, 0.4f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 1f, 0.75f, 0.4f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 0.5f, 0.3f);
        else entity.shellfish.scale(poseStack, 0.75f, 0.4f);

        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(CrabEntity crab, ShellfishRenderState<Variant> crabState, float f) {
        super.updateRenderState(crab, crabState, f);
        crabState.variant = crab.getVariant();
        crabState.shellfish = crab;
        crabState.idleAnimationState.copyFrom(crab.idleAnimationState);
        crabState.moveAnimationState.copyFrom(crab.moveAnimationState);
    }
}
