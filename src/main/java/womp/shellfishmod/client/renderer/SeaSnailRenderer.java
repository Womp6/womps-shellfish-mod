package womp.shellfishmod.client.renderer;

import java.util.HashMap;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.SeaSnailEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.snail.*;

public class SeaSnailRenderer extends MobEntityRenderer<SeaSnailEntity, ShellfishRenderState<SeaSnailEntity.Variant>, SeaSnailModel> {

    private final HashMap<Integer, SeaSnailModel> modelMap;

    public SeaSnailRenderer(Context context) {
        super(context, new SeaSnailModelV1(context.getPart(ShellfishClient.SEA_SNAIL_MODEL1)), 0.25f);
        modelMap = createModelMap(context);
    }

    private HashMap<Integer, SeaSnailModel> createModelMap(Context context) {
        HashMap<Integer, SeaSnailModel> map = new HashMap<>();
        map.put(0, new SeaSnailModelV1(context.getPart(ShellfishClient.SEA_SNAIL_MODEL1)));
        map.put(1, new SeaSnailModelV2(context.getPart(ShellfishClient.SEA_SNAIL_MODEL2)));
        map.put(2, new SeaSnailModelV3(context.getPart(ShellfishClient.SEA_SNAIL_MODEL3)));
        map.put(3, new SeaSnailModelV4(context.getPart(ShellfishClient.SEA_SNAIL_MODEL4)));
        map.put(4, new SeaSnailModelV5(context.getPart(ShellfishClient.SEA_SNAIL_MODEL5)));
        return map;
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<SeaSnailEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<SeaSnailEntity.Variant> entity, MatrixStack poseStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 0.7f, 0.35f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.8f, 1.0f, 0.5f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.3f, 0.9f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);
        
        if (entity.variant != null) this.model = modelMap.get(variant);
        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(SeaSnailEntity snail, ShellfishRenderState<Variant> snailState, float f) {
        super.updateRenderState(snail, snailState, f);
        snailState.variant = snail.getVariant();
        snailState.shellfish = snail;
        snailState.idleAnimationState.copyFrom(snail.idleAnimationState);
        snailState.moveAnimationState.copyFrom(snail.moveAnimationState);
    }
}
