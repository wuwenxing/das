package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据每个运营商的套餐，进行拆分流量包
 * 
 *  一、亿美-套餐编号	流量	套餐类型		运营商	价格
	1	5M		5M/1元		CTCC	1
	2	10M		10M/2元		CTCC	2
	3	30M		30M/5元		CTCC	5
	4	50M		50M/7元		CTCC	7
	5	100M	100M/10元	CTCC	10
	6	200M	200M/15元	CTCC	15
	7	500M	500M/30元	CTCC	30
	8	1G		1G/50元		CTCC	50
	
	24	20M		20M/3元		CUCC	3
	9	50M		50M/6元		CUCC	6
	25	100M	100M/10元	CUCC	10
	10	200M	200M/15元	CUCC	15
	26	500M	500M/30元	CUCC	30

	11	10M		10M/3元		CMCC	3
	12	30M		30M/5元		CMCC	5
	13	70M		70M/10元		CMCC	10
	27	100M	100M/10元	CMCC	10
	14	150M	150M/20元	CMCC	20
	28	300M	300M/20元	CMCC	20
	15	500M	500M/30元	CMCC	30
	16	1G		1G/50元		CMCC	50
	17	2G		2G/70元		CMCC	70
	18	3G		3G/100元		CMCC	100
	19	4G		4G/130元		CMCC	130
	20	6G		6G/180元		CMCC	180
	21	11G		11G/280元	CMCC	280
	
 *  二、乐免-运营商	流量编码	流量值
	移动		10		10M
			30		30M
			70		70M
			150		150M
			500		500M
			1024	1024M
			2048	2048M
			3072	3072M
			4096	4096M
			6144	6144M
			11264	11264M
	联通		20		20M
			50		50M
			100		100M
			200		200M
			500		500M
	电信		5		5M
			10		10M
			30		30M
			50		50M
			100		100M
			200		200M
			500		500M
			1024	1024M
			
 *  三、容联-运营商	
	sn编码	档位	原价		描述
	888888	无	无		测试档位不实际充值不产生费用
	
	"移动"
	100001	10	3		移动10M
	100002	30	5		移动30M
  --100014	50	 		移动50M(特殊包)
	100003	70	10		移动70M
  --100012	100	 		移动100M(特殊包)
	100004	150	20		移动150M
	100005	500	30		移动500M
	100006	1024	50		移动1024M
	100007	2048	70		移动2048M
	100008	3072	100		移动3072M
	100009	4096	130		移动4096M
	100010	6144	180		移动6144M
	100011	11264	280		移动11264M
	
	"全国联通"
	200001	20	3	0	联通20M
  --200008	30	 		联通30M(特殊包)
	200002	50	6	0	联通50M
	200003	100	10	0	联通100M
	200004	200	15	0	联通200M
  --200006	300	 		联通300M(特殊包)
	200005	500	30	0	联通500M
  --200007	1024 		联通1024M(特殊包)
	
	"电信"
	300001	5	1		电信5M
	300002	10	2		电信10M
	300003	30	5		电信30M
	300004	50	7		电信50M
	300005	100	10		电信100M
	300006	200	15		电信200M
	300007	500	30		电信500M
	300008	1024	50		电信1024M	
	
 * @author wayne
 */
public enum FlowPackageEnum implements EnumIntf {

//	_10M("10M", "10M", "24", "2", "11", "", "", ""),
	
	_20M("20M", "20M", "24", "2,2", "11,11"
			, "20", "10,10", "10,10"
			, "200001,20;", "300002,10;300002,10;", "100001,10;100001,10;"),
	_30M("30M", "30M", "24", "3", "12"
			, "20", "30", "30"
			, "200008,30;", "300003,30;", "100002,30;"),
	_50M("50M", "50M", "9", "4", "12,11,11"
			, "50", "50", "30,10,10"
			, "200002,50;", "300003,30;300002,10;300002,10;", "100014,50;"),
	_70M("70M", "70M", "9,24", "4,2,2", "13"
			, "50,20", "50,10,10", "70"
			, "200002,50;200001,20;", "300004,50;300002,10;300002,10;", "100003,70;"),
	_100M("100M", "100M", "25", "5", "27"
			, "100", "100", "70,30"
			, "200003,100;", "300005,100;", "100012,100;"),
	_120M("120M", "120M", "25,24", "5,2,2", "27,11,11"
			, "100,20", "100,10,10", "70,30,10,10"
			, "200003,100;200001,20;", "300005,100;300002,10;300002,10;", "100012,100;100001,10;100001,10;"),
	_150M("150M", "150M", "25,9", "5,4", "14"
			, "100,50", "100,50", "150"
			, "200003,100;200002,50;", "300005,100;300004,50;", "100004,150;"),
	_200M("200M", "200M", "10", "6", "27,27"
			, "200", "200", "150,30,10,10"
			, "200004,200;", "300006,200;", "100004,150;100014,50;"),
	_300M("300M", "300M", "10,25", "6,5", "28"
			, "200,100", "200,100", "150,150"
			, "200004,200;200003,100;", "300006,200;300005,100;", "100004,150;100004,150;"),
	_500M("500M", "500M", "26", "7", "15"
			, "500", "500", "500"
			, "200005,500;", "300007,500;", "100005,500;"),
	_800M("800M", "800M", "26,10,25", "7,6,5", "15,28"
			, "500,200,100", "500,200,100", "500,150,150"
			, "200005,500;200004,200;200003,100;", "300007,500;300006,200;300005,100;", "100005,500;100004,150;100004,150;"),
	_1G("1G", "1G", "26,26", "8", "16"
			, "500,500", "1024", "1024"
			, "200007,1024;", "300008,1024;", "100006,1024;"),
	_2G("2G", "2G", "26,26,26,26", "8,8", "17"
			, "500,500,500,500", "1024,1024", "2048"
			, "200007,1024;200007,1024;", "300008,1024;300008,1024;", "100007,2048;"),
	_3G("3G", "3G", "26,26,26,26,26,26", "8,8,8", "18"
			, "500,500,500,500,500,500", "1024,1024,1024", "3072"
			, "200007,1024;200007,1024;200007,1024;", "300008,1024;300008,1024;300008,1024;", "100008,3072;"),
	_5G("5G", "5G", "26,26,26,26,26,26,26,2,26,26", "8,8,8,8,8", "17,18"
			, "500,500,500,500,500,500,500,500,500,500", "1024,1024,1024,1024,1024", "2048,3072"
			, "200007,1024;200007,1024;200007,1024;200007,1024;200007,1024;", "300008,1024;300008,1024;300008,1024;300008,1024;300008,1024;", "100007,2048;100008,3072;")
	;

	private final String value;
	private final String labelKey;
	private final String cucc;//亿美对应套装编号-联通
	private final String ctcc;//亿美对应套装编号-电信
	private final String cmcc;//亿美对应套装编号-移动
	private final String cuccLm;//乐免对应套装编号-联通
	private final String ctccLm;//乐免对应套装编号-电信
	private final String cmccLm;//乐免对应套装编号-移动
	private final String cuccRl;//容联对应套装编号-联通
	private final String ctccRl;//容联对应套装编号-电信
	private final String cmccRl;//容联对应套装编号-移动
	
	FlowPackageEnum(String _operator, String labelKey, String cucc, String ctcc, String cmcc
			, String cuccLm, String ctccLm, String cmccLm, String cuccRl, String ctccRl, String cmccRl
			) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.cucc = cucc;
		this.ctcc = ctcc;
		this.cmcc = cmcc;
		this.cuccLm = cuccLm;
		this.ctccLm = ctccLm;
		this.cmccLm = cmccLm;
		this.cuccRl = cuccRl;
		this.ctccRl = ctccRl;
		this.cmccRl = cmccRl;
	}
	
	public static List<FlowPackageEnum> getList(){
		List<FlowPackageEnum> result = new ArrayList<FlowPackageEnum>();
		for(FlowPackageEnum ae : FlowPackageEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(FlowPackageEnum ae : FlowPackageEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	/**
	 * 验证是否存在对应的类型
	 * @param labelKey
	 * @return
	 */
	public static FlowPackageEnum check(String labelKey){
		for(FlowPackageEnum ae : FlowPackageEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getCucc() {
		return cucc;
	}

	public String getCtcc() {
		return ctcc;
	}

	public String getCmcc() {
		return cmcc;
	}
	
	public String getCuccLm() {
		return cuccLm;
	}

	public String getCtccLm() {
		return ctccLm;
	}

	public String getCmccLm() {
		return cmccLm;
	}

	public String getCuccRl() {
		return cuccRl;
	}

	public String getCtccRl() {
		return ctccRl;
	}

	public String getCmccRl() {
		return cmccRl;
	}

	public static void main(String[] args) {
		
	}
	
}
