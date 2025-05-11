package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class WaterLettuceModel {

    private final ModelPart lettuce;

    public WaterLettuceModel(ModelPart root) {
        this.lettuce = root.getChild("lettuce");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lettuce = modelPartData.addOrReplaceChild("lettuce", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.25F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.25F, 0.0F));

        lettuce.addOrReplaceChild("topsmall4_r1", CubeListBuilder.create().texOffs(6, 20).addBox(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 16).addBox(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

        lettuce.addOrReplaceChild("topsmall3_r1", CubeListBuilder.create().texOffs(10, 20).addBox(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 16).addBox(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

        lettuce.addOrReplaceChild("topsmall2_r1", CubeListBuilder.create().texOffs(14, 20).addBox(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

        lettuce.addOrReplaceChild("topsmall1_r1", CubeListBuilder.create().texOffs(18, 20).addBox(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 3).addBox(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall8_r1", CubeListBuilder.create().texOffs(18, 6).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 2.3562F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall7_r1", CubeListBuilder.create().texOffs(18, 8).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -2.3562F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall6_r1", CubeListBuilder.create().texOffs(18, 10).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.7854F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall5_r1", CubeListBuilder.create().texOffs(18, 12).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.7854F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall4_r1", CubeListBuilder.create().texOffs(11, 19).addBox(-1.5F, -0.25F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 4).addBox(-2.0F, -0.25F, 2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall3_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -0.25F, -2.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 8).addBox(-2.0F, -0.25F, -6.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        lettuce.addOrReplaceChild("bottomsmall2_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-2.0F, -0.25F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-6.0F, -0.25F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        lettuce.addOrReplaceChild("bottomsmall1_r1", CubeListBuilder.create().texOffs(18, 17).addBox(1.0F, -0.25F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 12).addBox(2.0F, -0.25F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    public void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
        lettuce.render(matrices, buffer, light, overlay);
    }

    public RenderType getLayer() {
        return RenderType.cutout();
    }
}
