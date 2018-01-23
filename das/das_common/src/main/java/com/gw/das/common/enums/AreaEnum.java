package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地域枚举类型
 */
public enum AreaEnum implements EnumIntf {

	BeiJing("北京市","北京","北京"),
	TianJin("天津市","天津","天津"),
	ShangHai("上海市","上海","上海"),
	ChongQing("重庆市","重庆","重庆"),
	
	HeBei("河北省","河北","河北"),
	ShanXi("山西省","山西","山西"),
	LiaoNing("辽宁省","辽宁","辽宁"),
	JiLin("吉林省","吉林","吉林"),
	HeiLongJiang("黑龙江省","黑龙江","黑龙江"),
	JiangSu("江苏省","江苏","江苏"),
	ZheJiang("浙江省","浙江","浙江"),
	AnHui("安徽省","安徽","安徽"),
	FuJian("福建省","福建","福建"),
	JiangXi("江西省","江西","江西"),
	ShanDong("山东省","山东","山东"),
	HeNan("河南省","河南","河南"),
	HuBei("湖北省","湖北","湖北"),
	HuNan("湖南省","湖南","湖南"),
	GuangDong("广东省","广东","广东"),
	HaiNan("海南省","海南","海南"),
	SiChuan("四川省","四川","四川"),
	GuiZhou("贵州省","贵州","贵州"),
	YunNan("云南省","云南","云南"),
	ShaanXi("陕西省","陕西","陕西"),
	
	GanSu("甘肃省","甘肃","甘肃"),
	QingHai("青海省","青海","青海"),
	TaiWan("台湾省","台湾","台湾"),
	
	XiangGang("香港特别行政区","香港","香港"),
	AoMen("澳门特别行政区","澳门","澳门"),
	QiTa("其他","其他","");

	private final String value;
	private final String labelKey;
	private final String condition;//模糊查询条件
	AreaEnum(String _operator, String labelKey, String condition) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.condition = condition;
	}
	
	public static List<AreaEnum> getList(){
		List<AreaEnum> result = new ArrayList<AreaEnum>();
		for(AreaEnum ae : AreaEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();
		for(AreaEnum ae : AreaEnum.values()){
			map.put(ae.getLabelKey(), ae.getValue());
		}
		return map;
	}
	
	public static AreaEnum getAreaEnum(String labelKey){
		for(AreaEnum ae : AreaEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}

	public String getValue() {
        return this.value;
    }
	public String getLabelKey() {
        return this.labelKey;
    }
	public String getCondition() {
		return condition;
	}
	
}
