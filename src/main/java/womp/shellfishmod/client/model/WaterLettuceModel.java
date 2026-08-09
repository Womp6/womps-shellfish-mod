package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import womp.shellfishmod.client.states.WaterLettuceBlockEntityRenderState;

public class WaterLettuceModel {
	
    private final ModelPart lettuce;

	public WaterLettuceModel(ModelPart root) {
		this.lettuce = root.getChild("lettuce");
	}
    public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lettuce = modelPartData.addChild("lettuce", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -1.25F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.25F, 0.0F));

		lettuce.addChild("topsmall4_r1", ModelPartBuilder.create().uv(6, 20).cuboid(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 16).cuboid(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

		lettuce.addChild("topsmall3_r1", ModelPartBuilder.create().uv(10, 20).cuboid(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 16).cuboid(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

		lettuce.addChild("topsmall2_r1", ModelPartBuilder.create().uv(14, 20).cuboid(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(17, 0).cuboid(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

		lettuce.addChild("topsmall1_r1", ModelPartBuilder.create().uv(18, 20).cuboid(-1.0F, -0.875F, 0.625F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(17, 3).cuboid(-1.5F, -0.875F, 1.625F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		lettuce.addChild("bottomsmall8_r1", ModelPartBuilder.create().uv(18, 6).cuboid(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 2.3562F, 0.0F));

		lettuce.addChild("bottomsmall7_r1", ModelPartBuilder.create().uv(18, 8).cuboid(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 4).cuboid(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, -2.3562F, 0.0F));

		lettuce.addChild("bottomsmall6_r1", ModelPartBuilder.create().uv(18, 10).cuboid(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, -0.7854F, 0.0F));

		lettuce.addChild("bottomsmall5_r1", ModelPartBuilder.create().uv(18, 12).cuboid(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F))
		.uv(8, 0).cuboid(-2.0F, -0.5F, 2.25F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.7854F, 0.0F));

		lettuce.addChild("bottomsmall4_r1", ModelPartBuilder.create().uv(11, 19).cuboid(-1.5F, -0.25F, 1.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 4).cuboid(-2.0F, -0.25F, 2.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		lettuce.addChild("bottomsmall3_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-1.5F, -0.25F, -2.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-2.0F, -0.25F, -6.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		lettuce.addChild("bottomsmall2_r1", ModelPartBuilder.create().uv(18, 14).cuboid(-2.0F, -0.25F, -1.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 12).cuboid(-6.0F, -0.25F, -2.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		lettuce.addChild("bottomsmall1_r1", ModelPartBuilder.create().uv(18, 17).cuboid(1.0F, -0.25F, -1.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
		.uv(8, 12).cuboid(2.0F, -0.25F, -2.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
		return TexturedModelData.of(modelData, 32, 32);
	}

    public void render(WaterLettuceBlockEntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        queue.submitModelPart(lettuce, matrices, RenderLayer.getEntityCutoutNoCull(renderState.texture), renderState.lightmapCoordinates, OverlayTexture.DEFAULT_UV, null, -1, renderState.crumblingOverlay);
    }

    public RenderLayer getLayer() {
       return RenderLayer.getCutout();
    }
}
