package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.SeaSnailModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.SeaSnailEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class SeaSnailRenderer extends MobEntityRenderer<SeaSnailEntity, ShellfishRenderState<SeaSnailEntity.Variant>, SeaSnailModel> {

    public SeaSnailRenderer(Context context) {
        super(context, new SeaSnailModel(context.getPart(ShellfishClient.SEA_SNAIL_MODEL)), 0.25f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<SeaSnailEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<SeaSnailEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 0.7f, 0.35f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.8f, 1.0f, 0.5f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.3f, 0.9f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);
        
        SeaSnailModel seaSnailModel = (SeaSnailModel)this.getModel();
        if (variant == 0) {
            seaSnailModel.shell1.visible = true;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 1) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = true;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 2) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = true;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 3) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = true;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 4) {
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
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(SeaSnailEntity snail, ShellfishRenderState<Variant> snailState, float f) {
        super.updateRenderState(snail, snailState, f);
        snailState.variant = snail.getVariant();
        snailState.shellfish = snail;
        snailState.idleAnimationState.copyFrom(snail.idleAnimationState);
        snailState.moveAnimationState.copyFrom(snail.moveAnimationState);
    }
}
