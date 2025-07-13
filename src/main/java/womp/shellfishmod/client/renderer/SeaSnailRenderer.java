package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.SeaSnailModel;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.client.ShellfishClient;

public class SeaSnailRenderer extends MobEntityRenderer<SeaSnailEntity, SeaSnailModel> {

    public SeaSnailRenderer(Context context) {
        super(context, new SeaSnailModel(context.getPart(ShellfishClient.SEA_SNAIL_MODEL)), 0.25f);
    }

    @Override
    public Identifier getTexture(SeaSnailEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return Identifier.of("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void render(SeaSnailEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        
        int variant = entity.getVariant().getIndex();                  
        if (variant == 0) entity.scale(poseStack, 0.7f, 0.35f);
        else if (variant == 2) entity.scale(poseStack, 1.8f, 1.0f, 0.5f);
        else if (variant == 3) entity.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.scale(poseStack, 1.3f, 0.9f, 0.5f);
        else entity.scale(poseStack, 1.0f, 0.5f);
        
        SeaSnailModel seaSnailModel = (SeaSnailModel)this.getModel();
        if (variant == 0) {
            seaSnailModel.shell1.visible = true;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 1) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = true;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 2) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = true;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 3) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = true;
            seaSnailModel.shell5.visible = false;
            seaSnailModel.sea_snail.visible = true;
        } else if (variant == 4) {
            seaSnailModel.shell1.visible = false;
            seaSnailModel.shell2.visible = false;
            seaSnailModel.shell3.visible = false;
            seaSnailModel.shell4.visible = false;
            seaSnailModel.shell5.visible = true;
            seaSnailModel.sea_snail.visible = true;
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
