package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class SeaSnailModel extends HierarchicalModel<SeaSnailEntity> {

    public final ModelPart sea_snail;
    private final ModelPart shell;
    public final ModelPart shell1;
    public final ModelPart shell2;
    public final ModelPart shell3;
    public final ModelPart shell4;
    public final ModelPart shell5;

    public SeaSnailModel(ModelPart root) {
        this.sea_snail = root.getChild("sea_snail");
        this.shell = sea_snail.getChild("shell");
        this.shell1 = shell.getChild("shell1");
        this.shell2 = shell.getChild("shell2");
        this.shell3 = shell.getChild("shell3");
        this.shell4 = shell.getChild("shell4");
        this.shell5 = shell.getChild("shell5");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition sea_snail = modelPartData.addOrReplaceChild("sea_snail", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = sea_snail.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = sea_snail.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        eyes.addOrReplaceChild("eye2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3478F, 0.0298F, -0.082F));

        eyes.addOrReplaceChild("eye1_r1", CubeListBuilder.create().texOffs(2, 0).addBox(0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3478F, -0.0298F, 0.082F));

        PartDefinition shell = sea_snail.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shell1 = shell.addOrReplaceChild("shell1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        shell1.addOrReplaceChild("shell1_r1", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition shell2 = shell.addOrReplaceChild("shell2", CubeListBuilder.create().texOffs(22, 27).addBox(-1.5F, -4.75F, 3.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 27).addBox(-1.0F, -4.25F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        shell2.addOrReplaceChild("shell_r1", CubeListBuilder.create().texOffs(11, 18).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 18).addBox(-1.5F, -4.1F, -3.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 1.25F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shell3 = shell.addOrReplaceChild("shell3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        shell3.addOrReplaceChild("shell6_r1", CubeListBuilder.create().texOffs(7, 22).addBox(-0.5F, -3.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 13).addBox(-1.0F, -4.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        shell3.addOrReplaceChild("shell4_r1", CubeListBuilder.create().texOffs(20, 22).addBox(-1.5F, -4.75F, 3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 16).addBox(-0.5F, -4.25F, -4.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 28).addBox(-1.0F, -4.75F, -2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 13).addBox(-1.5F, -5.0F, -1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(7, 22).addBox(-2.0F, -4.0F, -1.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition shell4 = shell.addOrReplaceChild("shell4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        shell4.addOrReplaceChild("shell3_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.75F, -3.75F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 10).addBox(-2.25F, -4.5F, 0.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 22).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition shell5 = shell.addOrReplaceChild("shell5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        shell5.addOrReplaceChild("shell5_r1", CubeListBuilder.create().texOffs(26, 18).addBox(-0.5F, -3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 15).addBox(-1.0F, -3.5F, 3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        shell5.addOrReplaceChild("shell8_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 24).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(SeaSnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.moveAnimationState, ShellfishAnimations.SNAIL_MOVE, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, ShellfishAnimations.SNAIL_HIDE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        sea_snail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return sea_snail;
    }
}
