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
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class ClamModel extends EntityModel<ShellfishRenderState<ClamEntity.Variant>> {

	private final KeyframeAnimation moveAnimation;
	private final KeyframeAnimation idleAnimation;

	public ClamModel(ModelPart root) {
		super(root);
		this.moveAnimation = ShellfishAnimations.CLAM_MOVE.bake(root);
		this.idleAnimation = ShellfishAnimations.CLAM_IDLE.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition clam = modelPartData.addOrReplaceChild("clam", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition mainbody = clam.addOrReplaceChild("mainbody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		mainbody.addOrReplaceChild("mainbody1", CubeListBuilder.create().texOffs(0, 5).addBox(-2.5F, -1.0F, -2.25F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		mainbody.addOrReplaceChild("mainbody2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.25F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		clam.addOrReplaceChild("bodyend", CubeListBuilder.create().texOffs(11, 10).addBox(-2.0F, -1.5F, 1.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		clam.addOrReplaceChild("insides", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -1.5F, -1.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(modelData, 32, 32);
	}

	@Override
	public void setupAnim(ShellfishRenderState<ClamEntity.Variant> entity) {
		super.setupAnim(entity);
		this.moveAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 1f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
	}
}
