package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.client.ShellfishClient;

public class OysterRenderer extends MobEntityRenderer<OysterEntity, OysterModel> {

    public OysterRenderer(Context ctx) {
        super(ctx, new OysterModel(ctx.getPart(ShellfishClient.OYSTER_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(OysterEntity var1) {
        int variant = var1.getVariant();
        return new Identifier("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void render(OysterEntity entity, float f, float g, MatrixStack poseStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        int variant = entity.getVariant();                  
        if (variant == 0) entity.scale(poseStack, 0.8f, 0.4f);
        else if (variant == 2 || variant == 3) entity.scale(poseStack, 1.2f, 0.85f, 0.5f);
        else entity.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, f, g, poseStack, vertexConsumerProvider, i);
    }
}
