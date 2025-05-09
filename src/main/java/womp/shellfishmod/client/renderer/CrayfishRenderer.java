package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.client.ShellfishClient;

public class CrayfishRenderer extends MobEntityRenderer<CrayfishEntity, CrayfishModel> {

   public CrayfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrayfishModel(renderManager.getPart(ShellfishClient.CRAYFISH_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(CrayfishEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        if (animatable.hasCustomName() && "Supercrayfish".equals(animatable.getName().getString())) {
            return Identifier.of("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return Identifier.of("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(CrayfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
         }
         if(entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }
        if(!entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
