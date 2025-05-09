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
        int variant = var1.getVariant().getIndex();
        return Identifier.of("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(MusselEntity livingEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        int variant = livingEntity.getVariant().getIndex();

        if(livingEntity.isBaby() && variant != 3) {
            matrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        if(!livingEntity.isBaby() && variant != 3) {
            matrixStack.scale(0.8f, 0.8f, 0.8f);
        }

        if(livingEntity.isBaby() && variant == 3) {
            matrixStack.scale(0.35f, 0.35f, 0.35f);
        }

        if(variant == 3 && !livingEntity.isBaby()) {
            matrixStack.scale(0.55f, 0.55f, 0.55f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
