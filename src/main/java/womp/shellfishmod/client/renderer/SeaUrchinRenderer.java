package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.entity.SeaUrchinEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class SeaUrchinRenderer extends MobEntityRenderer<SeaUrchinEntity, ShellfishRenderState<SeaUrchinEntity.Variant>, SeaUrchinModel> {
    
    public SeaUrchinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SeaUrchinModel(renderManager.getPart(ShellfishClient.SEA_URCHIN_MODEL)), 0.125f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<SeaUrchinEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/sea_urchin/sea_urchin_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<SeaUrchinEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.baby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(SeaUrchinEntity seaUrchin, ShellfishRenderState<Variant> seaUrchinState, float f) {
        super.updateRenderState(seaUrchin, seaUrchinState, f);
        seaUrchinState.variant = seaUrchin.getVariant();
        seaUrchinState.idleAnimationState.copyFrom(seaUrchin.idleAnimationState);
        seaUrchinState.moveAnimationState.copyFrom(seaUrchin.moveAnimationState);
    }
}
