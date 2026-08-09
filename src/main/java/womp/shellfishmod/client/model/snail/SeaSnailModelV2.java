package womp.shellfishmod.client.model.snail;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SeaSnailModelV2 extends SeaSnailModel {

	public SeaSnailModelV2(ModelPart root) {
		super(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition sea_snail = modelPartData.addOrReplaceChild("sea_snail", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = sea_snail.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition eyes = sea_snail.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		eyes.addOrReplaceChild("eye2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3478F, 0.0298F, -0.082F));

		eyes.addOrReplaceChild("eye1_r1", CubeListBuilder.create().texOffs(2, 0).addBox(0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3478F, -0.0298F, 0.082F));

		PartDefinition shell = sea_snail.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shell2 = shell.addOrReplaceChild("shell2", CubeListBuilder.create().texOffs(22, 27).addBox(-1.5F, -4.75F, 3.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 27).addBox(-1.0F, -4.25F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		shell2.addOrReplaceChild("shell_r1", CubeListBuilder.create().texOffs(11, 18).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(24, 18).addBox(-1.5F, -4.1F, -3.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 1.25F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(modelData, 32, 32);
	}
}
