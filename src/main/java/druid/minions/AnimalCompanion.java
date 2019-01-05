package druid.minions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import druid.Resources;
import druid.powers.MarkPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AnimalCompanion extends AbstractFriendlyMonster {

    public static final Logger logger = LogManager.getLogger(AnimalCompanion.class.getName());

    public static String NAME = "Animal Companion";
    public static String ID = "druid:AnimalCompanion";

    public static final int HP_MIN = 8;
    public static final int HP_MAX = 12;
    public static final int A_2_HP_MIN = 9;
    public static final int A_2_HP_MAX = 13;
    public static final int TACKLE_DAMAGE = 3;
    public static final int WEAK_TURNS = 1;
    public static final int A_2_TACKLE_DAMAGE = 4;
    private static final byte TACKLE = 1;
    private static final byte DEBUFF = 2;
    
    public AnimalCompanion(int offsetX, int offsetY) {
        super(NAME, ID, 50, -8.0F, 10.0F, 230.0F, 240.0F, "images/monsters/theBottom/slimeS/skeleton.png", -850F, 300);
        loadAnimation("images/monsters/theBottom/slimeS/skeleton.atlas", "images/monsters/theBottom/slimeS/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
        addMoves();
    }

    public void takeTurn()
    {
        logger.info("Animal Companion Taking Turn. nextMove: " + this.nextMove);
        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m.hasPower(MarkPower.POWER_ID)) {
                logger.info("found mark: " + m.name);
                abstractMonster = m;
            }
        }
        DamageInfo damageInfo = this.damage.get(0);
        damageInfo.applyPowers(this,abstractMonster);
        switch (this.nextMove)
        {
            case 1:

                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(abstractMonster,
                        damageInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

                setMove((byte)2, Intent.ATTACK_DEBUFF);
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(abstractMonster,
                        damageInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractMonster, AbstractDungeon.player, new WeakPower(abstractMonster, 1, true), 1));

                setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        }
    }

    protected void getMove(int num)
    {
        logger.info("Animal Companion Getting Move Turn.");
        if (AbstractDungeon.aiRng.randomBoolean()) {
            setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        } else {
            setMove((byte)2, Intent.ATTACK_DEBUFF);
        }
    }

    private void addMoves() {
        MOVES = new String[]{};
        DIALOG = new String[] {""};
        this.damage.add(new DamageInfo(this,6));
        this.moves.addMove(
            new MinionMove(
            "Sucide"
                , this
                , new Texture(Resources.ENERGY_ORB_1024_PATH)
                , "Kill Companion"
                , () -> AbstractDungeon.actionManager.addToBottom(new DamageAction(this, new DamageInfo(this,currentHealth)))
            )
        );
    }
}
