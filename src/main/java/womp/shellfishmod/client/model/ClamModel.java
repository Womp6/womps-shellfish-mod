package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class ClamModel extends HierarchicalModel<ClamEntity> {

    private final ModelPart clam;

    public ClamModel(ModelPart root) {
        this.clam = root.getChild("clam");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition clam = modelPartData.addOrReplaceChild("clam", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition mainbody = clam.addOrReplaceChild("mainbody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        mainbody.addOrReplaceChild("mainbody1", CubeListBuilder.create().texOffs(0, 5).addBox(-2.5F, -1.0F, -2.25F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        mainbody.addOrReplaceChild("mainbody2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.25F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        clam.addOrReplaceChild("bodyend", CubeListBuilder.create().texOffs(11, 10).addBox(-2.0F, -1.5F, 1.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        clam.addOrReplaceChild("insides", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -1.5F, -1.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(ClamEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.moveAnimationState, ShellfishAnimations.CLAM_MOVE, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, ShellfishAnimations.CLAM_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        clam.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return clam;
    }
}
