package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class SeaSnailModel extends EntityModel<ShellfishRenderState<SeaSnailEntity.Variant>> {

    public final ModelPart sea_snail;
	private final ModelPart shell;
	public final ModelPart shell1;
	public final ModelPart shell2;
	public final ModelPart shell3;
	public final ModelPart shell4;
	public final ModelPart shell5;

	public SeaSnailModel(ModelPart root) {
		super(root);
		this.sea_snail = root.getChild("sea_snail");
		this.shell = sea_snail.getChild("shell");
		this.shell1 = shell.getChild("shell1");
		this.shell2 = shell.getChild("shell2");
		this.shell3 = shell.getChild("shell3");
		this.shell4 = shell.getChild("shell4");
		this.shell5 = shell.getChild("shell5");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sea_snail = modelPartData.addChild("sea_snail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = sea_snail.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body.addChild("body1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body.addChild("body2", ModelPartBuilder.create().uv(11, 0).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyes = sea_snail.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		eyes.addChild("eye2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3478F, 0.0298F, -0.082F));

		eyes.addChild("eye1_r1", ModelPartBuilder.create().uv(2, 0).cuboid(0.55F, -5.25F, -3.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3478F, -0.0298F, 0.082F));

		ModelPartData shell = sea_snail.addChild("shell", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData shell1 = shell.addChild("shell1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		shell1.addChild("shell1_r1", ModelPartBuilder.create().uv(0, 9).cuboid(-1.5F, -6.0F, -1.5F, 3.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData shell2 = shell.addChild("shell2", ModelPartBuilder.create().uv(22, 27).cuboid(-1.5F, -4.75F, 3.5F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(11, 27).cuboid(-1.0F, -4.25F, 5.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		shell2.addChild("shell_r1", ModelPartBuilder.create().uv(11, 18).cuboid(-2.0F, -5.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(24, 18).cuboid(-1.5F, -4.1F, -3.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 1.25F, -0.0873F, 0.0F, 0.0F));

		ModelPartData shell3 = shell.addChild("shell3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		shell3.addChild("shell6_r1", ModelPartBuilder.create().uv(7, 22).cuboid(-0.5F, -3.5F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(7, 13).cuboid(-1.0F, -4.0F, 4.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		shell3.addChild("shell4_r1", ModelPartBuilder.create().uv(20, 22).cuboid(-1.5F, -4.75F, 3.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(7, 16).cuboid(-0.5F, -4.25F, -4.75F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(23, 28).cuboid(-1.0F, -4.75F, -2.75F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 13).cuboid(-1.5F, -5.0F, -1.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(7, 22).cuboid(-2.0F, -4.0F, -1.5F, 4.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData shell4 = shell.addChild("shell4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		shell4.addChild("shell3_r1", ModelPartBuilder.create().uv(0, 28).cuboid(-2.75F, -3.75F, 0.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 10).cuboid(-2.25F, -4.5F, 0.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(16, 22).cuboid(-1.5F, -6.0F, -1.5F, 3.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData shell5 = shell.addChild("shell5", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		shell5.addChild("shell5_r1", ModelPartBuilder.create().uv(26, 18).cuboid(-0.5F, -3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(26, 15).cuboid(-1.0F, -3.5F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		shell5.addChild("shell8_r1", ModelPartBuilder.create().uv(0, 27).cuboid(-1.0F, -4.5F, -1.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(16, 24).cuboid(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 32, 32);
	}
    
	@Override
	public void setAngles(ShellfishRenderState<SeaSnailEntity.Variant> entity) {
		super.setAngles(entity);
		this.animate(entity.moveAnimationState, ShellfishAnimations.SNAIL_MOVE, entity.age, 1f);
		this.animate(entity.idleAnimationState, ShellfishAnimations.SNAIL_HIDE, entity.age, 1f);
	}
}
