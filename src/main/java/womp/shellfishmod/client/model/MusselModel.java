package womp.shellfishmod.client.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

public class MusselModel extends EntityModel<ShellfishRenderState<MusselEntity.Variant>> {

    private final KeyframeAnimation moveAnimation;
    private final KeyframeAnimation idleAnimation;

    public MusselModel(ModelPart root) {
        super(root);
        this.moveAnimation = MoreShellfishAnimations.MUSSEL_MOVE.bake(root);
        this.idleAnimation = MoreShellfishAnimations.MUSSEL_IDLE.bake(root);
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
    public void setupAnim(ShellfishRenderState<MusselEntity.Variant> entity) {
        super.setupAnim(entity);
        this.moveAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 1f);
        this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
    }
}
