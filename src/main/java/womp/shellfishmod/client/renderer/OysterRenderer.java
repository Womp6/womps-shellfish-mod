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
        int variant = var1.getVariant().getIndex();
        return new Identifier("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void render(OysterEntity livingEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
                    matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
