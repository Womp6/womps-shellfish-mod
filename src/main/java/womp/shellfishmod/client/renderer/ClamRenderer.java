package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ClamEntity;

public class ClamRenderer extends MobRenderer<ClamEntity, ShellfishRenderState<ClamEntity.Variant>,  ClamModel> {

    public ClamRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ClamModel(renderManager.bakeLayer(ShellfishClient.CLAM_MODEL)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<ClamEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/clam/clam_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<ClamEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector queue, CameraRenderState camera) {
        int variant = entity.variant.getIndex();
        if (variant == 1) entity.shellfish.scale(poseStack, 1.1f, 0.8f, 0.5f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 0.85f, 0.45f);
        else if (variant == 3 || variant == 4) entity.shellfish.scale(poseStack, 0.65f, 0.35f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        super.submit(entity, poseStack, queue, camera);
    }

    @Override
    public ShellfishRenderState<ClamEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(ClamEntity clam, ShellfishRenderState<ClamEntity.Variant> clamState, float f) {
        super.extractRenderState(clam, clamState, f);
        clamState.variant = clam.getVariant();
        clamState.shellfish = clam;
        clamState.idleAnimationState.copyFrom(clam.idleAnimationState);
        clamState.moveAnimationState.copyFrom(clam.moveAnimationState);
    }
}
