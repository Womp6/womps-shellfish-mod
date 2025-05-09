package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.ClamEntity.Variant;

public class ClamRenderer extends MobEntityRenderer<ClamEntity, ShellfishRenderState<ClamEntity.Variant>, ClamModel> {
    
    public ClamRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClamModel(renderManager.getPart(ShellfishClient.CLAM_MODEL)), 0.25f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<ClamEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/clam/clam_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<ClamEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        int variant = entity.variant.getIndex();
        if(entity.baby && variant != 3 && variant != 4) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        if(variant == 3 && !entity.baby) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }
        if(variant == 4 && !entity.baby) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }
        if(variant == 3 && entity.baby) {
            poseStack.scale(0.35f, 0.35f, 0.35f);
        }
        if(variant == 4 && entity.baby) {
            poseStack.scale(0.35f, 0.35f, 0.35f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(ClamEntity clam, ShellfishRenderState<Variant> clamState, float f) {
        super.updateRenderState(clam, clamState, f);
        clamState.variant = clam.getVariant();
        clamState.idleAnimationState.copyFrom(clam.idleAnimationState);
        clamState.moveAnimationState.copyFrom(clam.moveAnimationState);
    }
}
