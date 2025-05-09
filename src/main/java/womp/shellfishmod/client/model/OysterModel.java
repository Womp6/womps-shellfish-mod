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
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class OysterModel extends SinglePartEntityModel<OysterEntity> {

	private final ModelPart oyster;

	public OysterModel(ModelPart root) {
		this.oyster = root.getChild("oyster");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData oyster = modelPartData.addChild("oyster", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		oyster.addChild("topshell", ModelPartBuilder.create().uv(0, 0).cuboid(-3.25F, -2.0F, -3.5F, 5.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.75F, 0.0F, 0.0F));

		oyster.addChild("bottomshell", ModelPartBuilder.create().uv(0, 7).cuboid(-2.5F, -1.0F, -3.5F, 5.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		oyster.addChild("innards", ModelPartBuilder.create().uv(0, 14).cuboid(-2.5F, -1.5F, -2.5F, 4.0F, 1.0F, 4.99F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -0.25F, 0.0F));

		oyster.addChild("bodyend", ModelPartBuilder.create().uv(13, 14).cuboid(-2.5F, -1.5F, 2.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(OysterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.moveAnimationState, ShellfishAnimations.OYSTER_MOVE, ageInTicks, 1f);
		this.updateAnimation(entity.idleAnimationState, ShellfishAnimations.OYSTER_IDLE, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		oyster.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return oyster;
	}
}