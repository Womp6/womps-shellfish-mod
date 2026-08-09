package womp.shellfishmod.client.model.snail;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class SeaSnailModelV2 extends SeaSnailModel {

	public SeaSnailModelV2(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sea_snail = modelPartData.addChild("sea_snail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = sea_snail.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		body.addChild("body1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		body.addChild("body2", ModelPartBuilder.create().uv(11, 0).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData eyes = sea_snail.addChild("eyes", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		eyes.addChild("eye2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3478F, 0.0298F, -0.082F));

		eyes.addChild("eye1_r1", ModelPartBuilder.create().uv(2, 0).cuboid(0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3478F, -0.0298F, 0.082F));

		ModelPartData shell = sea_snail.addChild("shell", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData shell2 = shell.addChild("shell2", ModelPartBuilder.create().uv(22, 27).cuboid(-1.5F, -4.75F, 3.5F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(11, 27).cuboid(-1.0F, -4.25F, 5.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		shell2.addChild("shell_r1", ModelPartBuilder.create().uv(11, 18).cuboid(-2.0F, -5.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(24, 18).cuboid(-1.5F, -4.1F, -3.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 1.25F, -0.0873F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 32, 32);
	}
}
