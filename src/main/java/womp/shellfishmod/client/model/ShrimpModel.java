package womp.shellfishmod.client.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ShrimpEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class ShrimpModel extends EntityModel<ShellfishRenderState<ShrimpEntity.Variant>> {

	private final KeyframeAnimation swimAnimation;
	private final KeyframeAnimation moveAnimation;
	private final KeyframeAnimation idleAnimation;

	public ShrimpModel(ModelPart root) {
		super(root);
		this.swimAnimation = ShellfishAnimations.SHRIMP_SWIM.bake(root);
		this.moveAnimation = ShellfishAnimations.SHRIMP_WALK.bake(root);
		this.idleAnimation = ShellfishAnimations.SHRIMP_IDLE.bake(root);
	}
    
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition shrimp = modelPartData.addOrReplaceChild("shrimp", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = shrimp.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -5.25F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(-2.0F, -3.0F, -6.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 1).addBox(-1.5F, -3.0F, -0.25F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 6).addBox(-1.5F, -3.0F, 1.75F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-1.5F, -2.5F, 5.75F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 10).addBox(-3.0F, -2.5F, 6.75F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-2.5F, -2.5F, 5.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).mirror().addBox(1.5F, -2.5F, 5.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(3, 12).addBox(-2.0F, -2.5F, 4.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(3, 14).mirror().addBox(1.0F, -2.5F, 4.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm = main.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm1 = arm.addOrReplaceChild("arm1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		arm1.addOrReplaceChild("arm2_r1", CubeListBuilder.create().texOffs(-1, 16).addBox(2.75F, -0.25F, -7.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.25F, 0.25F, 0.0F, 0.0873F, 0.0F));

		PartDefinition arm2 = arm.addOrReplaceChild("arm2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		arm2.addOrReplaceChild("arm3_r1", CubeListBuilder.create().texOffs(-3, 16).mirror().addBox(-3.75F, -0.25F, -7.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -1.25F, 0.25F, 0.0F, -0.0873F, 0.0F));

		PartDefinition antenna = main.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition antenna1 = antenna.addOrReplaceChild("antenna1", CubeListBuilder.create(), PartPose.offset(1.4F, -2.0F, -4.0F));

		antenna1.addOrReplaceChild("antenna2_r1", CubeListBuilder.create().texOffs(17, 5).addBox(0.19F, -1.115F, -0.05F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 0.425F, -0.25F, 0.1787F, 0.1629F, 0.0068F));

		PartDefinition antenna2 = antenna.addOrReplaceChild("antenna2", CubeListBuilder.create(), PartPose.offset(-1.4F, -2.0F, -4.0F));

		antenna2.addOrReplaceChild("antenna3_r1", CubeListBuilder.create().texOffs(17, 7).mirror().addBox(-0.19F, -1.115F, -0.05F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.4F, 0.425F, -0.25F, 0.1787F, -0.1629F, -0.0068F));

		PartDefinition otherantennas = main.addOrReplaceChild("otherantennas", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ot1 = otherantennas.addOrReplaceChild("ot1", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, -5.0F));

		ot1.addOrReplaceChild("ot1_r1", CubeListBuilder.create().texOffs(23, 14).addBox(-0.1F, -1.11F, -2.25F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.5F, -1.0F, -0.6545F, -0.1745F, 0.0F));

		PartDefinition ot2 = otherantennas.addOrReplaceChild("ot2", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.0F, -5.0F));

		ot2.addOrReplaceChild("ot2_r1", CubeListBuilder.create().texOffs(23, 16).mirror().addBox(0.1F, -1.11F, -2.25F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, -0.5F, -1.0F, -0.6545F, 0.1745F, 0.0F));

		PartDefinition ot3 = otherantennas.addOrReplaceChild("ot3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		ot3.addOrReplaceChild("ot3_r1", CubeListBuilder.create().texOffs(23, 15).addBox(0.0F, -0.75F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.25F, -5.25F, 0.0F, 0.0F, 0.0F));

		PartDefinition ot4 = otherantennas.addOrReplaceChild("ot4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		ot4.addOrReplaceChild("ot4_r1", CubeListBuilder.create().texOffs(28, 15).mirror().addBox(0.0F, -0.75F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.25F, -5.25F, 0.0F, 0.0F, 0.0F));

		PartDefinition leg = shrimp.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftlegs = leg.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg1 = leftlegs.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.7F));

		leg1.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(8, 12).addBox(0.2163F, -0.4211F, -0.77F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 0.2F, 0.0F, 0.0F, 0.4363F));

		PartDefinition leg2 = leftlegs.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.5F));

		leg2.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(8, 14).addBox(0.2163F, -0.4211F, -0.71F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 0.25F, 0.0F, 0.0F, 0.4363F));

		PartDefinition leg3 = leftlegs.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -4.25F));

		leg3.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(8, 16).addBox(0.2163F, -0.4211F, -0.48F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition rightlegs = leg.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg4 = rightlegs.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.7F));

		leg4.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(8, 18).mirror().addBox(-3.1588F, -0.4434F, -0.77F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.2F, 0.0F, 0.0F, -0.4363F));

		PartDefinition leg5 = rightlegs.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.5F));

		leg5.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(8, 20).mirror().addBox(-3.1588F, -0.4434F, -0.71F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.25F, 0.0F, 0.0F, -0.4363F));

		PartDefinition leg6 = rightlegs.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -4.25F));

		leg6.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(8, 22).mirror().addBox(-3.1588F, -0.4434F, -0.48F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		return LayerDefinition.create(modelData, 32, 32);
	}

	@Override
	public void setupAnim(ShellfishRenderState<ShrimpEntity.Variant> entity) {
		super.setupAnim(entity);
		if (!entity.isInWater) {
			this.moveAnimation.applyWalk(entity.walkAnimationPos, entity.walkAnimationSpeed, 12, 15f);
		}
		this.swimAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 2f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
	}
}
