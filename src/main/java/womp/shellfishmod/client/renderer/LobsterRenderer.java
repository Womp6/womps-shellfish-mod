package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.entity.LobsterEntity;
import womp.shellfishmod.client.ShellfishClient;

public class LobsterRenderer extends MobEntityRenderer<LobsterEntity, LobsterModel> {
    
    public LobsterRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LobsterModel(renderManager.getPart(ShellfishClient.LOBSTER_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(LobsterEntity animatable) {
        int variant = animatable.getVariant();
        return new Identifier("shellfish", "textures/entity/lobster/lobster_" + variant + ".png");
    }

    @Override
    public void render(LobsterEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        int variant = entity.getVariant();                  
        if (variant == 0) entity.scale(poseStack, 1.2f, 0.8f, 0.5f);
        else if (variant == 4) entity.scale(poseStack, 1.3f, 0.85f, 0.5f);
        else entity.scale(poseStack, 1f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
