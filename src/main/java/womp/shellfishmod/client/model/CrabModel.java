package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class CrabModel extends SinglePartEntityModel<CrabEntity> {

    private final ModelPart crab;

	public CrabModel(ModelPart root) {
		this.crab = root.getChild("crab");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData crab = modelPartData.addChild("crab", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = crab.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.75F, -2.5F, 6.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyes = body.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eye = eyes.addChild("eye", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		eye.addChild("eye3_r1", ModelPartBuilder.create().uv(26, 0).cuboid(0.25F, -4.75F, -2.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 0).cuboid(0.25F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, 0.25F, 0.0F, 0.0F, 0.0873F));

		ModelPartData eye2 = eyes.addChild("eye2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		eye2.addChild("eye2_r1", ModelPartBuilder.create().uv(22, 0).mirrored().cuboid(-1.25F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 4).mirrored().cuboid(-1.25F, -4.75F, -2.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5F, 0.0F, 0.25F, 0.0F, 0.0F, -0.0873F));

		ModelPartData claws = body.addChild("claws", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData claw1 = claws.addChild("claw1", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		claw1.addChild("claw4_r1", ModelPartBuilder.create().uv(12, 23).cuboid(3.5F, -1.58F, -6.05F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 27).cuboid(3.5F, -1.58F, -4.05F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 27).cuboid(4.5F, -1.58F, -6.05F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.5F, 0.25F, 0.0623F, 0.3312F, 0.0208F));

		claw1.addChild("claw1_r1", ModelPartBuilder.create().uv(19, 9).cuboid(3.82F, -1.2F, -5.4F, 1.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.25F, -0.5F, -1.75F, 0.0775F, -0.3494F, 0.0151F));

		ModelPartData claw2 = claws.addChild("claw2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		claw2.addChild("claw4_r2", ModelPartBuilder.create().uv(22, 20).mirrored().cuboid(-3.5F, -1.58F, -6.05F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(22, 24).mirrored().cuboid(-4.5F, -1.58F, -4.05F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(19, 27).mirrored().cuboid(-6.5F, -1.58F, -6.05F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -0.5F, 0.25F, 0.0623F, -0.3312F, -0.0208F));

		claw2.addChild("claw1_r2", ModelPartBuilder.create().uv(19, 14).mirrored().cuboid(-4.82F, -1.2F, -5.4F, 1.0F, 0.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.25F, -0.5F, -1.75F, 0.0775F, 0.3494F, -0.0151F));

		ModelPartData leg = crab.addChild("leg", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leg1 = leg.addChild("leg1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		leg1.addChild("leg_r1", ModelPartBuilder.create().uv(0, 8).cuboid(2.05F, -1.65F, 1.45F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.3054F));

		ModelPartData leg2 = leg.addChild("leg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		leg2.addChild("leg2_r1", ModelPartBuilder.create().uv(0, 11).cuboid(2.05F, -1.65F, -0.425F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3054F));

		ModelPartData leg3 = leg.addChild("leg3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		leg3.addChild("leg3_r1", ModelPartBuilder.create().uv(0, 14).cuboid(2.05F, -1.65F, -2.3F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		ModelPartData leg4 = leg.addChild("leg4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		leg4.addChild("leg4_r1", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-5.05F, -1.65F, 1.45F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData leg5 = leg.addChild("leg5", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		leg5.addChild("leg5_r1", ModelPartBuilder.create().uv(0, 20).mirrored().cuboid(-5.05F, -1.65F, -0.425F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData leg6 = leg.addChild("leg6", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		leg6.addChild("leg6_r1", ModelPartBuilder.create().uv(0, 23).mirrored().cuboid(-5.05F, -1.65F, -2.3F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(CrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		if (!entity.isTouchingWater()) {
			this.animateMovement(MoreShellfishAnimations.CRAB_WALK, limbSwing, limbSwingAmount, 12, 15f);
		}
		this.updateAnimation(entity.moveAnimationState, MoreShellfishAnimations.CRAB_WALK, ageInTicks, 2f);
		this.updateAnimation(entity.idleAnimationState, MoreShellfishAnimations.CRAB_IDLE, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		crab.render(matrices, vertexConsumer, light, overlay, color);
	}

    @Override
    public ModelPart getPart() {
        return crab;
    }
}
