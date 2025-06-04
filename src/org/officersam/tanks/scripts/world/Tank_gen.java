package org.officersam.tanks.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import org.officersam.tanks.scripts.world.systems.GER_SonneDeutschlands;
import org.officersam.tanks.scripts.world.systems.USA_StarofAmerica;
import org.officersam.tanks.scripts.world.systems.SOV_ZvezdaRodiny;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public class Tank_gen implements SectorGeneratorPlugin {

    @Override
    public void generate(SectorAPI sector) {
	
        new USA_StarofAmerica().generate(sector);
        new GER_SonneDeutschlands().generate(sector);
        new SOV_ZvezdaRodiny().generate(sector);
		
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("USA");
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("GER");
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("SOV");

        FactionAPI usa = sector.getFaction("USA");
        FactionAPI ger = sector.getFaction("GER");
        FactionAPI sov = sector.getFaction("SOV");

        FactionAPI player = sector.getFaction(Factions.PLAYER);
        FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
        FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
        FactionAPI pirates = sector.getFaction(Factions.PIRATES);
        FactionAPI independent = sector.getFaction(Factions.INDEPENDENT); 
        FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
        FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);   
        FactionAPI kol = sector.getFaction(Factions.KOL);	
        FactionAPI diktat = sector.getFaction(Factions.DIKTAT); 
		FactionAPI persean = sector.getFaction(Factions.PERSEAN);
        FactionAPI guard = sector.getFaction(Factions.LIONS_GUARD);

        usa.setRelationship(ger.getId(), -0.3f);
        usa.setRelationship(sov.getId(), 0.0f);

        ger.setRelationship(usa.getId(), -0.3f);
        ger.setRelationship(sov.getId(), -0.4f);

        sov.setRelationship(usa.getId(), 0.0f);
        sov.setRelationship(ger.getId(), -0.3f);

        usa.setRelationship(player.getId(), 0.1f);
        usa.setRelationship(hegemony.getId(), 0.0f);
        usa.setRelationship(tritachyon.getId(), 0.2f);
        usa.setRelationship(pirates.getId(), -1.0f);
        usa.setRelationship(independent.getId(), 0.6f);
        usa.setRelationship(persean.getId(), 0.2f);
        usa.setRelationship(church.getId(), -0.3f);
        usa.setRelationship(path.getId(), -1.0f);
        usa.setRelationship(kol.getId(), 0.1f);
        usa.setRelationship(diktat.getId(), 0.1f);
        usa.setRelationship(guard.getId(), -0.2f);

        ger.setRelationship(player.getId(), 0.0f);
        ger.setRelationship(hegemony.getId(), -0.4f);
        ger.setRelationship(tritachyon.getId(), 0.0f);
        ger.setRelationship(pirates.getId(), -1.0f);
        ger.setRelationship(independent.getId(), 0.0f);
        ger.setRelationship(persean.getId(), 0.0f);
        ger.setRelationship(church.getId(), -0.3f);
        ger.setRelationship(path.getId(), -1.0f);
        ger.setRelationship(kol.getId(), -0.2f);
        ger.setRelationship(diktat.getId(), -0.3f);
        ger.setRelationship(guard.getId(), -0.4f);

        sov.setRelationship(player.getId(), 0.1f);
        sov.setRelationship(hegemony.getId(), 0.1f);
        sov.setRelationship(tritachyon.getId(), 0.0f);
        sov.setRelationship(pirates.getId(), -1.0f);
        sov.setRelationship(independent.getId(), 0.6f);
        sov.setRelationship(persean.getId(), 0.2f);
        sov.setRelationship(church.getId(), -0.3f);
        sov.setRelationship(path.getId(), -1.0f);
        sov.setRelationship(kol.getId(), 0.1f);
        sov.setRelationship(diktat.getId(), 0.1f);
        sov.setRelationship(guard.getId(), -0.2f);

    }
}
