package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ShrimpEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class ShrimpModel extends EntityModel<ShellfishRenderState<ShrimpEntity.Variant>> {

	private final Animation swimAnimation;
	private final Animation moveAnimation;
	private final Animation idleAnimation;

	public ShrimpModel(ModelPart root) {
		super(root);
		this.swimAnimation = ShellfishAnimations.SHRIMP_SWIM.createAnimation(root);
		this.moveAnimation = ShellfishAnimations.SHRIMP_WALK.createAnimation(root);
		this.idleAnimation = ShellfishAnimations.SHRIMP_IDLE.createAnimation(root);
	}
    
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData shrimp = modelPartData.addChild("shrimp", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData main = shrimp.addChild("main", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -5.25F, 4.0F, 2.0F, 5.0F, new Dilation(0.0F))
		.uv(14, 0).cuboid(-2.0F, -3.0F, -6.25F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		main.addChild("tail", ModelPartBuilder.create().uv(22, 1).cuboid(-1.5F, -3.0F, -0.25F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 6).cuboid(-1.5F, -3.0F, 1.75F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-1.5F, -2.5F, 5.75F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(-1, 10).cuboid(-3.0F, -2.5F, 6.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 12).cuboid(-2.5F, -2.5F, 5.75F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 14).mirrored().cuboid(1.5F, -2.5F, 5.75F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(3, 12).cuboid(-2.0F, -2.5F, 4.75F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(3, 14).mirrored().cuboid(1.0F, -2.5F, 4.75F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData arm = main.addChild("arm", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData arm1 = arm.addChild("arm1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		arm1.addChild("arm2_r1", ModelPartBuilder.create().uv(-1, 16).cuboid(2.75F, -0.25F, -7.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -1.25F, 0.25F, 0.0F, 0.0873F, 0.0F));

		ModelPartData arm2 = arm.addChild("arm2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		arm2.addChild("arm3_r1", ModelPartBuilder.create().uv(-3, 16).mirrored().cuboid(-3.75F, -0.25F, -7.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.5F, -1.25F, 0.25F, 0.0F, -0.0873F, 0.0F));

		ModelPartData antenna = main.addChild("antenna", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData antenna1 = antenna.addChild("antenna1", ModelPartBuilder.create(), ModelTransform.origin(1.4F, -2.0F, -4.0F));

		antenna1.addChild("antenna2_r1", ModelPartBuilder.create().uv(17, 5).cuboid(0.19F, -1.115F, -0.05F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.4F, 0.425F, -0.25F, 0.1787F, 0.1629F, 0.0068F));

		ModelPartData antenna2 = antenna.addChild("antenna2", ModelPartBuilder.create(), ModelTransform.origin(-1.4F, -2.0F, -4.0F));

		antenna2.addChild("antenna3_r1", ModelPartBuilder.create().uv(17, 7).mirrored().cuboid(-0.19F, -1.115F, -0.05F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.4F, 0.425F, -0.25F, 0.1787F, -0.1629F, -0.0068F));

		ModelPartData otherantennas = main.addChild("otherantennas", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData ot1 = otherantennas.addChild("ot1", ModelPartBuilder.create(), ModelTransform.origin(2.0F, -2.0F, -5.0F));

		ot1.addChild("ot1_r1", ModelPartBuilder.create().uv(23, 14).cuboid(-0.1F, -1.11F, -2.25F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -0.5F, -1.0F, -0.6545F, -0.1745F, 0.0F));

		ModelPartData ot2 = otherantennas.addChild("ot2", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -2.0F, -5.0F));

		ot2.addChild("ot2_r1", ModelPartBuilder.create().uv(23, 16).mirrored().cuboid(0.1F, -1.11F, -2.25F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.25F, -0.5F, -1.0F, -0.6545F, 0.1745F, 0.0F));

		ModelPartData ot3 = otherantennas.addChild("ot3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ot3.addChild("ot3_r1", ModelPartBuilder.create().uv(23, 15).cuboid(0.0F, -0.75F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -2.25F, -5.25F, 0.0F, 0.0F, 0.0F));

		ModelPartData ot4 = otherantennas.addChild("ot4", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ot4.addChild("ot4_r1", ModelPartBuilder.create().uv(28, 15).mirrored().cuboid(0.0F, -0.75F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -2.25F, -5.25F, 0.0F, 0.0F, 0.0F));

		ModelPartData leg = shrimp.addChild("leg", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData leftlegs = leg.addChild("leftlegs", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData leg1 = leftlegs.addChild("leg1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -0.7F));

		leg1.addChild("leg1_r1", ModelPartBuilder.create().uv(8, 12).cuboid(0.2163F, -0.4211F, -0.77F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -1.0F, 0.2F, 0.0F, 0.0F, 0.4363F));

		ModelPartData leg2 = leftlegs.addChild("leg2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -2.5F));

		leg2.addChild("leg2_r1", ModelPartBuilder.create().uv(8, 14).cuboid(0.2163F, -0.4211F, -0.71F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -1.0F, 0.25F, 0.0F, 0.0F, 0.4363F));

		ModelPartData leg3 = leftlegs.addChild("leg3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -4.25F));

		leg3.addChild("leg3_r1", ModelPartBuilder.create().uv(8, 16).cuboid(0.2163F, -0.4211F, -0.48F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		ModelPartData rightlegs = leg.addChild("rightlegs", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData leg4 = rightlegs.addChild("leg4", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -0.7F));

		leg4.addChild("leg4_r1", ModelPartBuilder.create().uv(8, 18).mirrored().cuboid(-3.1588F, -0.4434F, -0.77F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -1.0F, 0.2F, 0.0F, 0.0F, -0.4363F));

		ModelPartData leg5 = rightlegs.addChild("leg5", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -2.5F));

		leg5.addChild("leg5_r1", ModelPartBuilder.create().uv(8, 20).mirrored().cuboid(-3.1588F, -0.4434F, -0.71F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -1.0F, 0.25F, 0.0F, 0.0F, -0.4363F));

		ModelPartData leg6 = rightlegs.addChild("leg6", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -4.25F));

		leg6.addChild("leg6_r1", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-3.1588F, -0.4434F, -0.48F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ShellfishRenderState<ShrimpEntity.Variant> entity) {
		super.setAngles(entity);
		if (!entity.touchingWater) {
			this.moveAnimation.applyWalking(entity.limbSwingAnimationProgress, entity.limbSwingAmplitude, 12, 15f);
		}
		this.swimAnimation.apply(entity.moveAnimationState, entity.age, 2f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.age, 1f);
	}
}
