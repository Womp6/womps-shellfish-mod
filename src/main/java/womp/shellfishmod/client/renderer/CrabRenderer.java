package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrabEntity;

public class CrabRenderer extends MobRenderer<CrabEntity, ShellfishRenderState<CrabEntity.Variant>,  CrabModel> {

    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.bakeLayer(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<CrabEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<CrabEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector queue, CameraRenderState camera) {
        int variant = entity.variant.getIndex();
        if (variant == 0) entity.shellfish.scale(poseStack, 1.1f, 0.85f, 0.4f);
        else if (variant == 1) entity.shellfish.scale(poseStack, 0.6f, 0.35f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 0.85f, 0.6f, 0.4f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 1f, 0.75f, 0.4f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 0.5f, 0.3f);
        else entity.shellfish.scale(poseStack, 0.75f, 0.4f);

        super.submit(entity, poseStack, queue, camera);
    }

    @Override
    public ShellfishRenderState<CrabEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(CrabEntity crab, ShellfishRenderState<CrabEntity.Variant> crabState, float f) {
        super.extractRenderState(crab, crabState, f);
        crabState.variant = crab.getVariant();
        crabState.shellfish = crab;
        crabState.idleAnimationState.copyFrom(crab.idleAnimationState);
        crabState.moveAnimationState.copyFrom(crab.moveAnimationState);
    }
}
