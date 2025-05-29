package org.officersam.tanks.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.procgen.themes.DerelictThemeGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.procgen.DefenderDataOverride;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;

import static org.officersam.tanks.scripts.world.systems.ot_addmarket.addMarketplace;

public class USA_StarofAmerica {
    // Star Orbits
    //asteroids
    final float asteroids1Dist = 7200f;
    final float stable1Dist = 4000f;
    final float asteroidBelt1Dist = 2050f;
    final float asteroidBelt2Dist = 9200f;
    //relays
    final float buoy1Dist = 4550f;
    final float relay1Dist = 5650f;
    final float sensor1Dist = 9350f;
    //planets
    final float usnsfDist = 9200f;
    final float vengusDist = 2900f;
    final float abadonDist = 4200f;
    final float amerierraDist = 6800f;
    final float martiniDist = 5000f;
    final float marksDist = 9800f;
    //jumps
    final float jumpInnerDist = 3050f;
    final float jumpOuterDist = 9300;
    final float jumpFringeDist = 16700f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Star of America");
        system.getLocation().set(-5500, -6000);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");

        //praise the sun
        PlanetAPI americaStar = system.initStar("StarofAmerica", "star_yellow", 800f, 900, 10, 0.5f, 3f);
        system.setLightColor(new Color(255, 245, 225));

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //asteroid field
        SectorEntityToken americaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(250f, 350f, 10, 24, 4f, 16f, "Asteroids Field"));
        americaAF1.setCircularOrbit(americaStar, 150, asteroids1Dist, 220);

        SectorEntityToken stableLoc1 = system.addCustomEntity("america_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 220f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 210f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "lasher_PD", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid belt1 ring
        system.addAsteroidBelt(americaStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        //asteroid belt2 ring
        system.addAsteroidBelt(americaStar, 1000, asteroidBelt2Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Outer Band");
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt2Dist - 200, 250f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist + 200, 400f);

        // Relays
        SectorEntityToken americaStar_relay = system.addCustomEntity("americaStar_relay", // unique id
                "American Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_relay.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520);

        SectorEntityToken americaStar_buoy = system.addCustomEntity("americaStar_buoy", // unique id
                "American Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_buoy.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520);

        SectorEntityToken americaStar_sensor = system.addCustomEntity("americaStar_sensor", // unique id
                "American Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_sensor.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520);

        // Marks Planet
        PlanetAPI marks = system.addPlanet("u_marks", americaStar, "Marks", "frozen", 360 * (float) Math.random(), 190f, marksDist, 390f);
        marks.setCustomDescriptionId("usa_starofamerica_marks"); //reference descriptions.csv
        marks.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        marks.getMarket().addCondition(Conditions.VERY_COLD);
        marks.getMarket().addCondition(Conditions.DECIVILIZED);
        marks.getMarket().addCondition(Conditions.DARK);
        marks.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        marks.getMarket().addCondition(Conditions.RARE_ORE_MODERATE);

        addDerelict(system, marks, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.BATTERED, 280f, (Math.random() < 0.6));
        addDerelict(system, marks, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 230f, (Math.random() < 0.6));
        addDerelict(system, marks, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, marks, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("marks_scrap4");
        scrap1.setCircularOrbit(marks, 105, 195, 150);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 0, 0, 0));
        scrap1.setDiscoverable(Boolean.TRUE);

        // Abadon
        PlanetAPI abadon = system.addPlanet("u_abadon", americaStar, "Abadon", "arid", 360f * (float) Math.random(), 320f, abadonDist, 380f);
        abadon.setCustomDescriptionId("usa_starofamerica_abadon"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(abadon, StarAge.AVERAGE);

        // Vengus
        PlanetAPI vengus = system.addPlanet("u_vengus", americaStar, "Vengus", "desert", 360f * (float) Math.random(), 120f, vengusDist, 200f);
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
                365f);

        amerierra.setCustomDescriptionId("usa_starofamerica_planetofamerica"); //reference descriptions.csv

        MarketAPI amerierra_market = addMarketplace("USA", amerierra, null,
                "Planet of America",
                10,
                Arrays.asList(
                        Conditions.POPULATION_10,
                        Conditions.URBANIZED_POLITY,
                        Conditions.ORE_ABUNDANT,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.HABITABLE,
                        Conditions.ORGANIZED_CRIME,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.VOLATILES_ABUNDANT,
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
                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HEAVYINDUSTRY,
                        Industries.LIGHTINDUSTRY,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.17f,
                false,
                false);

        //amerierra_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        amerierra_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        //amerierra_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        amerierra_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        amerierra_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);

        // Martini
        PlanetAPI martini;
        martini = system.addPlanet("u_martini",
                americaStar,
                "Martini Isle",
                "water",
                360f * (float) Math.random(),
                220f,
                martiniDist,
                360f);

        martini.setCustomDescriptionId("usa_starofamerica_martini"); //reference descriptions.csv

        MarketAPI martini_market = addMarketplace("USA", martini, null,
                "Martini Isle",
                8,
                Arrays.asList(
                        Conditions.POPULATION_8,
                        Conditions.ORE_RICH,
                        Conditions.WATER,
                        Conditions.INDUSTRIAL_POLITY,
                        Conditions.RARE_ORE_RICH,
                        Conditions.VOLATILES_PLENTIFUL,
                        Conditions.VOLTURNIAN_LOBSTER_PENS,
                        Conditions.HABITABLE,
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
                        Industries.REFINING,
                        Industries.HEAVYINDUSTRY,
                        Industries.AQUACULTURE,
                        Industries.FUELPROD,
                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.18f,
                false,
                true);


        martini_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.ALPHA_CORE);
        martini_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        martini_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);

        // US Navy Star Fortress
        SectorEntityToken USNSF = system.addCustomEntity("america_navy_star", "US Navy Star Fortress", "station_hightech2", "USA");
        USNSF.setCircularOrbitPointingDown(americaStar, 0, usnsfDist, 410f);
        USNSF.setCustomDescriptionId("usa_america_usnsf");
        MarketAPI usnsf_market = addMarketplace("USA", USNSF, null,
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
                0.20f,
                false,
                true);

        usnsf_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);
        usnsf_market.getIndustry(Industries.BATTLESTATION_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        usnsf_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.BETA_CORE);

        addDerelict(system, USNSF, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));
        addDerelict(system, USNSF, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));
        addDerelict(system, USNSF, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));


    }

    private void addDerelict(StarSystemAPI system, SectorEntityToken focus, String variantId, ShipRecoverySpecial.ShipCondition condition, float orbitRadius, boolean recoverable) {

        DerelictShipEntityPlugin.DerelictShipData params = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(variantId, condition), true);
        SectorEntityToken ship = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, params);
        ship.setDiscoverable(true);

        float orbitDays = 60f;
        ship.setCircularOrbit(focus, (float) MathUtils.getRandomNumberInRange(-2, 2) + 90f, orbitRadius, orbitDays);

        if (recoverable) {
            SalvageSpecialAssigner.ShipRecoverySpecialCreator creator = new SalvageSpecialAssigner.ShipRecoverySpecialCreator(null, 0, 0, false, null, null);
            Misc.setSalvageSpecial(ship, creator.createSpecial(ship, null));
        }
    }

}