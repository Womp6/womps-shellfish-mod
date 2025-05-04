package womp.shellfishmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

public class MusselModel extends HierarchicalModel<MusselEntity> {

    private final ModelPart mussel;

    public MusselModel(ModelPart root) {
        this.mussel = root.getChild("mussel");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition mussel = modelPartData.addOrReplaceChild("mussel", CubeListBuilder.create(), PartPose.offset(-0.5F, 24.0F, 0.0F));

        mussel.addOrReplaceChild("topshell", CubeListBuilder.create().texOffs(0, 7).addBox(-2.25F, -2.0F, -3.5F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.0F, 0.0F));

        mussel.addOrReplaceChild("bottomshell", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -3.5F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        mussel.addOrReplaceChild("innards", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 1.0F, 4.99F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.25F, 0.0F));

        mussel.addOrReplaceChild("bodyend", CubeListBuilder.create().texOffs(14, 0).addBox(-1.5F, -1.5F, 2.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(MusselEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.moveAnimationState, MoreShellfishAnimations.MUSSEL_MOVE, ageInTicks, 1f);
        this.animate(entity.idleAnimationState, MoreShellfishAnimations.MUSSEL_IDLE, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        mussel.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return mussel;
    }
}
