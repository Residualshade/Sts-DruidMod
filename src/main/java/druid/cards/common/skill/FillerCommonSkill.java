package druid.cards.common.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import druid.patches.AbstractCardEnum;

public class FillerCommonSkill extends CustomCard {
    public static final String ID = "druid:FillerCommonSkill";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK_BONUS = 4;

    public FillerCommonSkill() {
        super(
                ID
                , NAME
                , null
                , COST
                , DESCRIPTION
                , CardType.SKILL
                , AbstractCardEnum.DRUID_MAROON
                , CardRarity.COMMON
                , CardTarget.SELF
        );

        this.baseBlock = this.block = BLOCK;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_BONUS);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(abstractPlayer,abstractPlayer,this.block));
    }
}
