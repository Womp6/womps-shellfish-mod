package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.entity.CrayfishEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class CrayfishRenderer extends MobEntityRenderer<CrayfishEntity, ShellfishRenderState<Variant>, CrayfishModel> {

   public CrayfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrayfishModel(renderManager.getPart(ShellfishClient.CRAYFISH_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<Variant> animatable) {
        int variant = animatable.variant.getIndex();
        if (animatable.customName != null && "Supercrayfish".equals(animatable.customName.getString())) {
            return Identifier.of("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return Identifier.of("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(ShellfishRenderState<Variant> entity, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        
        int variant = entity.variant.getIndex();                  
        if (variant == 11 || variant == 17 || variant == 25) entity.shellfish.scale(poseStack, 0.9f, 0.65f, 0.4f);
        else if (variant == 18) entity.shellfish.scale(poseStack, 1.4f, 0.9f, 0.4f);
        else if (variant == 10 || variant == 21) entity.shellfish.scale(poseStack, 0.4f, 0.2f);
        else if (variant == 14 || variant == 19) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 23) entity.shellfish.scale(poseStack, 0.5f, 0.25f);
        else entity.shellfish.scale(poseStack, 0.75f, 0.4f);

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(CrayfishEntity crayfish, ShellfishRenderState<Variant> crayfishState, float f) {
        super.updateRenderState(crayfish, crayfishState, f);
        crayfishState.variant = crayfish.getVariant();
        crayfishState.shellfish = crayfish;
        crayfishState.idleAnimationState.copyFrom(crayfish.idleAnimationState);
        crayfishState.moveAnimationState.copyFrom(crayfish.moveAnimationState);
    }
}
