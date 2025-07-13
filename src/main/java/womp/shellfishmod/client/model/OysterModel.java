package womp.shellfishmod.client.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class OysterModel extends EntityModel<ShellfishRenderState<OysterEntity.Variant>> {

    private final KeyframeAnimation moveAnimation;
    private final KeyframeAnimation idleAnimation;

    public OysterModel(ModelPart root) {
        super(root);
        this.moveAnimation = ShellfishAnimations.OYSTER_MOVE.bake(root);
        this.idleAnimation = ShellfishAnimations.OYSTER_IDLE.bake(root);
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
    public void setupAnim(ShellfishRenderState<OysterEntity.Variant> entity) {
        super.setupAnim(entity);
        this.moveAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 1f);
        this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
    }
}
