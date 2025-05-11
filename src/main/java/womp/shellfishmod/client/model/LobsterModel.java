package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.LobsterEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class LobsterModel extends HierarchicalModel<LobsterEntity> {

    private final ModelPart lobster;

    public LobsterModel(ModelPart root) {
        this.lobster = root.getChild("lobster");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lobster = modelPartData.addOrReplaceChild("lobster", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = lobster.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        body.addOrReplaceChild("bodybody", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.75F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 9).addBox(-1.0F, -2.75F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("tailtail", CubeListBuilder.create().texOffs(0, 7).addBox(-7.0F, -2.0F, -1.5F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        tail.addOrReplaceChild("tailend", CubeListBuilder.create().texOffs(14, 21).addBox(-10.0F, -1.5F, -3.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 25).addBox(-7.0F, -1.5F, -2.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 23).addBox(-7.0F, -1.5F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition claw = body.addOrReplaceChild("claw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition claw1 = claw.addOrReplaceChild("claw1", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 0.0F));

        claw1.addOrReplaceChild("claw5_r1", CubeListBuilder.create().texOffs(5, 27).addBox(1.3F, -0.8F, 3.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.75F, -2.5F, 0.0F, 0.8279F, 0.1309F));

        claw1.addOrReplaceChild("claw5_r2", CubeListBuilder.create().texOffs(23, 2).addBox(8.0F, -0.56F, -0.25F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(27, 7).addBox(7.0F, -0.56F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 15).addBox(7.0F, -0.56F, -3.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.75F, -0.5F, 0.0F, 0.2182F, 0.0F));

        PartDefinition claw2 = claw.addOrReplaceChild("claw2", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 0.0F));

        claw2.addOrReplaceChild("claw8_r1", CubeListBuilder.create().texOffs(23, 0).addBox(8.0F, -0.56F, 0.25F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 4).addBox(7.0F, -0.56F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 18).addBox(7.0F, -0.56F, 1.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.75F, 0.5F, 0.0F, -0.2182F, 0.0F));

        claw2.addOrReplaceChild("claw4_r1", CubeListBuilder.create().texOffs(5, 25).addBox(1.3F, -0.8F, -4.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.75F, 2.5F, 0.0F, -0.8279F, 0.1309F));

        PartDefinition annetna = body.addOrReplaceChild("annetna", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition antennaantenna = annetna.addOrReplaceChild("antennaantenna", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition antenna = antennaantenna.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 5.25F, -10.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition antenna1 = antenna.addOrReplaceChild("antenna1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        antenna1.addOrReplaceChild("antenna1_r1", CubeListBuilder.create().texOffs(22, 28).mirror().addBox(-1.5F, -2.35F, -10.1F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 1.0F, -0.109F, -0.3325F, -1.3515F));

        PartDefinition antennaantennatwo = annetna.addOrReplaceChild("antennaantennatwo", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition antennatwo = antennaantennatwo.addOrReplaceChild("antennatwo", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 5.25F, -10.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition antenna2 = antennatwo.addOrReplaceChild("antenna2", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 1.0F));

        antenna2.addOrReplaceChild("antenna2_r1", CubeListBuilder.create().texOffs(22, 30).addBox(-2.5F, -2.35F, -10.1F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.109F, 0.3325F, 1.3515F));

        PartDefinition leg = lobster.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition leg1 = leg.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 0.0F));

        leg1.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(0, 20).addBox(3.75F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg2.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(8, 20).addBox(1.925F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition leg3 = leg.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 0.0F));

        leg3.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.1F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition leg4 = leg.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 0.0F));

        leg4.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(8, 12).addBox(3.75F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition leg5 = leg.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leg5.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(0, 12).addBox(1.925F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition leg6 = leg.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 0.0F));

        leg6.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(8, 16).addBox(0.1F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(LobsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isInWater()) {
            this.animateWalk(MoreShellfishAnimations.LOBSTER_WALK, limbSwing, limbSwingAmount, 12, 15f);
        }
        this.animate(entity.moveAnimationState, MoreShellfishAnimations.LOBSTER_WALK, ageInTicks, 2f);
        this.animate(entity.idleAnimationState, MoreShellfishAnimations.LOBSTER_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        lobster.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return lobster;
    }
}
