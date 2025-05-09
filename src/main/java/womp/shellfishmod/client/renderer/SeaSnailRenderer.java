package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.SeaSnailModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;

public class SeaSnailRenderer extends MobRenderer<SeaSnailEntity, ShellfishRenderState<SeaSnailEntity.Variant>,  SeaSnailModel> {

    public SeaSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaSnailModel(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<SeaSnailEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<SeaSnailEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        int variant = entity.variant.getIndex();
        SeaSnailModel seaSnailModel = (SeaSnailModel)this.getModel();
        if(entity.isBaby && variant != 2) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        if(variant == 2 && !entity.isBaby) {
            poseStack.scale(1.5f, 1.5f, 1.5f);
        }
        if(entity.isBaby && variant == 2) {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        }

        if (variant == 0) {
            seaSnailModel.shell1.visible = true;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        }
        if (variant == 1) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = true;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        }
        if (variant == 2) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = true;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        }
        if (variant == 3) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = true;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        }
        if (variant == 4) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = true;
            seaSnailModel.sea_snail.visible = true;
        }
        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<SeaSnailEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(SeaSnailEntity snail, ShellfishRenderState<SeaSnailEntity.Variant> snailState, float f) {
        super.extractRenderState(snail, snailState, f);
        snailState.variant = snail.getVariant();
        snailState.idleAnimationState.copyFrom(snail.idleAnimationState);
        snailState.moveAnimationState.copyFrom(snail.moveAnimationState);
    }
}
