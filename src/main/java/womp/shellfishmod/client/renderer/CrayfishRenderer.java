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
    public void render(ShellfishRenderState<Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.baby) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
         }
         if(!entity.baby) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

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
        crayfishState.idleAnimationState.copyFrom(crayfish.idleAnimationState);
        crayfishState.moveAnimationState.copyFrom(crayfish.moveAnimationState);
    }
}
