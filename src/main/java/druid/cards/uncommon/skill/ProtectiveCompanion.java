package druid.cards.uncommon.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import druid.minions.AnimalCompanion;
import druid.patches.AbstractCardEnum;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.enums.MonsterIntentEnum;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class ProtectiveCompanion extends CustomCard {

    public static final Logger logger = LogManager.getLogger(ProtectiveCompanion.class.getName());

    public static final String ID = "druid:ProtectiveCompanion";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int COMPANION_BLOCK = 6;
    private static final int UPGRADE_COMPANION_BLOCK_BONUS = 3;

    //TODO: CARD IMAGE
    public ProtectiveCompanion() {
        super(
                ID
                ,NAME
                ,null
                ,COST
                ,DESCRIPTION
                , AbstractCard.CardType.SKILL
                , AbstractCardEnum.DRUID_MAROON
                , CardRarity.BASIC
                , AbstractCard.CardTarget.ENEMY
        );

        this.baseMagicNumber = this.magicNumber = COMPANION_BLOCK;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeMagicNumber(UPGRADE_COMPANION_BLOCK_BONUS);
        }

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions abstractPlayerWithMinions = (AbstractPlayerWithMinions)abstractPlayer;
            AbstractFriendlyMonster abstractFriendlyMonster = (AbstractFriendlyMonster)abstractPlayerWithMinions.minions.getMonster(AnimalCompanion.ID);
            if(abstractFriendlyMonster != null) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(abstractFriendlyMonster,abstractPlayer,this.magicNumber));
                try {
                    Field moveInfo = AbstractMonster.class.getDeclaredField("move");
                    moveInfo.setAccessible(true);

                    EnemyMoveInfo enemyMoveInfo = (EnemyMoveInfo)moveInfo.get(abstractMonster);

                    switch (enemyMoveInfo.intent) {
                        case ATTACK:
                            enemyMoveInfo.intent = MonsterIntentEnum.ATTACK_MINION;
                            break;
                        case ATTACK_BUFF:
                            enemyMoveInfo.intent = MonsterIntentEnum.ATTACK_MINION_BUFF;
                            break;
                        case ATTACK_DEBUFF:
                            enemyMoveInfo.intent = MonsterIntentEnum.ATTACK_MINION_DEBUFF;
                            break;
                        case ATTACK_DEFEND:
                            enemyMoveInfo.intent = MonsterIntentEnum.ATTACK_MINION_DEFEND;
                            break;
                    }

                    moveInfo.set(abstractMonster, enemyMoveInfo);
                    abstractMonster.createIntent();
                    logger.info("Successfully changed intent.");
                } catch (Exception exception) {
                    logger.info("Protective Companion Intent Manipulation Exploded");
                }

            }
        }


    }
}
