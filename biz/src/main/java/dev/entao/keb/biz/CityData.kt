package dev.entao.keb.biz

import dev.entao.keb.biz.model.Area

object CityData {

	fun childrenOf(provinceCode: String): List<Area> {
		return all.filter { it.parent == provinceCode }
	}

	fun nameOf(code: String): String {
		return map[code] ?: ""
	}

	val map: HashMap<String, String> by lazy {
		val m = HashMap<String, String>()
		all.forEach {
			m[it.code] = it.name
		}
		m
	}
	val all: List<Area> = listOf(
			Area("110100", "北京市", "110000"),
			Area("110101", "东城区", "110000"),
			Area("110102", "西城区", "110000"),
			Area("110105", "朝阳区", "110000"),
			Area("110106", "丰台区", "110000"),
			Area("110107", "石景山区", "110000"),
			Area("110108", "海淀区", "110000"),
			Area("110109", "门头沟区", "110000"),
			Area("110111", "房山区", "110000"),
			Area("110112", "通州区", "110000"),
			Area("110113", "顺义区", "110000"),
			Area("110114", "昌平区", "110000"),
			Area("110115", "大兴区", "110000"),
			Area("110116", "怀柔区", "110000"),
			Area("110117", "平谷区", "110000"),
			Area("110118", "密云区", "110000"),
			Area("110119", "延庆区", "110000"),
			Area("120100", "天津市", "120000"),
			Area("120101", "和平区", "120000"),
			Area("120102", "河东区", "120000"),
			Area("120103", "河西区", "120000"),
			Area("120104", "南开区", "120000"),
			Area("120105", "河北区", "120000"),
			Area("120106", "红桥区", "120000"),
			Area("120110", "东丽区", "120000"),
			Area("120111", "西青区", "120000"),
			Area("120112", "津南区", "120000"),
			Area("120113", "北辰区", "120000"),
			Area("120114", "武清区", "120000"),
			Area("120115", "宝坻区", "120000"),
			Area("120116", "滨海新区", "120000"),
			Area("120117", "宁河区", "120000"),
			Area("120118", "静海区", "120000"),
			Area("120119", "蓟州区", "120000"),
			Area("130100", "石家庄市", "130000"),
			Area("130200", "唐山市", "130000"),
			Area("130300", "秦皇岛市", "130000"),
			Area("130400", "邯郸市", "130000"),
			Area("130500", "邢台市", "130000"),
			Area("130600", "保定市", "130000"),
			Area("130700", "张家口市", "130000"),
			Area("130800", "承德市", "130000"),
			Area("130900", "沧州市", "130000"),
			Area("131000", "廊坊市", "130000"),
			Area("131100", "衡水市", "130000"),
			Area("140100", "太原市", "140000"),
			Area("140200", "大同市", "140000"),
			Area("140300", "阳泉市", "140000"),
			Area("140400", "长治市", "140000"),
			Area("140500", "晋城市", "140000"),
			Area("140600", "朔州市", "140000"),
			Area("140700", "晋中市", "140000"),
			Area("140800", "运城市", "140000"),
			Area("140900", "忻州市", "140000"),
			Area("141000", "临汾市", "140000"),
			Area("141100", "吕梁市", "140000"),
			Area("150100", "呼和浩特市", "150000"),
			Area("150200", "包头市", "150000"),
			Area("150300", "乌海市", "150000"),
			Area("150400", "赤峰市", "150000"),
			Area("150500", "通辽市", "150000"),
			Area("150600", "鄂尔多斯市", "150000"),
			Area("150700", "呼伦贝尔市", "150000"),
			Area("150800", "巴彦淖尔市", "150000"),
			Area("150900", "乌兰察布市", "150000"),
			Area("210100", "沈阳市", "210000"),
			Area("210200", "大连市", "210000"),
			Area("210300", "鞍山市", "210000"),
			Area("210400", "抚顺市", "210000"),
			Area("210500", "本溪市", "210000"),
			Area("210600", "丹东市", "210000"),
			Area("210700", "锦州市", "210000"),
			Area("210800", "营口市", "210000"),
			Area("210900", "阜新市", "210000"),
			Area("211000", "辽阳市", "210000"),
			Area("211100", "盘锦市", "210000"),
			Area("211200", "铁岭市", "210000"),
			Area("211300", "朝阳市", "210000"),
			Area("211400", "葫芦岛市", "210000"),
			Area("220100", "长春市", "220000"),
			Area("220200", "吉林市", "220000"),
			Area("220300", "四平市", "220000"),
			Area("220400", "辽源市", "220000"),
			Area("220500", "通化市", "220000"),
			Area("220600", "白山市", "220000"),
			Area("220700", "松原市", "220000"),
			Area("220800", "白城市", "220000"),
			Area("230100", "哈尔滨市", "230000"),
			Area("230200", "齐齐哈尔市", "230000"),
			Area("230300", "鸡西市", "230000"),
			Area("230400", "鹤岗市", "230000"),
			Area("230500", "双鸭山市", "230000"),
			Area("230600", "大庆市", "230000"),
			Area("230700", "伊春市", "230000"),
			Area("230800", "佳木斯市", "230000"),
			Area("230900", "七台河市", "230000"),
			Area("231000", "牡丹江市", "230000"),
			Area("231100", "黑河市", "230000"),
			Area("231200", "绥化市", "230000"),
			Area("310100", "上海市", "310000"),
			Area("310101", "黄浦区", "310000"),
			Area("310104", "徐汇区", "310000"),
			Area("310105", "长宁区", "310000"),
			Area("310106", "静安区", "310000"),
			Area("310107", "普陀区", "310000"),
			Area("310109", "虹口区", "310000"),
			Area("310110", "杨浦区", "310000"),
			Area("310112", "闵行区", "310000"),
			Area("310113", "宝山区", "310000"),
			Area("310114", "嘉定区", "310000"),
			Area("310115", "浦东新区", "310000"),
			Area("310116", "金山区", "310000"),
			Area("310117", "松江区", "310000"),
			Area("310118", "青浦区", "310000"),
			Area("310120", "奉贤区", "310000"),
			Area("310151", "崇明区", "310000"),
			Area("320100", "南京市", "320000"),
			Area("320200", "无锡市", "320000"),
			Area("320300", "徐州市", "320000"),
			Area("320400", "常州市", "320000"),
			Area("320500", "苏州市", "320000"),
			Area("320600", "南通市", "320000"),
			Area("320700", "连云港市", "320000"),
			Area("320800", "淮安市", "320000"),
			Area("320900", "盐城市", "320000"),
			Area("321000", "扬州市", "320000"),
			Area("321100", "镇江市", "320000"),
			Area("321200", "泰州市", "320000"),
			Area("321300", "宿迁市", "320000"),
			Area("330100", "杭州市", "330000"),
			Area("330200", "宁波市", "330000"),
			Area("330300", "温州市", "330000"),
			Area("330400", "嘉兴市", "330000"),
			Area("330500", "湖州市", "330000"),
			Area("330600", "绍兴市", "330000"),
			Area("330700", "金华市", "330000"),
			Area("330800", "衢州市", "330000"),
			Area("330900", "舟山市", "330000"),
			Area("331000", "台州市", "330000"),
			Area("331100", "丽水市", "330000"),
			Area("340100", "合肥市", "340000"),
			Area("340200", "芜湖市", "340000"),
			Area("340300", "蚌埠市", "340000"),
			Area("340400", "淮南市", "340000"),
			Area("340500", "马鞍山市", "340000"),
			Area("340600", "淮北市", "340000"),
			Area("340700", "铜陵市", "340000"),
			Area("340800", "安庆市", "340000"),
			Area("341000", "黄山市", "340000"),
			Area("341100", "滁州市", "340000"),
			Area("341200", "阜阳市", "340000"),
			Area("341300", "宿州市", "340000"),
			Area("341500", "六安市", "340000"),
			Area("341600", "亳州市", "340000"),
			Area("341700", "池州市", "340000"),
			Area("341800", "宣城市", "340000"),
			Area("350100", "福州市", "350000"),
			Area("350200", "厦门市", "350000"),
			Area("350300", "莆田市", "350000"),
			Area("350400", "三明市", "350000"),
			Area("350500", "泉州市", "350000"),
			Area("350600", "漳州市", "350000"),
			Area("350700", "南平市", "350000"),
			Area("350800", "龙岩市", "350000"),
			Area("350900", "宁德市", "350000"),
			Area("360100", "南昌市", "360000"),
			Area("360200", "景德镇市", "360000"),
			Area("360300", "萍乡市", "360000"),
			Area("360400", "九江市", "360000"),
			Area("360500", "新余市", "360000"),
			Area("360600", "鹰潭市", "360000"),
			Area("360700", "赣州市", "360000"),
			Area("360800", "吉安市", "360000"),
			Area("360900", "宜春市", "360000"),
			Area("361000", "抚州市", "360000"),
			Area("361100", "上饶市", "360000"),
			Area("370100", "济南市", "370000"),
			Area("370200", "青岛市", "370000"),
			Area("370300", "淄博市", "370000"),
			Area("370400", "枣庄市", "370000"),
			Area("370500", "东营市", "370000"),
			Area("370600", "烟台市", "370000"),
			Area("370700", "潍坊市", "370000"),
			Area("370800", "济宁市", "370000"),
			Area("370900", "泰安市", "370000"),
			Area("371000", "威海市", "370000"),
			Area("371100", "日照市", "370000"),
			Area("371200", "莱芜市", "370000"),
			Area("371300", "临沂市", "370000"),
			Area("371400", "德州市", "370000"),
			Area("371500", "聊城市", "370000"),
			Area("371600", "滨州市", "370000"),
			Area("371700", "菏泽市", "370000"),
			Area("410100", "郑州市", "410000"),
			Area("410200", "开封市", "410000"),
			Area("410300", "洛阳市", "410000"),
			Area("410400", "平顶山市", "410000"),
			Area("410500", "安阳市", "410000"),
			Area("410600", "鹤壁市", "410000"),
			Area("410700", "新乡市", "410000"),
			Area("410800", "焦作市", "410000"),
			Area("410900", "濮阳市", "410000"),
			Area("411000", "许昌市", "410000"),
			Area("411100", "漯河市", "410000"),
			Area("411200", "三门峡市", "410000"),
			Area("411300", "南阳市", "410000"),
			Area("411400", "商丘市", "410000"),
			Area("411500", "信阳市", "410000"),
			Area("411600", "周口市", "410000"),
			Area("411700", "驻马店市", "410000"),
			Area("420100", "武汉市", "420000"),
			Area("420200", "黄石市", "420000"),
			Area("420300", "十堰市", "420000"),
			Area("420500", "宜昌市", "420000"),
			Area("420600", "襄阳市", "420000"),
			Area("420700", "鄂州市", "420000"),
			Area("420800", "荆门市", "420000"),
			Area("420900", "孝感市", "420000"),
			Area("421000", "荆州市", "420000"),
			Area("421100", "黄冈市", "420000"),
			Area("421200", "咸宁市", "420000"),
			Area("421300", "随州市", "420000"),
			Area("430100", "长沙市", "430000"),
			Area("430200", "株洲市", "430000"),
			Area("430300", "湘潭市", "430000"),
			Area("430400", "衡阳市", "430000"),
			Area("430500", "邵阳市", "430000"),
			Area("430600", "岳阳市", "430000"),
			Area("430700", "常德市", "430000"),
			Area("430800", "张家界市", "430000"),
			Area("430900", "益阳市", "430000"),
			Area("431000", "郴州市", "430000"),
			Area("431100", "永州市", "430000"),
			Area("431200", "怀化市", "430000"),
			Area("431300", "娄底市", "430000"),
			Area("440100", "广州市", "440000"),
			Area("440200", "韶关市", "440000"),
			Area("440300", "深圳市", "440000"),
			Area("440400", "珠海市", "440000"),
			Area("440500", "汕头市", "440000"),
			Area("440600", "佛山市", "440000"),
			Area("440700", "江门市", "440000"),
			Area("440800", "湛江市", "440000"),
			Area("440900", "茂名市", "440000"),
			Area("441200", "肇庆市", "440000"),
			Area("441300", "惠州市", "440000"),
			Area("441400", "梅州市", "440000"),
			Area("441500", "汕尾市", "440000"),
			Area("441600", "河源市", "440000"),
			Area("441700", "阳江市", "440000"),
			Area("441800", "清远市", "440000"),
			Area("445100", "潮州市", "440000"),
			Area("445200", "揭阳市", "440000"),
			Area("445300", "云浮市", "440000"),
			Area("450100", "南宁市", "450000"),
			Area("450200", "柳州市", "450000"),
			Area("450300", "桂林市", "450000"),
			Area("450400", "梧州市", "450000"),
			Area("450500", "北海市", "450000"),
			Area("450600", "防城港市", "450000"),
			Area("450700", "钦州市", "450000"),
			Area("450800", "贵港市", "450000"),
			Area("450900", "玉林市", "450000"),
			Area("451000", "百色市", "450000"),
			Area("451100", "贺州市", "450000"),
			Area("451200", "河池市", "450000"),
			Area("451300", "来宾市", "450000"),
			Area("451400", "崇左市", "450000"),
			Area("460100", "海口市", "460000"),
			Area("460200", "三亚市", "460000"),
			Area("460300", "三沙市", "460000"),
			Area("500100", "重庆市", "500000"),
			Area("500101", "万州区", "500000"),
			Area("500102", "涪陵区", "500000"),
			Area("500103", "渝中区", "500000"),
			Area("500104", "大渡口区", "500000"),
			Area("500105", "江北区", "500000"),
			Area("500106", "沙坪坝区", "500000"),
			Area("500107", "九龙坡区", "500000"),
			Area("500108", "南岸区", "500000"),
			Area("500109", "北碚区", "500000"),
			Area("500110", "綦江区", "500000"),
			Area("500111", "大足区", "500000"),
			Area("500112", "渝北区", "500000"),
			Area("500113", "巴南区", "500000"),
			Area("500114", "黔江区", "500000"),
			Area("500115", "长寿区", "500000"),
			Area("500116", "江津区", "500000"),
			Area("500117", "合川区", "500000"),
			Area("500118", "永川区", "500000"),
			Area("500119", "南川区", "500000"),
			Area("500120", "璧山区", "500000"),
			Area("500151", "铜梁区", "500000"),
			Area("500152", "潼南区", "500000"),
			Area("500153", "荣昌区", "500000"),
			Area("500154", "开州区", "500000"),
			Area("500155", "梁平区", "500000"),
			Area("500156", "武隆区", "500000"),
			Area("500200", "重庆市郊县", "500000"),
			Area("500229", "城口县", "500000"),
			Area("500230", "丰都县", "500000"),
			Area("500231", "垫江县", "500000"),
			Area("500233", "忠县", "500000"),
			Area("500235", "云阳县", "500000"),
			Area("500236", "奉节县", "500000"),
			Area("500237", "巫山县", "500000"),
			Area("500238", "巫溪县", "500000"),
			Area("500240", "石柱自治县", "500000"),
			Area("500241", "秀山自治县", "500000"),
			Area("500242", "酉阳自治县", "500000"),
			Area("500243", "彭水自治县", "500000"),
			Area("510100", "成都市", "510000"),
			Area("510300", "自贡市", "510000"),
			Area("510400", "攀枝花市", "510000"),
			Area("510500", "泸州市", "510000"),
			Area("510600", "德阳市", "510000"),
			Area("510700", "绵阳市", "510000"),
			Area("510800", "广元市", "510000"),
			Area("510900", "遂宁市", "510000"),
			Area("511000", "内江市", "510000"),
			Area("511100", "乐山市", "510000"),
			Area("511300", "南充市", "510000"),
			Area("511400", "眉山市", "510000"),
			Area("511500", "宜宾市", "510000"),
			Area("511600", "广安市", "510000"),
			Area("511700", "达州市", "510000"),
			Area("511800", "雅安市", "510000"),
			Area("511900", "巴中市", "510000"),
			Area("512000", "资阳市", "510000"),
			Area("520100", "贵阳市", "520000"),
			Area("520300", "遵义市", "520000"),
			Area("520400", "安顺市", "520000"),
			Area("530100", "昆明市", "530000"),
			Area("530300", "曲靖市", "530000"),
			Area("530400", "玉溪市", "530000"),
			Area("530500", "保山市", "530000"),
			Area("530600", "昭通市", "530000"),
			Area("530700", "丽江市", "530000"),
			Area("530800", "普洱市", "530000"),
			Area("530900", "临沧市", "530000"),
			Area("540100", "拉萨市", "540000"),
			Area("610100", "西安市", "610000"),
			Area("610200", "铜川市", "610000"),
			Area("610300", "宝鸡市", "610000"),
			Area("610400", "咸阳市", "610000"),
			Area("610500", "渭南市", "610000"),
			Area("610600", "延安市", "610000"),
			Area("610700", "汉中市", "610000"),
			Area("610800", "榆林市", "610000"),
			Area("610900", "安康市", "610000"),
			Area("611000", "商洛市", "610000"),
			Area("620100", "兰州市", "620000"),
			Area("620200", "嘉峪关市", "620000"),
			Area("620300", "金昌市", "620000"),
			Area("620400", "白银市", "620000"),
			Area("620500", "天水市", "620000"),
			Area("620600", "武威市", "620000"),
			Area("620700", "张掖市", "620000"),
			Area("620800", "平凉市", "620000"),
			Area("620900", "酒泉市", "620000"),
			Area("621000", "庆阳市", "620000"),
			Area("621100", "定西市", "620000"),
			Area("621200", "陇南市", "620000"),
			Area("630100", "西宁市", "630000"),
			Area("640100", "银川市", "640000"),
			Area("640200", "石嘴山市", "640000"),
			Area("640300", "吴忠市", "640000"),
			Area("640400", "固原市", "640000"),
			Area("640500", "中卫市", "640000"),
			Area("650100", "乌鲁木齐市", "650000"),
			Area("650200", "克拉玛依市", "650000"),
			Area("810001", "中西区", "810000"),
			Area("810002", "湾仔区", "810000"),
			Area("810003", "东区", "810000"),
			Area("810004", "南区", "810000"),
			Area("810005", "油尖旺区", "810000"),
			Area("810006", "深水_区", "810000"),
			Area("810007", "九龙城区", "810000"),
			Area("810008", "黄大仙区", "810000"),
			Area("810009", "观塘区", "810000"),
			Area("810010", "荃湾区", "810000"),
			Area("810011", "屯门区", "810000"),
			Area("810012", "元朗区", "810000"),
			Area("810013", "北区", "810000"),
			Area("810014", "大埔区", "810000"),
			Area("810015", "西贡区", "810000"),
			Area("810016", "沙田区", "810000"),
			Area("810017", "葵青区", "810000"),
			Area("810018", "离岛区", "810000"),
			Area("820001", "花地玛堂区", "820000"),
			Area("820002", "花王堂区", "820000"),
			Area("820003", "望德堂区", "820000"),
			Area("820004", "大堂区", "820000"),
			Area("820005", "风顺堂区", "820000"),
			Area("820006", "嘉模堂区", "820000"),
			Area("820007", "路凼填海区", "820000"),
			Area("820008", "圣方济各堂区", "820000")
	)
}