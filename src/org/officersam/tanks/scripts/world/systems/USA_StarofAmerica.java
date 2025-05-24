package org.officersam.tanks.scripts.world.systems;

import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;

import static org.officersam.tanks.scripts.world.systems.ot_addmarket.addMarketplace;

public class USA_StarofAmerica {
    final float asteroids1Dist = 2750f;
    final float marksDist = 1500f;
    final float abadonDist = 900f;
    final float stable1Dist = 4200f;
    final float usnsfDist = 5000f;
    final float asteroidBelt1Dist = 2000f;
    final float vengusDist = 600f;
    final float amerierraDist = 750f;

    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Star of America");
        system.getLocation().set(-5050,-5050);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");
        //star
        PlanetAPI americaStar = system.initStar("Star of America", "star_yellow", 600f, 700, 10, 0.5f, 3f);
        system.setLightColor(new Color(255, 245, 225));

        //asteroid field
        SectorEntityToken americaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(200f, 300f, 8, 16, 4f, 16f, "Asteroids Field"));
        americaAF1.setCircularOrbit(americaStar, 150, asteroids1Dist, 200);

        SectorEntityToken stableLoc1 = system.addCustomEntity("america_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid belt1 ring
        system.addAsteroidBelt(americaStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        // Marks Planet
        PlanetAPI marks = system.addPlanet("marks",americaStar,"Marks","frozen",360 * (float) Math.random(),190f,marksDist,3421f);
        marks.setCustomDescriptionId("usa_starofamerica_marks"); //reference descriptions.csv
        marks.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        marks.getMarket().addCondition(Conditions.VERY_COLD);
        marks.getMarket().addCondition(Conditions.DECIVILIZED);
        marks.getMarket().addCondition(Conditions.DARK);
        marks.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        marks.getMarket().addCondition(Conditions.RARE_ORE_MODERATE);

        // Abadon
        PlanetAPI abadon = system.addPlanet("abadon",americaStar,"Abadon","water",360f * (float) Math.random(),320f,abadonDist,1200f);
        abadon.setCustomDescriptionId("usa_starofamerica_abadon"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(abadon, StarAge.AVERAGE);

        // Vengus
        PlanetAPI vengus = system.addPlanet("vengus",americaStar,"Vengus","desert",360f * (float) Math.random(),120f,vengusDist,200f);
        vengus.setCustomDescriptionId("usa_starofamerica_vengus"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(vengus, StarAge.AVERAGE);

        // Amerierra
        PlanetAPI amerierra;
        amerierra = system.addPlanet("amerierra",
                americaStar,
                "Planet of America",
                "terran",
                360f * (float) Math.random(),
                220f,
                amerierraDist,
                350f);

        amerierra.setCustomDescriptionId("usa_starofamerica_planetofamerica"); //reference descriptions.csv

        MarketAPI amerierra_market = addMarketplace("USA", amerierra, null,
                "Planet of America",
                10,
                Arrays.asList(
                        Conditions.POPULATION_10,
                        Conditions.ORE_RICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.HABITABLE,
                        Conditions.ORGANIZED_CRIME,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.MINING,
                        Industries.STARFORTRESS,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.18f,
                true,
                true);

        //amerierra_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        amerierra_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.STARFORTRESS).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        amerierra_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);

        // US Navy Star Fortress
        SectorEntityToken USNSF = system.addCustomEntity("america_navy_star", "US Navy Star Fortress", "station_hightech2", "USA");
        USNSF.setCircularOrbitPointingDown(americaStar, 0, usnsfDist, 4000f);
        USNSF.setCustomDescriptionId("usa_america_usnsf");
        MarketAPI usnsf_market = addMarketplace("usa", USNSF, null,
                "US Navy Star Fortress",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.OUTPOST,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.ORBITALWORKS,
                        Industries.WAYSTATION
                ),
                0.18f,
                false,
                false);

        usnsf_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);
        usnsf_market.getIndustry(Industries.BATTLESTATION_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        usnsf_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.BETA_CORE);

    }
}