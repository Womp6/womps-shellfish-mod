package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.client.ShellfishClient;

public class MusselRenderer extends MobEntityRenderer<MusselEntity, MusselModel> {
    
    public MusselRenderer(Context ctx) {
        super(ctx, new MusselModel(ctx.getPart(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(MusselEntity var1) {
        int variant = var1.getVariant();
        return new Identifier("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(MusselEntity entity, float f, float g, MatrixStack poseStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        int variant = entity.getVariant();                  
        if (variant == 1) entity.scale(poseStack, 0.95f, 0.65f, 0.4f);
        else if (variant == 2) entity.scale(poseStack, 1.1f, 0.8f, 0.4f);
        else if (variant == 3) entity.scale(poseStack, 0.55f, 0.3f);
        else entity.scale(poseStack, 0.8f, 0.4f);

        super.render(entity, f, g, poseStack, vertexConsumerProvider, i);
    }
}
