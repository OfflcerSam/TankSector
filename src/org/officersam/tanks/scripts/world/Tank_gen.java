package org.officersam.tanks.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import org.officersam.tanks.scripts.world.systems.USA_StarofAmerica;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public class Tank_gen implements SectorGeneratorPlugin {

    @Override
    public void generate(SectorAPI sector) {
	
        new USA_StarofAmerica().generate(sector);
		
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("USA");

        FactionAPI usa = sector.getFaction("USA");
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

        usa.setRelationship(player.getId(), 0.3f);	
        usa.setRelationship(hegemony.getId(), -0.2f);
        usa.setRelationship(tritachyon.getId(), 0.2f);
        usa.setRelationship(pirates.getId(), -0.9f);
        usa.setRelationship(independent.getId(), 0.6f);
        usa.setRelationship(persean.getId(), 0.15f);
        usa.setRelationship(church.getId(), -0.3f);
        usa.setRelationship(path.getId(), -0.9f);
        usa.setRelationship(kol.getId(), 0.1f);
        usa.setRelationship(diktat.getId(), -0.15f);
        usa.setRelationship(guard.getId(), -0.25f);   
	
    }
}
