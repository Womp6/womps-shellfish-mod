package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.SeaSnailModel;
import womp.shellfishmod.entity.SeaSnailEntity;

public class SeaSnailRenderer extends MobRenderer<SeaSnailEntity, SeaSnailModel> {

    public SeaSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaSnailModel(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(SeaSnailEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void render(SeaSnailEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

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
