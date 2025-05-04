package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.client.ShellfishClient;

public class SeaUrchinRenderer extends MobEntityRenderer<SeaUrchinEntity, SeaUrchinModel> {
    
    public SeaUrchinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SeaUrchinModel(renderManager.getPart(ShellfishClient.SEA_URCHIN_MODEL)), 0.125f);
    }

    @Override
    public Identifier getTexture(SeaUrchinEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return new Identifier("shellfish", "textures/entity/sea_urchin/sea_urchin_" + variant + ".png");
    }

    @Override
    public void render(SeaUrchinEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
