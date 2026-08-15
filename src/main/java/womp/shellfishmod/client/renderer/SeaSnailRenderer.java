package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.snail.*;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;

import java.util.HashMap;

public class SeaSnailRenderer extends MobRenderer<SeaSnailEntity, ShellfishRenderState<SeaSnailEntity.Variant>,  SeaSnailModel> {

    private final HashMap<Integer, SeaSnailModel> modelMap;

    public SeaSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaSnailModelV1(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL1)), 0.25f);
        modelMap = createModelMap(context);
    }

    private HashMap<Integer, SeaSnailModel> createModelMap(EntityRendererProvider.Context context) {
        HashMap<Integer, SeaSnailModel> map = new HashMap<>();
        map.put(0, new SeaSnailModelV1(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL1)));
        map.put(1, new SeaSnailModelV2(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL2)));
        map.put(2, new SeaSnailModelV3(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL3)));
        map.put(3, new SeaSnailModelV4(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL4)));
        map.put(4, new SeaSnailModelV5(context.bakeLayer(ShellfishClient.SEA_SNAIL_MODEL5)));
        return map;
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<SeaSnailEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/snail/snail_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<SeaSnailEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector queue, CameraRenderState camera) {
        int variant = entity.variant.getIndex();
        if (variant == 0) entity.shellfish.scale(poseStack, 0.7f, 0.35f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.8f, 1.0f, 0.5f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.3f, 0.9f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        if (entity.variant != null) this.model = modelMap.get(variant);
        super.submit(entity, poseStack, queue, camera);
    }

    @Override
    public ShellfishRenderState<SeaSnailEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(SeaSnailEntity snail, ShellfishRenderState<SeaSnailEntity.Variant> snailState, float f) {
        super.extractRenderState(snail, snailState, f);
        snailState.variant = snail.getVariant();
        snailState.shellfish = snail;
        snailState.idleAnimationState.copyFrom(snail.idleAnimationState);
        snailState.moveAnimationState.copyFrom(snail.moveAnimationState);
    }
}
