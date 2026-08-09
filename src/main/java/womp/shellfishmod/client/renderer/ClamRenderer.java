package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.ClamEntity.Variant;

public class ClamRenderer extends MobEntityRenderer<ClamEntity, ShellfishRenderState<ClamEntity.Variant>, ClamModel> {
    
    public ClamRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClamModel(renderManager.getPart(ShellfishClient.CLAM_MODEL)), 0.25f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<ClamEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/clam/clam_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<ClamEntity.Variant> entity, MatrixStack poseStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {

        int variant = entity.variant.getIndex();                  
        if (variant == 1) entity.shellfish.scale(poseStack, 1.1f, 0.8f, 0.5f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 0.85f, 0.45f);
        else if (variant == 3 || variant == 4) entity.shellfish.scale(poseStack, 0.65f, 0.35f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(ClamEntity clam, ShellfishRenderState<Variant> clamState, float f) {
        super.updateRenderState(clam, clamState, f);
        clamState.variant = clam.getVariant();
        clamState.shellfish = clam;
        clamState.idleAnimationState.copyFrom(clam.idleAnimationState);
        clamState.moveAnimationState.copyFrom(clam.moveAnimationState);
    }
}
