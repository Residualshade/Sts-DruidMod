package druid.cards.common.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import druid.patches.AbstractCardEnum;

public class FillerCommonPower extends CustomCard {
    public static final String ID = "druid:FillerCommonPower";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    public FillerCommonPower() {
        super(
                ID
                , NAME
                , null
                , COST
                , DESCRIPTION
                , CardType.POWER
                , AbstractCardEnum.DRUID_MAROON
                , CardRarity.COMMON
                , CardTarget.SELF
        );
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }
}
