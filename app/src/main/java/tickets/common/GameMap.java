
package tickets.common;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class GameMap {
	private Map<String, List<Route>> cityGraph;

	public GameMap() {
		cityGraph = makeMap();
		
	}

	public boolean claimRoute(String srcCity, String destCity, RouteColors color, PlayerColor player) {
		List<Route> destinations = cityGraph.get(srcCity);
		for (Route r : destinations) {
			if (r.equals(srcCity, destCity)) {
				return r.claim(color, player);
			}
		}
		return false;
	}


    public List<Route> getClaimedRoutes() {
        List<Route> claimedRoutes = new ArrayList<>();
        for (List<Route> routes: cityGraph.values()) {
            for (Route route : routes) {
                if (route.isOwned())
                    claimedRoutes.add(route);
            }
        }
        return claimedRoutes;
    }

	private Map<String, List<Route>> makeMap() {
		Map<String, List<Route>> map = new HashMap<>();

		// Vancouver
		Route van_cal = new Route(Cities.VANCOUVER, Cities.CALGARY, 
															RouteColors.Gray, 3);
		Route van_sea = new Route(Cities.VANCOUVER, Cities.SEATTLE, 
															RouteColors.Gray, RouteColors.Gray, 1);
		Route[] vanRoutes = { van_cal, van_sea };
		map.put(Cities.VANCOUVER, Arrays.asList(vanRoutes));

		// Seattle
		// sea_van
		Route sea_cal = new Route(Cities.SEATTLE, Cities.CALGARY,
															RouteColors.Gray, 4);
		Route sea_hel = new Route(Cities.SEATTLE, Cities.HELENA,
															RouteColors.Yellow, 6);
		Route sea_port = new Route(Cities.SEATTLE, Cities.PORTLAND,
															RouteColors.Gray, RouteColors.Gray, 1);
		Route[] seaRoutes = { van_sea, sea_cal, sea_hel, sea_port };
		map.put(Cities.SEATTLE, Arrays.asList(seaRoutes));

		// Portland
		// port_sea
		Route port_fran = new Route(Cities.PORTLAND, Cities.SAN_FRANCISCO,
															RouteColors.Green, RouteColors.Purple, 5);
		Route port_slc = new Route(Cities.PORTLAND, Cities.SALT_LAKE_CITY,
															RouteColors.Blue, 5);
		Route[] portRoutes = { sea_port, port_fran, port_slc };
		map.put(Cities.PORTLAND, Arrays.asList(portRoutes));

		// San Francisco
		// fran_port
		Route fran_slc = new Route(Cities.SAN_FRANCISCO, Cities.SALT_LAKE_CITY,
															RouteColors.Orange, RouteColors.White, 5);
		Route fran_la = new Route(Cities.SAN_FRANCISCO, Cities.LOS_ANGELES,
															RouteColors.Purple, RouteColors.Yellow, 3);
		Route[] franRoutes = { port_fran, fran_slc, fran_la };
		map.put(Cities.SAN_FRANCISCO, Arrays.asList(franRoutes));

		// Los Angeles
		// la_fran
		Route la_vgs = new Route(Cities.LOS_ANGELES, Cities.LAS_VEGAS,
															RouteColors.Gray, 2);
		Route la_phx = new Route(Cities.LOS_ANGELES, Cities.PHOENIX,
															RouteColors.Gray, 3);
		Route la_pso = new Route(Cities.LOS_ANGELES, Cities.EL_PASO,
															RouteColors.Black, 6);
		Route[] laRoutes = { fran_la, la_vgs, la_phx, la_pso };
		map.put(Cities.LOS_ANGELES, Arrays.asList(laRoutes));

		// Las Vegas
		// vgs_la
		Route vgs_slc = new Route(Cities.LAS_VEGAS, Cities.SALT_LAKE_CITY,
															RouteColors.Orange, 3);
		Route[] vgsRoutes = { la_vgs, vgs_slc };
		map.put(Cities.LAS_VEGAS, Arrays.asList(vgsRoutes));

		// Salt Lake City
		// slc_vgs
		// slc_fran
		// slc_port
		Route slc_hel = new Route(Cities.SALT_LAKE_CITY, Cities.HELENA,
															RouteColors.Orange, 6);
		Route slc_dnv = new Route(Cities.SALT_LAKE_CITY, Cities.DENVER,
															RouteColors.Red, RouteColors.Yellow, 3);
		Route[] slcRoutes = { vgs_slc, fran_slc, port_slc, slc_hel, slc_dnv };
		map.put(Cities.SALT_LAKE_CITY, Arrays.asList(slcRoutes));

		// Helena
		// hel_slc
		// hel_sea
		Route hel_cal = new Route(Cities.HELENA, Cities.CALGARY,
															RouteColors.Gray, 4);
		Route hel_win = new Route(Cities.HELENA, Cities.WINNIPEG,
															RouteColors.Blue, 4);
		Route hel_dul = new Route(Cities.HELENA, Cities.DULUTH,
															RouteColors.Orange, 6);
		Route hel_oma = new Route(Cities.HELENA, Cities.OMAHA,
															RouteColors.Red, 5);
		Route hel_dnv = new Route(Cities.HELENA, Cities.DENVER,
															RouteColors.Green, 4);
		Route[] helRoutes = { hel_cal, hel_win, hel_dul, hel_oma, hel_dnv, slc_hel, sea_hel };
		map.put(Cities.HELENA, Arrays.asList(helRoutes));

		// Calgary
		// cal_van
		// cal_sea
		// cal_hel
		Route cal_win = new Route(Cities.CALGARY, Cities.WINNIPEG,
															RouteColors.White, 6);
		Route[] calRoutes = { van_cal, sea_cal, hel_cal, cal_win };
		map.put(Cities.CALGARY, Arrays.asList(calRoutes));

		// Winnipeg
		// win_hel
		// win_cal
		Route win_dul = new Route(Cities.WINNIPEG, Cities.DULUTH,
															RouteColors.Black, 4);
		Route win_ssm = new Route(Cities.WINNIPEG, Cities.SAULT_ST_MARIE,
															RouteColors.Gray, 6);
		Route[] winRoutes = { win_dul, hel_win, cal_win, win_ssm };
		map.put(Cities.WINNIPEG, Arrays.asList(winRoutes));

		// Duluth
		// dul_hel
		// dul_win
		Route dul_ssm = new Route(Cities.DULUTH, Cities.SAULT_ST_MARIE,
															RouteColors.Gray, 3);
		Route dul_tor = new Route(Cities.DULUTH, Cities.TORONTO,
															RouteColors.Purple, 6);
		Route dul_chic = new Route(Cities.DULUTH, Cities.CHICAGO,
															RouteColors.Red, 3);
		Route dul_oma = new Route(Cities.DULUTH, Cities.OMAHA,
															RouteColors.Gray, RouteColors.Gray, 2);
		Route[] dulRoutes = { dul_ssm, dul_tor, dul_chic, dul_oma, hel_dul, win_dul };
		map.put(Cities.DULUTH, Arrays.asList(dulRoutes));

		// Omaha
		// oma_hel
		// oma_dul
		Route oma_dnv = new Route(Cities.OMAHA, Cities.DENVER,
															RouteColors.Purple, 4);
		Route oma_chic = new Route(Cities.OMAHA, Cities.CHICAGO,
															RouteColors.Blue, 4);
		Route oma_kc = new Route(Cities.OMAHA, Cities.KANSAS_CITY,
															RouteColors.Gray, RouteColors.Gray, 1);
		Route[] omaRoutes = { oma_dnv, hel_oma, dul_oma, oma_chic, oma_kc };
		map.put(Cities.OMAHA, Arrays.asList(omaRoutes));

		// Kansas City
		// kc_oma
		Route kc_dnv = new Route(Cities.KANSAS_CITY, Cities.DENVER,
															RouteColors.Black, RouteColors.Orange, 4);
		Route kc_lou = new Route(Cities.KANSAS_CITY, Cities.SAINT_LOUIS,
															RouteColors.Blue, RouteColors.Purple, 2);
		Route kc_okc = new Route(Cities.KANSAS_CITY, Cities.OKLAHOMA_CITY,
															RouteColors.Gray, RouteColors.Gray, 2);
		Route[] kcRoutes = { oma_kc, kc_dnv, kc_lou, kc_okc };
		map.put(Cities.KANSAS_CITY, Arrays.asList(kcRoutes));

		// Denver
		// dnv_hel
		// dnv_oma
		// dnv_kc
		// dnv_slc
		Route dnv_okc = new Route(Cities.DENVER, Cities.OKLAHOMA_CITY,
															RouteColors.Red, 4);
		Route dnv_sfe = new Route(Cities.DENVER, Cities.SANTA_FE,
															RouteColors.Gray, 2);
		Route dnv_phx = new Route(Cities.DENVER, Cities.PHOENIX,
															RouteColors.White, 5);
		Route[] dnvRoutes = { hel_dnv, oma_dnv, kc_dnv, slc_dnv, dnv_okc, dnv_sfe, dnv_phx };
		map.put(Cities.DENVER, Arrays.asList(dnvRoutes));

		// Phoenix
		// phx_la
		// phx_dnv
		Route phx_sfe = new Route(Cities.PHOENIX, Cities.SANTA_FE,
															RouteColors.Gray, 3);
		Route phx_pso = new Route(Cities.PHOENIX, Cities.EL_PASO,
															RouteColors.Gray, 3);
		Route[] phxRoutes = { la_phx, dnv_phx, phx_sfe, phx_pso };
		map.put(Cities.PHOENIX, Arrays.asList(phxRoutes));

		// Santa Fe
		// sfe_dnv
		// sfe_phx
		Route sfe_okc = new Route(Cities.SANTA_FE, Cities.OKLAHOMA_CITY,
															RouteColors.Blue, 3);
		Route sfe_pso = new Route(Cities.SANTA_FE, Cities.EL_PASO,
															RouteColors.Gray, 2);
		Route[] sfeRoutes = { dnv_sfe, phx_sfe, sfe_okc, sfe_pso };
		map.put(Cities.SANTA_FE, Arrays.asList(sfeRoutes));

		// El Paso
		// pso_la
		// pso_phx
		// pso_sfe
		Route pso_okc = new Route(Cities.EL_PASO, Cities.OKLAHOMA_CITY,
															RouteColors.Yellow, 5);
		Route pso_dls = new Route(Cities.EL_PASO, Cities.DALLAS,
															RouteColors.Red, 4);
		Route pso_hst = new Route(Cities.EL_PASO, Cities.HOUSTON,
															RouteColors.Green, 6);
		Route[] psoRoutes = { la_pso, phx_pso, sfe_pso };
		map.put(Cities.EL_PASO, Arrays.asList(psoRoutes));

		// Houston
		// hst_pso
		Route hst_dls = new Route(Cities.HOUSTON, Cities.DALLAS,
															RouteColors.Gray, RouteColors.Gray, 1);
		Route hst_no = new Route(Cities.HOUSTON, Cities.NEW_ORLEANS,
															RouteColors.Gray, 2);
		Route[] hstRoutes = { pso_hst, hst_dls, hst_no };
		map.put(Cities.HOUSTON, Arrays.asList(hstRoutes));

		// Dallas
		// dls_pso
		// dls_hst
		Route dls_okc = new Route(Cities.DALLAS, Cities.OKLAHOMA_CITY,
															RouteColors.Gray, RouteColors.Gray, 2);
		Route dls_lr = new Route(Cities.DALLAS, Cities.LITTLE_ROCK,
															RouteColors.Gray, 2);
		Route[] dlsRoutes = { pso_dls, hst_dls, dls_okc, dls_lr };
		map.put(Cities.DALLAS, Arrays.asList(dlsRoutes));

		// Oklahoma City
		// okc_pso
		// okc_sfe
		// okc_dnv
		// okc_kc
		// okc_dls
		Route okc_lr = new Route(Cities.OKLAHOMA_CITY, Cities.LITTLE_ROCK,
															RouteColors.Gray, 2);
		Route[] okcRoutes = { pso_okc, sfe_okc, dnv_okc, kc_dnv, dls_okc, okc_lr };
		map.put(Cities.OKLAHOMA_CITY, Arrays.asList(okcRoutes));

		// Little Rock
		// lr_dls
		// lr_okc
		Route lr_lou = new Route(Cities.LITTLE_ROCK, Cities.SAINT_LOUIS,
															RouteColors.Gray, 2);
		Route lr_nsh = new Route(Cities.LITTLE_ROCK, Cities.NASHVILLE,
															RouteColors.White, 3);
		Route lr_no = new Route(Cities.LITTLE_ROCK, Cities.NEW_ORLEANS,
															RouteColors.Green, 3);
		Route[] lrRoutes = { dls_lr, okc_lr, lr_lou, lr_nsh, lr_no };
		map.put(Cities.LITTLE_ROCK, Arrays.asList(lrRoutes));

		// New Orleans
		// no_hst
		// no_lr
		Route no_atl = new Route(Cities.NEW_ORLEANS, Cities.ATLANTA,
															RouteColors.Yellow, RouteColors.Orange, 4);
		Route no_mia = new Route(Cities.NEW_ORLEANS, Cities.MIAMI,
															RouteColors.Red, 6);
		Route[] noRoutes = { hst_no, lr_no, no_atl, no_mia };
		map.put(Cities.NEW_ORLEANS, Arrays.asList(noRoutes));

		// Saint Louis
		// lou_kc
		// lou_lr
		Route lou_chic = new Route(Cities.SAINT_LOUIS, Cities.CHICAGO,
															RouteColors.Green, RouteColors.White, 2);
		Route lou_pit = new Route(Cities.SAINT_LOUIS, Cities.PITTSBURG,
															RouteColors.Green, 5);
		Route lou_nsh = new Route(Cities.SAINT_LOUIS, Cities.NASHVILLE,
															RouteColors.Gray, 2);
		Route[] louRoutes = { kc_lou, lr_lou, lou_chic, lou_pit, lou_nsh };
		map.put(Cities.SAINT_LOUIS, Arrays.asList(louRoutes));

		// Chicago
		// chic_oma
		// chic_dul
		// chic_lou
		Route chic_tor = new Route(Cities.CHICAGO, Cities.TORONTO,
															RouteColors.White, 4);
		Route chic_pit = new Route(Cities.CHICAGO, Cities.PITTSBURG,
															RouteColors.Orange, RouteColors.Black, 3);
		Route[] chicRoutes = { oma_chic, dul_chic, lou_chic, chic_tor, chic_pit };
		map.put(Cities.CHICAGO, Arrays.asList(chicRoutes));

		// Sault St. Marie
		// ssm_win
		// ssm_dul
		Route ssm_tor = new Route(Cities.SAULT_ST_MARIE, Cities.TORONTO,
															RouteColors.Gray, 2);
		Route ssm_mnt = new Route(Cities.SAULT_ST_MARIE, Cities.MONTREAL,
															RouteColors.Black, 5);
		Route[] ssmRoutes = { win_ssm, dul_ssm, ssm_tor, ssm_tor };
		map.put(Cities.SAULT_ST_MARIE, Arrays.asList(ssmRoutes));

		// Toronto
		// tor_chic
		// tor_dul
		// tor_ssm
		Route tor_pit = new Route(Cities.TORONTO, Cities.PITTSBURG,
															RouteColors.Gray, 2);
		Route tor_mnt = new Route(Cities.TORONTO, Cities.MONTREAL,
															RouteColors.Gray, 2);
		Route[] torRoutes = { chic_tor, dul_tor, ssm_tor, tor_pit, tor_mnt };
		map.put(Cities.TORONTO, Arrays.asList(torRoutes));

		// Pittsburg
		// pit_lou
		// pit_chic
		// pit_tor
		Route pit_ny = new Route(Cities.PITTSBURG, Cities.NEW_YORK,
															RouteColors.White, RouteColors.Green, 2);
		Route pit_wsh = new Route(Cities.PITTSBURG, Cities.WASHINGTON,
															RouteColors.Gray, 2);
		Route pit_ral = new Route(Cities.PITTSBURG, Cities.RALEIGH,
															RouteColors.Gray, 2);
		Route pit_nsh = new Route(Cities.PITTSBURG, Cities.NASHVILLE,
															RouteColors.Yellow, 4);
		Route[] pitRoutes = { lou_pit, chic_pit, tor_pit, pit_ny, pit_wsh, pit_ral, pit_nsh };
		map.put(Cities.PITTSBURG, Arrays.asList(pitRoutes));

		// Nashville
		// nsh_lr
		// nsh_lou
		// nsh_pit
		Route nsh_ral = new Route(Cities.NASHVILLE, Cities.RALEIGH,
															RouteColors.Black, 3);
		Route nsh_atl = new Route(Cities.NASHVILLE, Cities.ATLANTA,
															RouteColors.Gray, 1);
		Route[] nshRoutes = { lr_nsh, lou_nsh, pit_nsh, nsh_ral, nsh_atl };
		map.put(Cities.NASHVILLE, Arrays.asList(nshRoutes));

		// Atlanta
		// atl_no
		Route atl_ral = new Route(Cities.ATLANTA, Cities.RALEIGH,
															RouteColors.Gray, RouteColors.Gray, 2);
		Route atl_chr = new Route(Cities.ATLANTA, Cities.CHARLESTON,
															RouteColors.Gray, 2);
		Route atl_mia = new Route(Cities.ATLANTA, Cities.MIAMI,
															RouteColors.Blue, 5);
		Route atl_nsh = new Route(Cities.ATLANTA, Cities.NASHVILLE,
															RouteColors.Gray, 1);
		Route[] atlRoutes = { no_atl, atl_ral, atl_chr, atl_mia, atl_nsh };
		map.put(Cities.ATLANTA, Arrays.asList(atlRoutes));

		// Miami
		// mia_atl
		// mia_no
		Route mia_chr = new Route(Cities.MIAMI, Cities.CHARLESTON,
															RouteColors.Purple, 4);
		Route[] miaRoutes = { atl_mia, no_mia, mia_chr };
		map.put(Cities.MIAMI, Arrays.asList(miaRoutes));

		// Charleston
		// chr_atl
		// chr_mia
		Route chr_ral = new Route(Cities.CHARLESTON, Cities.RALEIGH,
															RouteColors.Gray, 2);
		Route[] chrRoutes = { atl_chr, mia_chr, chr_ral };
		map.put(Cities.CHARLESTON, Arrays.asList(chrRoutes));

		// Raleigh
		// ral_pit
		// ral_nsh
		// ral_atl
		// ral_chr
		Route ral_wsh = new Route(Cities.RALEIGH, Cities.WASHINGTON,
															RouteColors.Gray, RouteColors.Gray, 2);		
		Route[] ralRoutes = { pit_ral, ral_wsh, nsh_ral, };
		map.put(Cities.RALEIGH, Arrays.asList(ralRoutes));

		// Washington
		// wsh_pit
		// wsh_ral
		Route wsh_ny = new Route(Cities.WASHINGTON, Cities.NEW_YORK,
															RouteColors.Orange, RouteColors.Black, 2);
		Route[] wshRoutes = { pit_wsh, ral_wsh, wsh_ny };
		map.put(Cities.WASHINGTON, Arrays.asList(wshRoutes));

		// New York
		// ny_pit
		// ny_wsh
		Route ny_mnt = new Route(Cities.NEW_YORK, Cities.MONTREAL,
															RouteColors.Blue, 3);
		Route ny_bos = new Route(Cities.NEW_YORK, Cities.BOSTON,
															RouteColors.Yellow, RouteColors.Red, 2);
		Route[] nyRoutes = { pit_ny, wsh_ny, ny_mnt, ny_bos };
		map.put(Cities.NEW_YORK, Arrays.asList(nyRoutes));

		// Boston
		// bos_ny
		Route bos_mnt = new Route(Cities.BOSTON, Cities.MONTREAL,
															RouteColors.Gray, RouteColors.Gray, 2);		
		Route[] bosRoutes = { ny_bos, bos_mnt };
		map.put(Cities.BOSTON, Arrays.asList(bosRoutes));

		// Montreal
		// mnt_ssm
		// mnt_tor
		// mnt_ny 
		// mnt_bos
		Route[] mntRoutes = { ssm_mnt, tor_mnt, ny_mnt, bos_mnt };
		map.put(Cities.MONTREAL, Arrays.asList(mntRoutes));

		return map;
	}

    public List<Route> getAllRoutes() {
        List<Route> allRoutes = new ArrayList<>();
        for (List<Route> routes: cityGraph.values()) {
            for (Route route : routes) {
                allRoutes.add(route);
            }
        }
        return allRoutes;
    }
}
