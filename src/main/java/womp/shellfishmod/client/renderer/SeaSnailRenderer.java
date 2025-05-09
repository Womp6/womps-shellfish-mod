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
        SeaSnailModel seaSnailModel = (SeaSnailModel)this.getModel();
        if(entity.baby && variant != 2) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
         }
        if(variant == 2 && !entity.baby) {
            poseStack.scale(1.5f, 1.5f, 1.5f);
        }
        if(entity.baby && variant == 2) {
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
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(SeaSnailEntity snail, ShellfishRenderState<Variant> snailState, float f) {
        super.updateRenderState(snail, snailState, f);
        snailState.variant = snail.getVariant();
        snailState.idleAnimationState.copyFrom(snail.idleAnimationState);
        snailState.moveAnimationState.copyFrom(snail.moveAnimationState);
    }
}
