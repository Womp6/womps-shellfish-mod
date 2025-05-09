package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrabEntity;

public class CrabRenderer extends MobRenderer<CrabEntity, ShellfishRenderState<CrabEntity.Variant>,  CrabModel> {

    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.bakeLayer(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<CrabEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<CrabEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        if(!entity.isBaby) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<CrabEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(CrabEntity crab, ShellfishRenderState<CrabEntity.Variant> crabState, float f) {
        super.extractRenderState(crab, crabState, f);
        crabState.variant = crab.getVariant();
        crabState.idleAnimationState.copyFrom(crab.idleAnimationState);
        crabState.moveAnimationState.copyFrom(crab.moveAnimationState);
    }
}
