package com.rubberdroid.sudoiku.generator;

import java.util.HashMap;
import java.util.Random;

import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoku.util.GeneratorSupport;

public class StaticSudokuGenerator implements SudokuGenerator {

	private final String[] easy = {
			"030000001050000004200000700080500000072603000400000060004850000007206080800710502",
			"000010000080050000905360000000000079300802500800075001008900006072000000090031050",
			"600000009009801360070040000000670000008000002730002000480003000010020000006790000",
			"000000001300000400002074050704006200680000000035007000000000030510000087408000600",
			"600050832007260005000043000009500008034070520820900004000480090000000000000000600",
			"026040000000000000000890004410300700000900080805000200000180000008702053030000007",
			"005306000000014000010920000004000250500700109009032067006003000703050010000400000",
			"150200700309100000702804000000000350000000297000009410000070020030040900000518000",
			"020007805700089401500010600005172004000040000001090000062000009804000000000000000",
			"007200800132005700500700010000500000093040100400090007749003000001029000000000000",
			"000700008000003405100500670000400003039000700007600004000941006000006000600800031",
			"500003200010500700200600140006310020003050010790402006000000000060270000000000090",
			"490000007020850000080001003000300400000000000703042010000089000904073120005000000",
			"000520703000600800000703200000030000002090000008006490503940080000008034806002007",
			"000300000900010070800007000200049006010030049007800020000900000100000897000060301",
			"900031628008040130000090000000180006036007900000000000009600003200000700000070491",
			"003500470020003000000140020000089000200075000080000000870000130090001002034002590",
			"840000000002790805000000100500000000060029000108065000400010200706050004005407060",
			"008045309040002700000000050600000008800309020000008405000804070009027000000000006",
			"004307016105002073000000000583000400700050102020000000008200604000006000012000000" };

	private final String[] expert = {
			"302000500000000706971000000000005000700000001018067350030100200000009000250006009",
			"457000000000060070000000042000300780073024600100500000000200001000015368000000050",
			"200007001380100500700240930000400080000009000000005010050701008400030000007500000",
			"000020008103000000065300000000409800501000090004180260009200000000500000400091320",
			"000019300401006002006800000000031000000500090000082000500000028100000003008423006",
			"900300004005200090610009000001000306006000700207600040000003100020090057000000920",
			"030000000001025090002089030000030000000002010000700804000001500070000302000450081",
			"004900003002030800970000100000000030000200904000400015000007300008042070001809050",
			"000020009400370000020800407390041250000000901850000700000290003000000500000430000",
			"950060040200400305040008020509032000063804100800000600000300000000001500000009000",
			"020000900010020000800009000058000000400581000000706090500900706072008000004600205",
			"006000080103002090500000600000008013300021800000000070607000030205300760009000002",
			"005060020904200507000005900070020000012500804000908010600000081000000000000001005",
			"000804090009500042023000100000609023000040010090002000000100000010050086002096000",
			"800000000040021000105403002400006000301040000068100000000050204000809010600000000",
			"900803005280000104000100290400006900000000000050000000500061029020040060030058400",
			"000406037000030500008210400006800000010000050000302064540000070320000000060000003",
			"300010040007020000400600203002109600006080000018007030000000060280030000000840900",
			"038109200049000000602070080000000309000010800010000070000008720200603040004000600",
			"080900500301000000000005001097080200000010030810000060905000670000000020002007800" };

	private final String[] medium = {
			"500000708810400609900103054040600000000000040080090000091006000400009000006507000",
			"000700005500032000000560089607090050402003600000080300003000800000000000008070062",
			"009062410000000006000300809090003000865004000401806900206901000014000000000000000",
			"700300060000005730060740089000009600680400300090080504003900000009000000000032000",
			"000300000000040512000000400201000806854030900063090000000000008006051000030900200",
			"090000000710600095206000307902000003005090080300080710000000000000820000031000068",
			"970061480000400090050000001010000050000089000800500002020900000080012000490670008",
			"900000018000042007002100040019008002000070160700004000005000076000800050000056900",
			"001700000000050000608002004000040100063080000002300508004007005007060000250100300",
			"008020000090500002045600908000284060000090000000700005000001006460800507000060004",
			"045703082307500901000000005900000000080000000001004058002800000400000000750401039",
			"070610000006008029000000001000040610090200008084136007000000480060400000000007002",
			"000509700009000000000000043058043600000000105000000080000005301004901076030087009",
			"000069000000100000000400602020040800080900426000700301000090007002001008001378004",
			"000000801000205003900060020000007000200600057000159002620390005050080010100000009",
			"002000900408000000000000600020000098900840003007200000000400817001020000300790024",
			"008050040003000709000700030400500000000382600000076000200009008087000003000060420",
			"810000900004000100603504000000080010009003700000000000030600409008350000005700600",
			"000094000000006200200080000009802030084073002002900600608400009093700000100030000" };

	private final String[] simple = {
			"708000020020060108500000060006000080005000300040700016010050030007081000004000500",
			"030508270000600058040000630500070080094800000006009024000405700000002000060000000",
			"000000035000020706000000210200005097053000002000008300020000000005160000106970080",
			"050106000700200000100500003000050020002073569000002004000060000005004300036000047",
			"200000000060080050003000140040152600100800000685030000030504000004000009000300008",
			"002006000000900750080002090500000900090851004040007600260000000400009010059000000",
			"300007000700004302100860070026000054000000200005000080000200000030000708008000030",
			"720000000900006000000095000503000700000010000200000890068057100005001006000008030",
			"000000010010453000000800605043080000600100008500000047000940853000000100000600094",
			"309080200100070480000023019000096000080002000002004000013908640060000000008000020",
			"500800700001200500000000006600000800002070005087000009054009200100006000290001084",
			"056807901010050004000020800009500080400096700720001000001004000070000000000000050",
			"008600503002090407035004900000548600900000000050039704020007000000000000047000000",
			"180400000367018000000070000070001060008093000400060058006000530840000000500307000",
			"000000700000000090071020005095060100108000520700000000000906073803000200000052080",
			"100500070000000090005102000009000000006700003070000010000001802820070530030028000",
			"086005000005080200090001007000070029010000000503006001008000900070010000000500003",
			"700600001000090004000027090000002100009560200000480006100005460000000005405806072",
			"039000000000400078050790600100079000306100050080043007000006000000000140010000003" };

	private HashMap<Integer, String[]> levels = new HashMap<Integer, String[]>();
	private Random random;

	
	public StaticSudokuGenerator() {
		levels.put(Sudoiku.DIFFICULTY_EASY, easy);
		levels.put(Sudoiku.DIFFICULTY_SIMPLE, simple);
		levels.put(Sudoiku.DIFFICULTY_MEDIUM, medium);
		levels.put(Sudoiku.DIFFICULTY_EXPERT, expert);
		random = new Random();
	}

	private final String finishedPuzzle = "038427651756981324241365798683542917172693845495178263324859176517236489869714532";

	public Tile[] create(int diff) {
		return GeneratorSupport.fromPuzzleString(finishedPuzzle);
	}
	
	public Tile[] create1(int diff) {
		String[] level = levels.get(diff);
		String puzzle = level[random.nextInt(level.length)];
		return GeneratorSupport.fromPuzzleString(puzzle);
	}
}
