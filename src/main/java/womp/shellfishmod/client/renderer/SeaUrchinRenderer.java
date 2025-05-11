package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaUrchinEntity;

public class SeaUrchinRenderer extends MobRenderer<SeaUrchinEntity, ShellfishRenderState<SeaUrchinEntity.Variant>,  SeaUrchinModel> {

    public SeaUrchinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeaUrchinModel(renderManager.bakeLayer(ShellfishClient.SEA_URCHIN_MODEL)), 0.125f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<SeaUrchinEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/sea_urchin/sea_urchin_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<SeaUrchinEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<SeaUrchinEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(SeaUrchinEntity seaUrchin, ShellfishRenderState<SeaUrchinEntity.Variant> seaUrchinState, float f) {
        super.extractRenderState(seaUrchin, seaUrchinState, f);
        seaUrchinState.variant = seaUrchin.getVariant();
        seaUrchinState.idleAnimationState.copyFrom(seaUrchin.idleAnimationState);
        seaUrchinState.moveAnimationState.copyFrom(seaUrchin.moveAnimationState);
    }
}
