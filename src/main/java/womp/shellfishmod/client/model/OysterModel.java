package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class OysterModel extends HierarchicalModel<OysterEntity> {

    private final ModelPart oyster;

    public OysterModel(ModelPart root) {
        this.oyster = root.getChild("oyster");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition oyster = modelPartData.addOrReplaceChild("oyster", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        oyster.addOrReplaceChild("topshell", CubeListBuilder.create().texOffs(0, 0).addBox(-3.25F, -2.0F, -3.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.0F, 0.0F));

        oyster.addOrReplaceChild("bottomshell", CubeListBuilder.create().texOffs(0, 7).addBox(-2.5F, -1.0F, -3.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        oyster.addOrReplaceChild("innards", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -1.5F, -2.5F, 4.0F, 1.0F, 4.99F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.25F, 0.0F));

        oyster.addOrReplaceChild("bodyend", CubeListBuilder.create().texOffs(13, 14).addBox(-2.5F, -1.5F, 2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(OysterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.moveAnimationState, ShellfishAnimations.OYSTER_MOVE, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, ShellfishAnimations.OYSTER_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        oyster.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return oyster;
    }
}
