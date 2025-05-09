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
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class ClamModel extends SinglePartEntityModel<ClamEntity> {

   private final ModelPart clam;

	public ClamModel(ModelPart root) {
		this.clam = root.getChild("clam");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData clam = modelPartData.addChild("clam", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData mainbody = clam.addChild("mainbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		mainbody.addChild("mainbody1", ModelPartBuilder.create().uv(0, 5).cuboid(-2.5F, -1.0F, -2.25F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		mainbody.addChild("mainbody2", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -2.25F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		clam.addChild("bodyend", ModelPartBuilder.create().uv(11, 10).cuboid(-2.0F, -1.5F, 1.75F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		clam.addChild("insides", ModelPartBuilder.create().uv(0, 10).cuboid(-2.0F, -1.5F, -1.25F, 4.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ClamEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.moveAnimationState, ShellfishAnimations.CLAM_MOVE, ageInTicks, 1f);
		this.updateAnimation(entity.idleAnimationState, ShellfishAnimations.CLAM_IDLE, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		clam.render(matrices, vertexConsumer, light, overlay, color);
	}

    @Override
	public ModelPart getPart() {
		return clam;
	}
}
