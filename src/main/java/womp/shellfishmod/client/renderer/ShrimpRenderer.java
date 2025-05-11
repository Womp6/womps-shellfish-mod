package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ShrimpEntity;

public class ShrimpRenderer extends MobRenderer<ShrimpEntity, ShellfishRenderState<ShrimpEntity.Variant>,  ShrimpModel> {

    public ShrimpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShrimpModel(renderManager.bakeLayer(ShellfishClient.SHRIMP_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<ShrimpEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<ShrimpEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby) {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        }
        if(!entity.isBaby) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<ShrimpEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(ShrimpEntity shrimp, ShellfishRenderState<ShrimpEntity.Variant> shrimpState, float f) {
        super.extractRenderState(shrimp, shrimpState, f);
        shrimpState.variant = shrimp.getVariant();
        shrimpState.idleAnimationState.copyFrom(shrimp.idleAnimationState);
        shrimpState.moveAnimationState.copyFrom(shrimp.swimAnimationState);
    }
}