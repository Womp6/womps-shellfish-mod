package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class CrabModel extends HierarchicalModel<CrabEntity> {

    private final ModelPart crab;

    public CrabModel(ModelPart root) {
        this.crab = root.getChild("crab");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition crab = modelPartData.addOrReplaceChild("crab", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = crab.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.75F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye = eyes.addOrReplaceChild("eye", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        eye.addOrReplaceChild("eye3_r1", CubeListBuilder.create().texOffs(26, 0).addBox(0.25F, -4.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(0.25F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.25F, 0.0F, 0.0F, 0.0873F));

        PartDefinition eye2 = eyes.addOrReplaceChild("eye2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        eye2.addOrReplaceChild("eye2_r1", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(-1.25F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 4).mirror().addBox(-1.25F, -4.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.25F, 0.0F, 0.0F, -0.0873F));

        PartDefinition claws = body.addOrReplaceChild("claws", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition claw1 = claws.addOrReplaceChild("claw1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

        claw1.addOrReplaceChild("claw4_r1", CubeListBuilder.create().texOffs(12, 23).addBox(3.5F, -1.58F, -6.05F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 27).addBox(3.5F, -1.58F, -4.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 27).addBox(4.5F, -1.58F, -6.05F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.5F, 0.25F, 0.0623F, 0.3312F, 0.0208F));

        claw1.addOrReplaceChild("claw1_r1", CubeListBuilder.create().texOffs(19, 9).addBox(3.82F, -1.2F, -5.4F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -0.5F, -1.75F, 0.0775F, -0.3494F, 0.0151F));

        PartDefinition claw2 = claws.addOrReplaceChild("claw2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

        claw2.addOrReplaceChild("claw4_r2", CubeListBuilder.create().texOffs(22, 20).mirror().addBox(-3.5F, -1.58F, -6.05F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 24).mirror().addBox(-4.5F, -1.58F, -4.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(19, 27).mirror().addBox(-6.5F, -1.58F, -6.05F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -0.5F, 0.25F, 0.0623F, -0.3312F, -0.0208F));

        claw2.addOrReplaceChild("claw1_r2", CubeListBuilder.create().texOffs(19, 14).mirror().addBox(-4.82F, -1.2F, -5.4F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, -0.5F, -1.75F, 0.0775F, 0.3494F, -0.0151F));

        PartDefinition leg = crab.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg1 = leg.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        leg1.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(0, 8).addBox(2.05F, -1.65F, 1.45F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        leg2.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(0, 11).addBox(2.05F, -1.65F, -0.425F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition leg3 = leg.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg3.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(0, 14).addBox(2.05F, -1.65F, -2.3F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition leg4 = leg.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        leg4.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-5.05F, -1.65F, 1.45F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition leg5 = leg.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        leg5.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-5.05F, -1.65F, -0.425F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition leg6 = leg.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg6.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-5.05F, -1.65F, -2.3F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(CrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isInWater()) {
            this.animateWalk(MoreShellfishAnimations.CRAB_WALK, limbSwing, limbSwingAmount, 12, 15f);
        }
        this.animate(entity.moveAnimationState, MoreShellfishAnimations.CRAB_WALK, ageInTicks, 2f);
        this.animate(entity.idleAnimationState, MoreShellfishAnimations.CRAB_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        crab.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return crab;
    }
}
