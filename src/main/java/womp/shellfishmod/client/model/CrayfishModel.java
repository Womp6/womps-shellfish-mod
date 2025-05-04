package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class CrayfishModel extends HierarchicalModel<CrayfishEntity> {

    private final ModelPart crayfish;

    public CrayfishModel(ModelPart root) {
        this.crayfish = root.getChild("crayfish");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition crayfish = modelPartData.addOrReplaceChild("crayfish", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = crayfish.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -3.0F, -5.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(10, 17).addBox(-2.0F, -3.0F, 1.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(1, 1).addBox(-1.5F, -2.25F, 2.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 10).addBox(-3.0F, -1.25F, 7.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 9).addBox(1.0F, -1.25F, 7.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 1).addBox(-4.0F, -1.25F, 8.75F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 0).addBox(-4.5F, -1.25F, 9.75F, 9.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition claws = body.addOrReplaceChild("claws", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition claw1 = claws.addOrReplaceChild("claw1", CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 2).addBox(-3.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-4.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        claw1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(13, 6).addBox(-7.0F, -1.57F, -2.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1447F, -0.6855F, -0.2262F));

        PartDefinition claw2 = claws.addOrReplaceChild("claw2", CubeListBuilder.create().texOffs(10, 24).addBox(4.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 9).addBox(2.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(3.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        claw2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(13, 7).addBox(3.0F, -1.57F, -2.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1447F, 0.6855F, 0.2262F));

        PartDefinition antennas = body.addOrReplaceChild("antennas", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition antenna1 = antennas.addOrReplaceChild("antenna1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        antenna1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(0.3F, -0.3F, -9.3F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2411F, -0.2821F, -1.5048F));

        PartDefinition antenna2 = antennas.addOrReplaceChild("antenna2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        antenna2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -9.3F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2411F, 0.2821F, 1.6032F));

        PartDefinition legs = crayfish.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_1 = legs.addOrReplaceChild("leg_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_1.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 6).addBox(-4.3F, -1.65F, -3.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leg_2 = legs.addOrReplaceChild("leg_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        leg_2.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 4).addBox(-4.3F, -1.65F, -1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leg_3 = legs.addOrReplaceChild("leg_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        leg_3.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 5).addBox(-4.3F, -1.65F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leg_4 = legs.addOrReplaceChild("leg_4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_4.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 7).addBox(1.3F, -1.65F, -3.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition leg_5 = legs.addOrReplaceChild("leg_5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        leg_5.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(13, 13).addBox(1.3F, -1.65F, -1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition leg_6 = legs.addOrReplaceChild("leg_6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        leg_6.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(13, 14).addBox(1.3F, -1.65F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.3491F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(CrayfishEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isInWater()) {
            this.animateWalk(MoreShellfishAnimations.CRAYFISH_WALK, limbSwing, limbSwingAmount, 12, 15f);
        }
        this.animate(entity.moveAnimationState, MoreShellfishAnimations.CRAYFISH_WALK, ageInTicks, 2f);
        this.animate(entity.idleAnimationState, MoreShellfishAnimations.CRAYFISH_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        crayfish.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return crayfish;
    }
}
