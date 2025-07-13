package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MusselEntity;

public class MusselRenderer extends MobRenderer<MusselEntity, ShellfishRenderState<MusselEntity.Variant>,  MusselModel> {

    public MusselRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MusselModel(ctx.bakeLayer(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<MusselEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<MusselEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        int variant = entity.variant.getIndex();
        if (variant == 1) entity.shellfish.scale(poseStack, 0.95f, 0.65f, 0.4f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.1f, 0.8f, 0.4f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.55f, 0.3f);
        else entity.shellfish.scale(poseStack, 0.8f, 0.4f);

        super.render(entity, poseStack, vertexConsumerProvider, i);
    }

    @Override
    public ShellfishRenderState<MusselEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(MusselEntity mussel, ShellfishRenderState<MusselEntity.Variant> musselState, float f) {
        super.extractRenderState(mussel, musselState, f);
        musselState.variant = mussel.getVariant();
        musselState.shellfish = mussel;
        musselState.idleAnimationState.copyFrom(mussel.idleAnimationState);
        musselState.moveAnimationState.copyFrom(mussel.moveAnimationState);
    }
}
