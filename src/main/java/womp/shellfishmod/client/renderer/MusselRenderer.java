package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.MusselEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class MusselRenderer extends MobEntityRenderer<MusselEntity, ShellfishRenderState<MusselEntity.Variant>, MusselModel> {
    
    public MusselRenderer(Context ctx) {
        super(ctx, new MusselModel(ctx.getPart(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<MusselEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<MusselEntity.Variant> livingEntity, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        int variant = livingEntity.variant.getIndex();

        if(livingEntity.baby && variant != 3) {
            matrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        if(!livingEntity.baby && variant != 3) {
            matrixStack.scale(0.8f, 0.8f, 0.8f);
        }

        if(livingEntity.baby && variant == 3) {
            matrixStack.scale(0.35f, 0.35f, 0.35f);
        }

        if(variant == 3 && !livingEntity.baby) {
            matrixStack.scale(0.55f, 0.55f, 0.55f);
        }

        super.render(livingEntity, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(MusselEntity mussel, ShellfishRenderState<Variant> musselState, float f) {
        super.updateRenderState(mussel, musselState, f);
        musselState.variant = mussel.getVariant();
        musselState.idleAnimationState.copyFrom(mussel.idleAnimationState);
        musselState.moveAnimationState.copyFrom(mussel.moveAnimationState);
    }
}
