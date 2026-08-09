package womp.shellfishmod.client.model.snail;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public abstract class SeaSnailModel extends EntityModel<ShellfishRenderState<SeaSnailEntity.Variant>> {

    public final ModelPart sea_snail;
	protected final Animation moveAnimation;
	protected final Animation idleAnimation;

    protected SeaSnailModel(ModelPart root) {
        super(root);
        this.sea_snail = root.getChild("sea_snail");
		this.moveAnimation = ShellfishAnimations.SNAIL_MOVE.createAnimation(root);
		this.idleAnimation = ShellfishAnimations.SNAIL_HIDE.createAnimation(root);
    }
    
    @Override
	public void setAngles(ShellfishRenderState<SeaSnailEntity.Variant> entity) {
		super.setAngles(entity);
		this.moveAnimation.apply(entity.moveAnimationState, entity.age, 1f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.age, 1f);
	}
}
