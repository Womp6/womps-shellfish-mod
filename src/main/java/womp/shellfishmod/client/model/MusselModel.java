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
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

public class MusselModel extends EntityModel<ShellfishRenderState<MusselEntity.Variant>> {

	private final Animation moveAnimation;
	private final Animation idleAnimation;

	public MusselModel(ModelPart root) {
		super(root);
		this.moveAnimation = MoreShellfishAnimations.MUSSEL_MOVE.createAnimation(root);
		this.idleAnimation = MoreShellfishAnimations.MUSSEL_IDLE.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mussel = modelPartData.addChild("mussel", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, 24.0F, 0.0F));

		mussel.addChild("topshell", ModelPartBuilder.create().uv(0, 7).cuboid(-2.25F, -2.0F, -3.5F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.75F, 0.0F, 0.0F));

		mussel.addChild("bottomshell", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.0F, -3.5F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		mussel.addChild("innards", ModelPartBuilder.create().uv(0, 14).cuboid(-1.5F, -1.5F, -2.5F, 3.0F, 1.0F, 4.99F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -0.25F, 0.0F));

		mussel.addChild("bodyend", ModelPartBuilder.create().uv(14, 0).cuboid(-1.5F, -1.5F, 2.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ShellfishRenderState<MusselEntity.Variant> entity) {
        super.setAngles(entity);
		this.moveAnimation.apply(entity.moveAnimationState, entity.age, 1f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.age, 1f);
	}
}
