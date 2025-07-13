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
        int variant = animatable.getVariant();
        if (animatable.hasCustomName() && "Supercrayfish".equals(animatable.getName().getString())) {
            return new Identifier("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return new Identifier("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(CrayfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        
        int variant = entity.getVariant();                  
        if (variant == 11 || variant == 17 || variant == 25) entity.scale(poseStack, 0.9f, 0.65f, 0.4f);
        else if (variant == 18) entity.scale(poseStack, 1.4f, 0.9f, 0.4f);
        else if (variant == 10 || variant == 21) entity.scale(poseStack, 0.4f, 0.2f);
        else if (variant == 14 || variant == 19) entity.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 23) entity.scale(poseStack, 0.5f, 0.25f);
        else entity.scale(poseStack, 0.75f, 0.4f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
